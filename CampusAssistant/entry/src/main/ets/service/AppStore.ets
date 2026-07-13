import { UserInfo } from '../model/Models';
import { common } from '@kit.AbilityKit';
import { WebSocketService } from './WebSocketService';

export class AppStore {
  private static instance: AppStore | undefined = undefined;
  token: string = '';
  userInfo: UserInfo = new UserInfo();
  isLoggedIn: boolean = false;
  context: common.UIAbilityContext | undefined = undefined;

  static getInstance(): AppStore {
    if (AppStore.instance === undefined) {
      AppStore.instance = new AppStore();
    }
    return AppStore.instance;
  }

  setContext(ctx: common.UIAbilityContext): void {
    this.context = ctx;
  }

  setToken(token: string): void {
    this.token = token;
    this.isLoggedIn = token.length > 0;
    if (this.isLoggedIn) {
      WebSocketService.getInstance().connect();
    }
  }

  setUserInfo(info: UserInfo): void {
    this.userInfo = info;
  }

  logout(): void {
    WebSocketService.getInstance().disconnect();
    this.token = '';
    this.isLoggedIn = false;
    this.userInfo = new UserInfo();
  }
}
