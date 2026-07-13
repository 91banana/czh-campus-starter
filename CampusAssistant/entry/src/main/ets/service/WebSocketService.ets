import webSocket from '@ohos.net.webSocket';
import { AppStore } from './AppStore';
import { ApiService } from './ApiService';

export interface WsMessage {
  type: string;
  data: Object;
}

export type MessageCallback = (msg: WsMessage) => void;

interface WsSendMsg {
  type: string;
  conversationId?: number;
  content?: string;
  msgType?: string;
}

interface WsStateMsg {
  type: string;
  data: Record<string, Object>;
}

interface StateData {
  state: Object;
}

export class WebSocketService {
  private static instance: WebSocketService | null = null;
  private ws: webSocket.WebSocket | null = null;
  private callbacks: MessageCallback[] = [];
  private reconnectTimer: number = -1;
  private heartbeatTimer: number = -1;
  private reconnectAttempts: number = 0;
  private maxReconnectDelay: number = 30000;
  private shouldReconnect: boolean = false;
  private _connectionState: string = 'disconnected';
  private _onlineUsers: Set<number> = new Set();

  static getInstance(): WebSocketService {
    if (WebSocketService.instance === null) {
      WebSocketService.instance = new WebSocketService();
    }
    return WebSocketService.instance;
  }

  get connectionState(): string {
    return this._connectionState;
  }

  isUserOnline(userId: number): boolean {
    return this._onlineUsers.has(userId);
  }

  onMessage(cb: MessageCallback): void {
    this.callbacks.push(cb);
  }

  removeMessageCallback(cb: MessageCallback): void {
    const idx: number = this.callbacks.indexOf(cb);
    if (idx >= 0) {
      this.callbacks.splice(idx, 1);
    }
  }

  notifyUnreadUpdate(count: number): void {
    const unreadData: Record<string, Object> = { 'unreadCount': count as Object };
    this.notifyCallbacks({ type: 'unread_update', data: unreadData });
  }

  connect(): void {
    const store: AppStore = AppStore.getInstance();
    if (store.token.length === 0) {
      return;
    }
    if (this._connectionState === 'connected' || this._connectionState === 'connecting') {
      return;
    }
    this.shouldReconnect = true;
    this._connectionState = 'connecting';
    this.doConnect();
  }

  disconnect(): void {
    this.shouldReconnect = false;
    this._connectionState = 'disconnected';
    this.clearTimers();
    if (this.ws !== null) {
      try {
        this.ws.close();
      } catch (e) {
      }
      this.ws = null;
    }
    this._onlineUsers.clear();
  }

  send(obj: WsSendMsg): void {
    if (this.ws !== null && this._connectionState === 'connected') {
      try {
        this.ws.send(JSON.stringify(obj));
      } catch (e) {
      }
    }
  }

  sendMessage(conversationId: number, content: string, msgType: string): void {
    const msg: WsSendMsg = {
      type: 'message',
      conversationId: conversationId,
      content: content,
      msgType: msgType,
    };
    this.send(msg);
  }

  sendMarkRead(conversationId: number): void {
    const msg: WsSendMsg = {
      type: 'mark_read',
      conversationId: conversationId,
    };
    this.send(msg);
  }

  private doConnect(): void {
    const store: AppStore = AppStore.getInstance();
    const url: string = `ws://${ApiService.baseUrl.substring(7)}/ws/chat?token=${store.token}`;
    this.ws = webSocket.createWebSocket();

    this.ws.on('open', () => {
      this._connectionState = 'connected';
      this.reconnectAttempts = 0;
      this.startHeartbeat();
      this.fetchOnlineUsers();
      const stateData: StateData = { state: 'connected' as Object };
      this.notifyCallbacks({ type: 'connection_state', data: stateData });
    });

    this.ws.on('message', (err: Error, value: string | ArrayBuffer) => {
      if (err !== null && err !== undefined) return;
      const msgStr: string = typeof value === 'string' ? value : '';
      if (msgStr.length === 0) return;
      try {
        const parsed: Record<string, Object> = JSON.parse(msgStr) as Record<string, Object>;
        const msgType: string = (parsed['type'] as string) ?? '';
        const msgData: Object = parsed['data'] as Object;

        if (msgType === 'ping') {
          const pongMsg: WsSendMsg = { type: 'pong' };
          this.send(pongMsg);
          return;
        }

        if (msgType === 'user_status') {
          const data: Record<string, Object> = msgData as Record<string, Object>;
          const uid: number = (data['userId'] as number) ?? 0;
          const online: boolean = (data['online'] as boolean) ?? false;
          if (online) {
            this._onlineUsers.add(uid);
          } else {
            this._onlineUsers.delete(uid);
          }
        }

        this.notifyCallbacks({ type: msgType, data: msgData });
      } catch (e) {
      }
    });

    this.ws.on('close', () => {
      this._connectionState = 'disconnected';
      this.clearTimers();
      const stateData: StateData = { state: 'disconnected' as Object };
      this.notifyCallbacks({ type: 'connection_state', data: stateData });
      if (this.shouldReconnect) {
        this.scheduleReconnect();
      }
    });

    this.ws.on('error', () => {
      this._connectionState = 'disconnected';
      this.clearTimers();
      if (this.shouldReconnect) {
        this.scheduleReconnect();
      }
    });

    this.ws.connect(url);
  }

  private scheduleReconnect(): void {
    this._connectionState = 'reconnecting';
    const delay: number = Math.min(1000 * Math.pow(2, this.reconnectAttempts), this.maxReconnectDelay);
    this.reconnectAttempts++;
    const stateData: StateData = { state: 'reconnecting' as Object };
    this.notifyCallbacks({ type: 'connection_state', data: stateData });
    this.reconnectTimer = setTimeout(() => {
      this.doConnect();
    }, delay) as number;
  }

  private startHeartbeat(): void {
    this.heartbeatTimer = setInterval(() => {
      if (this._connectionState === 'connected') {
        const pingMsg: WsSendMsg = { type: 'ping' };
        this.send(pingMsg);
      }
    }, 25000) as number;
  }

  private clearTimers(): void {
    if (this.reconnectTimer !== -1) {
      clearTimeout(this.reconnectTimer);
      this.reconnectTimer = -1;
    }
    if (this.heartbeatTimer !== -1) {
      clearInterval(this.heartbeatTimer);
      this.heartbeatTimer = -1;
    }
  }

  private notifyCallbacks(msg: WsMessage): void {
    for (let i = 0; i < this.callbacks.length; i++) {
      this.callbacks[i](msg);
    }
  }

  private async fetchOnlineUsers(): Promise<void> {
    const result = await ApiService.getOnlineUsers();
    if (result.code === 200 && result.data !== undefined) {
      const arr: Object[] = result.data as Object[];
      this._onlineUsers.clear();
      for (let i = 0; i < arr.length; i++) {
        const uid: number = (arr[i] as number) ?? 0;
        if (uid > 0) {
          this._onlineUsers.add(uid);
        }
      }
      const emptyData: Record<string, Object> = {};
      this.notifyCallbacks({ type: 'online_users_loaded', data: emptyData });
    }
  }
}
