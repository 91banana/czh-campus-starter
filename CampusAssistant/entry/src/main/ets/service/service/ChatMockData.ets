import { Conversation, ChatMessage } from '../model/Models';

export class ChatMockData {
  static getConversations(): Conversation[] {
    const list: Conversation[] = [];
    const c1: Conversation = new Conversation();
    c1.id = 1; c1.otherUserId = 2; c1.otherUserName = '李同学';
    c1.otherUserAvatar = ''; c1.lastMessage = '这本书还在吗？';
    c1.lastTime = '10:30'; c1.unreadCount = 2; c1.productId = 1;
    list.push(c1);

    const c2: Conversation = new Conversation();
    c2.id = 2; c2.otherUserId = 3; c2.otherUserName = '王学姐';
    c2.otherUserAvatar = ''; c2.lastMessage = 'iPad可以便宜点吗？';
    c2.lastTime = '昨天'; c2.unreadCount = 0; c2.productId = 2;
    list.push(c2);

    const c3: Conversation = new Conversation();
    c3.id = 3; c3.otherUserId = 4; c3.otherUserName = '张同学';
    c3.otherUserAvatar = ''; c3.lastMessage = '好的，明天致远楼见';
    c3.lastTime = '前天'; c3.unreadCount = 0; c3.productId = 1;
    list.push(c3);

    return list;
  }

  static getMessages(): ChatMessage[] {
    const list: ChatMessage[] = [];
    const m1: ChatMessage = new ChatMessage();
    m1.id = 1; m1.senderId = 2; m1.content = '你好，高等数学这本书还在吗？'; m1.createTime = '10:25'; m1.isMine = false;
    list.push(m1);

    const m2: ChatMessage = new ChatMessage();
    m2.id = 2; m2.senderId = 1; m2.content = '在的，几乎全新'; m2.createTime = '10:26'; m2.isMine = true;
    list.push(m2);

    const m3: ChatMessage = new ChatMessage();
    m3.id = 3; m3.senderId = 2; m3.content = '可以便宜点吗？10块出不出？'; m3.createTime = '10:28'; m3.isMine = false;
    list.push(m3);

    const m4: ChatMessage = new ChatMessage();
    m4.id = 4; m4.senderId = 1; m4.content = '可以，10块给你了'; m4.createTime = '10:29'; m4.isMine = true;
    list.push(m4);

    const m5: ChatMessage = new ChatMessage();
    m5.id = 5; m5.senderId = 2; m5.content = '这本书还在吗？'; m5.createTime = '10:30'; m5.isMine = false;
    list.push(m5);

    return list;
  }
}
