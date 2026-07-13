# 鸿蒙端 API 调用模板

> 以下代码已封装在 `service/ApiService.ets` 中，**不要修改该文件**。
> 组员在页面中直接调用即可。

## 基础调用方式

```typescript
// 无需登录的接口（needToken = false）
const result = await ApiService.request(http.RequestMethod.GET, '/api/public/colleges', undefined, false)

// 需要 Token 的接口
const result = await ApiService.getProfile()
```

> ⚠️ `ApiService.request` 第一个参数是 `http.RequestMethod` 枚举，不是字符串。需要 `import { http } from '@kit.NetworkKit'`。

## 已封装好的全部 API 函数

### 1️⃣ 用户相关

```typescript
// 登录
const result = await ApiService.login('2023101010', '123456')
// 登录成功后 token 和 userInfo 已自动存入 AppStore

// 注册 — 传中文名称字符串，不是 ID！
const result = await ApiService.register({
  name: '张三',
  studentId: '2023101010',
  phone: '13800000000',       // 必填
  grade: '2023',
  college: '计算机学院',      // 字符串，不是 collegeId
  major: '物联网工程',        // 字符串，不是 majorId
  className: '物联网2301',    // 字符串，不是 classId
  hometown: '北京',           // 可选
  password: '123456'
})

// 获取个人信息
const result = await ApiService.getProfile()

// 修改密码
const result = await ApiService.changePassword('旧密码', '新密码')

// 上传头像（传入图片 URI，返回图片 URL 字符串）
const imageUrl = await ApiService.uploadAvatar('file://...')
```

### 2️⃣ 公共数据

```typescript
// 获取学院列表
const result = await ApiService.getPublicColleges()
// 解析：const list = ApiService.parsePickerOptions(result.data)

// 获取专业列表
const result = await ApiService.getPublicMajors(1)  // collegeId: number

// 获取班级列表
const result = await ApiService.getPublicClasses('2023', 1, 1)
// grade: string, collegeId: number, majorId: number

// 获取年级列表
const result = await ApiService.getPublicGrades()

// 获取商品分类
const result = await ApiService.getProductCategories()
// 解析：const list = ApiService.parseProductCategoryList(result.data)
```

### 3️⃣ 课表

```typescript
// 获取当前学期
const result = await ApiService.getCurrentSemester()
// 解析：const s = ApiService.parseSemester(result.data)

// 获取我的课表
const result = await ApiService.getMySchedule(1)  // semesterId: number
// 解析：const list = ApiService.parseScheduleList(result.data)
// ScheduleVO 返回字段：id, courseName, classroom, dayOfWeek, startSection, endSection,
//   startWeek, endWeek, weekParity, teacherName
// weekParity: "all"=每周 "odd"=单周 "even"=双周
```

### 4️⃣ 公告

```typescript
const result = await ApiService.getAnnouncementList('notice')
// 解析：const list = ApiService.parseAnnouncementList(result.data)
// ⚠️ 内部 needToken=true，需登录后调用
```

### 5️⃣ 二手交易

```typescript
// 获取商品列表
const result = await ApiService.getProductList(0)  // categoryId, 0=全部

// 搜索商品（未封装，用 request）
const result = await ApiService.request(http.RequestMethod.GET, '/api/product/search?keyword=教材', undefined, false)

// 搜索联想词（未封装，用 request）
const result = await ApiService.request(http.RequestMethod.GET, '/api/product/search-suggest?keyword=教', undefined, false)

// 商品详情 — needToken=true，会记录浏览历史
const result = await ApiService.getProductDetail(1)

// 发布商品
const result = await ApiService.publishProduct({
  categoryId: 1, title: '二手高数书', description: '9成新',
  price: 15.00, originalPrice: 45.00,
  conditionLevel: 2,   // 1=全新 2=几乎全新 3=轻微痕迹 4=明显痕迹 5=成色一般
  images: ['/uploads/1.jpg'],
  campusLocation: '一食堂'
})

// 我的发布
const result = await ApiService.getMyProducts()

// 更新商品状态
const result = await ApiService.updateProductStatus(1, 2)
// 状态: 0=待审核 1=上架 2=已交易 3=审核拒绝

// 删除商品
const result = await ApiService.deleteProduct(1)

// 选图 + 上传图片
const uris: string[] = await ApiService.pickImages()  // 打开相册选图，返回 URI 数组
const url: string = await ApiService.uploadImage(uri)  // 上传单张，返回 "/uploads/xxx.jpg"
```

### 6️⃣ 收藏

```typescript
// 我的收藏
const result = await ApiService.getMyFavorites()
// 解析：const list = ApiService.parseFavoriteList(result.data)
// FavoriteVO 字段：id, productId, productTitle, productPrice, productImages(String!), productStatus, sellerName, createTime
// ⚠️ productImages 是 JSON 字符串，parseFavoriteList 内部已拆为数组

// 添加收藏
const result = await ApiService.addFavorite(1)

// 取消收藏
const result = await ApiService.removeFavorite(1)

// 检查是否已收藏
const result = await ApiService.checkFavorite(1)
// result.data -> true/false
```

### 7️⃣ 浏览记录

```typescript
// 记录浏览（未封装，用 request）
await ApiService.request(http.RequestMethod.POST, '/api/browse/record?productId=1', undefined, true)

// 我的浏览记录
const result = await ApiService.getMyBrowseHistory()
// BrowseHistoryVO 字段：id, productId, title, price(Double), originalPrice(String),
//   images(String), categoryName, campusLocation, conditionLevel, browseTime, sellerId, sellerName, status

// 清空浏览记录
const result = await ApiService.clearBrowseHistory()
```

### 8️⃣ 聊天

```typescript
// 获取会话列表
const result = await ApiService.getConversations()
// 解析：const list = ApiService.parseConversationList(result.data)
// ConversationVO 字段：id, otherUserId, otherUserName, lastMessage, lastTime, unreadCount, productId
// ⚠️ 无 otherUserAvatar 字段

// 获取消息
const result = await ApiService.getMessages(1)  // conversationId: number
// 解析：const list = ApiService.parseMessageList(result.data)

// 发送消息
const result = await ApiService.sendMessage(1, '你好', 'text')

// 创建新会话
const result = await ApiService.createConversation(2, 1)
// otherUserId: number, productId: number

// 获取未读消息数
const result = await ApiService.getUnreadCount()
// result.data.unreadCount

// 在线用户
const result = await ApiService.getOnlineUsers()
// result.data -> [1, 2, 5]  在线用户ID数组
```

### 9️⃣ 空教室

```typescript
// 获取教学楼列表（直接返回 string[]）
const buildings: string[] = await ApiService.getBuildings()
// -> ["1教", "2教", "3教", "4教", "5教"]

// 查询空教室（直接返回 EmptyRoom[]）
const rooms: EmptyRoom[] = await ApiService.getEmptyRooms('1教', 1, 3, 10)
// building, dayOfWeek, section, week
// building 和 week 可选（传空字符串/0 跳过）

// 教室课表（直接返回 CourseSchedule[]）
const list: CourseSchedule[] = await ApiService.getRoomSchedule('1教101', 1)
// classroom, dayOfWeek（dayOfWeek=0 返回全部）
```

### 🔟 失物招领

```typescript
// 获取列表（直接返回 LostFoundItem[]）
const list: LostFoundItem[] = await ApiService.getLostFoundList(1, '校园卡', 1)
// type: 1=寻物 2=招领 0=全部
// ⚠️ images 字段是 JSON 字符串，需 JSON.parse 或手动拆分

// 发布失物/招领 — 传 LostFoundItem 对象
const lf = new LostFoundItem()
lf.type = 1
lf.title = '丢失校园卡'
lf.description = '在二教丢失'
lf.images = '["/uploads/1.jpg"]'   // JSON 字符串，无图传 '[]'
lf.locationDesc = '二教203'
lf.category = '证件'
lf.contactPhone = '13800000000'    // 必填
const result = await ApiService.publishLostFound(lf)

// 我的失物
const result = await ApiService.getMyLostFoundList()

// 删除/标记完成
const result = await ApiService.deleteLostFound(1)
```

### 1️⃣1️⃣ 社团活动

```typescript
// 活动列表
const result = await ApiService.getClubActivityList('篮球', 1)
// club 可选值：排球/篮球/足球/羽毛球/乒乓球（无"社"字）

// 活动详情
const result = await ApiService.getClubActivityDetail(1)
// 携带 Token 时返回 registered + participantNames 字段

// 发布活动
const result = await ApiService.publishClubActivity({
  club: '篮球', title: '迎新赛',
  description: '欢迎新社员', venue: '篮球场',
  activityTime: '2024-09-10T15:00:00', maxParticipants: 20
})

// 报名/取消
const result = await ApiService.registerClubActivity(1)
const result = await ApiService.cancelClubActivity(1)

// 我的活动
const result = await ApiService.getMyClubActivities()
```

### 1️⃣2️⃣ 投诉与信用分

```typescript
// 提交投诉
const result = await ApiService.submitComplaint({
  defendantId: 2, productId: 1, reason: '虚假宣传'
})

// 我的投诉
const result = await ApiService.getMyComplaints()

// 信用分
const result = await ApiService.getCreditScore()
// result.data.creditScore  result.data.canTrade
```

### 🛠 图片 URL 拼接

```typescript
// 后端返回的图片路径是相对路径：/uploads/xxx.jpg
// 显示时需拼接完整 URL：
const fullUrl = `${ApiService.baseUrl}/uploads/xxx.jpg`
// 或自己封装 getImageUrl：
static getImageUrl(path: string): string {
  if (path.startsWith('http')) return path;
  return `${ApiService.baseUrl}${path}`;
}
```

---

## ⚠️ 给组员的铁律

1. **不要修改 `ApiService.ets`、`AppStore.ets`、`Models.ets`**
2. **新增页面调用 API 时，先看一下 `ApiService.ets` 里有没有现成的方法，有就直接用**
3. **没有现成方法的接口，用 `ApiService.request(http.RequestMethod.XXX, path, data, needToken)` 调用，不要自己 `http.createHttp()`**
4. **`result.data` 是 `Object` 类型，需要 `as Record<string, Object>` 转型或用 `parseXXX` 方法**
5. **解析列表用已写好的 `parseXXXList` 方法，不要自己写 for 循环解析**
6. **ArkTS 限制：不能用 `any`、不能解构、对象字面量必须有显式类型、`null as string` 会崩溃需先判空**
