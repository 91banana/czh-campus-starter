# 校园助手 — API 接口文档

## 通用规范

- **基础 URL：** `http://服务器IP:8080`
- **认证方式：** Bearer Token（登录后返回的 token）
- **请求头：** `Content-Type: application/json`，需登录的接口加 `Authorization: Bearer {token}`
- **图片访问：** `http://服务器IP:8080/uploads/{filename}`
- **响应格式：**
```json
{
  "code": 200,        // 200=成功, 其他=失败
  "msg": "ok",        // 提示消息
  "data": { ... }     // 具体数据
}
```

> ⚠️ **铁律：接口路径、请求方法、字段名必须与本文档完全一致。自己脑补的地址一律打回。**

---

## 白名单接口（无需 Token）

| 路径 | 说明 |
|------|------|
| `POST /api/user/login` | 登录 |
| `POST /api/user/register` | 注册 |
| `GET /api/public/**` | 公共数据（学院/专业/班级/年级/分类） |
| `GET /uploads/**` | 图片访问 |
| `GET /api/classroom/**` | 空教室查询 |
| `GET /ws/**` | WebSocket 连接 |
| `GET /api/product/list` | 商品列表 |
| `GET /api/product/search` | 搜索商品 |
| `GET /api/product/search-suggest` | 搜索联想 |
| `GET /api/product/{id}` | 商品详情（有 Token 会记录浏览） |
| `GET /api/lost-found/list` | 失物招领列表 |
| `GET /api/lost-found/detail` | 失物招领详情 |

**其余接口均需 Token，未携带返回 401。**包括 `/api/announcement/list`、`/api/schedule/**`、`/api/club-activity/**` 均不在白名单内，必须携带 Token。
**白名单接口携带 Token 时后端也会解析 userId，用于可选登录功能。**

---

## 1. 用户模块 (`/api/user`)

### POST `/api/user/login` 🔓
```json
// 请求
{ "studentId": "2023101010", "password": "123456" }
// 响应 data
{
  "token": "eyJhbGciOi...",
  "user": {
    "id": 1, "studentId": "2023101010", "name": "张三",
    "avatar": "", "college": "计算机学院", "major": "物联网工程",
    "grade": "2023", "className": "物联网2301",
    "hometown": "", "phone": "", "role": "student",
    "creditScore": 100
  }
}
```

### POST `/api/user/register` 🔓
```json
{
  "name": "张三",
  "studentId": "2023101010",
  "phone": "13800000000",
  "grade": "2023",
  "college": "计算机学院",
  "major": "物联网工程",
  "className": "物联网2301",
  "hometown": "北京",
  "password": "123456"
}
```
> ⚠️ 注册传的是**中文名称字符串**（college/major/className），不是 ID。phone 为必填，hometown 可选。

### GET `/api/user/profile` 🔒
响应 data：同登录返回的 user 对象

### POST `/api/user/change-password` 🔒
```json
{ "oldPassword": "xxx", "newPassword": "xxx" }
```

### POST `/api/user/upload-avatar` 🔒
`multipart/form-data`，字段名 `file`
响应 data：图片URL字符串 `"/uploads/xxx.jpg"`

---

## 2. 公共数据 (`/api/public`) 🔓

### GET `/api/public/colleges`
响应 data：`[{ "id": 1, "name": "计算机学院" }]`

### GET `/api/public/majors?collegeId=1`
响应 data：`[{ "id": 1, "collegeId": 1, "name": "物联网工程" }]`

### GET `/api/public/classes?grade=2023&collegeId=1&majorId=1`
响应 data：`[{ "id": 1, "name": "物联网2301", "college": "计算机学院", "major": "物联网工程", "grade": "2023", "majorId": 1 }]`

### GET `/api/public/grades`
响应 data：`["2023", "2024", ...]`

### GET `/api/public/product-categories`
响应 data：`[{ "id": 1, "name": "教材", "icon": "...", "parentId": 0, "sortOrder": 1 }]`

---

## 3. 课表 (`/api/schedule`)

### GET `/api/schedule/my?semesterId=1` 🔒
semesterId 可选，不传=当前学期
```json
[{
  "id": 1, "courseName": "高等数学", "classroom": "A102",
  "dayOfWeek": 1, "startSection": 1, "endSection": 2,
  "startWeek": 1, "endWeek": 16, "weekParity": "all",
  "teacherName": "王老师"
}]
```
**weekParity：** "all"=每周 "odd"=单周 "even"=双周

### GET `/api/schedule/current-semester` 🔒
```json
{ "id": 1, "name": "2024-2025-1", "startDate": "2024-09-01", "endDate": "2025-01-15", "weekCount": 20, "isCurrent": 1 }
```

---

## 4. 公告 (`/api/announcement`) 🔒

### GET `/api/announcement/list?category=notice`
category 可选（不传返回全部）。分类值：notice/academic/club/logistic/urgent
```json
[{
  "id": 1, "title": "期末通知", "content": "...", "summary": "...",
  "category": "notice", "isTop": 1, "isPublished": 1,
  "publishTime": "2024-06-20T10:00:00", "expireTime": null
}]
```

---

## 5. 二手交易 (`/api/product`)

### GET `/api/product/list?categoryId=1` 🔓
categoryId 可选，0或不传=全部
```json
[{
  "id": 1, "sellerId": 6, "sellerName": "张三",
  "categoryId": 1, "title": "二手高数书", "description": "9成新",
  "price": 15.00, "originalPrice": 45.00, "conditionLevel": 2,
  "images": ["/uploads/1.jpg"], "status": 1,
  "viewCount": 10, "campusLocation": "一食堂", "createTime": "..."
}]
```
**conditionLevel：** 1=全新 2=几乎全新 3=轻微使用痕迹 4=明显使用痕迹 5=成色一般
**status：** 0=待审核 1=上架 2=已交易 3=审核拒绝
> ⚠️ ProductVO 无 sellerAvatar、无 favoriteCount 字段

### GET `/api/product/search?keyword=教材` 🔓
响应同商品列表格式

### GET `/api/product/search-suggest?keyword=教` 🔓
响应 data：`["教材", "教辅", ...]`

### GET `/api/product/{id}` 🔓
同商品列表单个对象。携带 Token 时自动记录浏览历史

### POST `/api/product/publish` 🔒
⚠️ creditScore < 85 返回错误"信用分不足85，无法发布商品"
```json
{
  "categoryId": 1, "title": "二手高数书", "description": "9成新",
  "price": 15.00, "originalPrice": 45.00,
  "conditionLevel": 2, "images": ["/uploads/1.jpg"],
  "campusLocation": "一食堂"
}
```

### GET `/api/product/my-products` 🔒
同商品列表格式

### POST `/api/product/update-status?productId=1&status=2` 🔒
后端不校验 status 值，传入的任何整数都会直接写入。建议前端只传：1=上架 2=已交易 3=审核拒绝

### POST `/api/product/delete?productId=1` 🔒

### POST `/api/product/upload-image` 🔒
`multipart/form-data`，字段名 `file`
响应 data：图片URL字符串 `"/uploads/xxx.jpg"`

---

## 6. 收藏 (`/api/favorite`) 🔒

### GET `/api/favorite/list`
```json
[{
  "id": 1, "productId": 1, "productTitle": "...",
  "productPrice": 15.00, "productImages": "[\"url\"]",
  "productStatus": 1, "sellerName": "张三", "createTime": "..."
}]
```
> ⚠️ productImages 是 String 类型（JSON 字符串），不是数组

### POST `/api/favorite/add?productId=1`

### POST `/api/favorite/remove?productId=1`

### GET `/api/favorite/check?productId=1`
响应 data：`true` 或 `false`

---

## 7. 浏览记录 (`/api/browse`) 🔒

### POST `/api/browse/record?productId=1`

### GET `/api/browse/my-history`
```json
[{
  "id": 1, "productId": 1, "title": "二手高数书",
  "price": 15.0, "originalPrice": "45.00",
  "images": "[\"/uploads/1.jpg\"]", "categoryName": "教材",
  "campusLocation": "一食堂", "conditionLevel": 2,
  "browseTime": "...", "sellerId": 6, "sellerName": "张三", "status": 1
}]
```
> ⚠️ 字段名是 `title`/`price`/`images`，不是 productTitle/productPrice/productImage。images 和 originalPrice 是 String 类型。

### POST `/api/browse/clear`

---

## 8. 聊天 (`/api/chat`) 🔒

### GET `/api/chat/conversations`
```json
[{
  "id": 1, "otherUserId": 2, "otherUserName": "李四",
  "lastMessage": "你好", "lastTime": "...",
  "unreadCount": 1, "productId": 1
}]
```
> ⚠️ ConversationVO 无 otherUserAvatar 字段

### GET `/api/chat/messages/{conversationId}`
调用后自动标记该对话消息为已读
```json
[{
  "id": 1, "conversationId": 1, "senderId": 1,
  "content": "你好", "msgType": "text",
  "createTime": "...", "isMine": true
}]
```
**msgType：** text=文本 image=图片 product=商品卡片

### POST `/api/chat/send`
```json
{ "conversationId": 1, "content": "你好", "msgType": "text" }
```
msgType=product 时 content 为 JSON：`{ "productId":1, "title":"高数", "price":"25", "coverImage":"/uploads/1.jpg" }`

### GET `/api/chat/unread-count`
```json
{ "unreadCount": 3 }
```

### POST `/api/chat/create-conversation`
同一对用户只创建一个对话，重复调用返回已有对话ID
```json
{ "otherUserId": 2, "productId": 1 }
```
响应 data：`{ "conversationId": 1 }`

### GET `/api/chat/online-users`
响应 data：`[1, 2, 5]`（在线用户ID数组）

---

## 9. WebSocket 实时通信

### 连接地址
`ws://服务器IP:8080/ws/chat?token={jwt_token}`

### 客户端发送消息格式
```json
{ "type": "message", "conversationId": 1, "content": "你好", "msgType": "text" }
{ "type": "mark_read", "conversationId": 1 }
{ "type": "pong" }
```

### 服务端推送消息格式
```json
{ "type": "message", "data": { "id":1, "conversationId":1, "senderId":1, "content":"你好", "msgType":"text", "createTime":"...", "isMine":true } }
{ "type": "unread_update", "data": { "unreadCount": 2 } }
{ "type": "user_status", "data": { "userId": 7, "online": true } }
{ "type": "ping" }
{ "type": "connection_state", "data": { "state": "connected" } }
```

**心跳：** 服务端 30s 发 ping，客户端需回 pong；超时 60s 未响应断开连接
**断线重连：** 客户端建议指数退避重连

---

## 10. 空教室 (`/api/classroom`) 🔓

### GET `/api/classroom/buildings`
响应 data：`["1教", "2教", "3教", "4教", "5教"]`

### GET `/api/classroom/empty?building=1教&dayOfWeek=1&section=3&week=10`
- building 可选
- dayOfWeek 必填（1-7）
- section 必填（节次1-12）
- week 可选
```json
[{
  "id": 1, "building": "1教", "roomNo": "101",
  "capacity": 60, "hasProjector": 1, "hasAc": 1, "type": "普通教室"
}]
```

### GET `/api/classroom/schedule?classroom=1教101&dayOfWeek=1`
dayOfWeek 可选
```json
[{
  "courseName": "高等数学", "dayOfWeek": 1,
  "startSection": 1, "endSection": 2,
  "startWeek": 1, "endWeek": 16
}]
```

---

## 11. 失物招领 (`/api/lost-found`)

### GET `/api/lost-found/list?type=1&status=1&keyword=校园卡&page=1&size=20` 🔓
- type 可选（1=寻物 2=招领，不传返回全部）
- status 可选（1=进行中 2=已找到）
- keyword 可选
```json
[{
  "id": 1, "userId": 1, "userName": "张三",
  "type": 1, "title": "丢失校园卡", "description": "...",
  "images": "[\"/uploads/1.jpg\"]", "locationDesc": "二教",
  "category": "证件", "contactPhone": "138...",
  "status": 1, "createTime": "..."
}]
```
**type：** 1=寻物 2=招领
**status：** 1=进行中 2=已找到/已归还
> ⚠️ images 是 String（JSON 字符串），不是数组

### GET `/api/lost-found/detail?id=1` 🔓
同列表单个对象，含 contactPhone

### POST `/api/lost-found/publish` 🔒
⚠️ contactPhone 必填；images 为 JSON 字符串 `"[]"`，无图传 `"[]"`；creditScore < 85 无法发布
```json
{
  "type": 1, "title": "丢失校园卡", "description": "...",
  "images": "[\"/uploads/1.jpg\"]", "locationDesc": "二教",
  "category": "证件", "contactPhone": "13800000000"
}
```

### GET `/api/lost-found/my-list` 🔒

### POST `/api/lost-found/delete?id=1` 🔒
真删除（DELETE 操作），不是改状态

---

## 12. 社团活动 (`/api/club-activity`)

### GET `/api/club-activity/list?club=篮球&status=1` 🔒
- club 可选（排球/篮球/足球/羽毛球/乒乓球）
- status 可选（1=报名中 2=已满 3=已结束）
- 列表查询时自动清理过期活动（数据库真删除）
- `registered` 字段默认返回 false
```json
[{
  "id": 1, "userId": 1, "userName": "张三", "club": "篮球",
  "title": "迎新赛", "description": "...", "venue": "篮球场",
  "activityTime": "2024-09-10T15:00:00",
  "maxParticipants": 20, "currentParticipants": 5,
  "status": 1, "createTime": "..."
}]
```

### GET `/api/club-activity/detail?id=1` 🔒
追加 `registered: true/false` + `participantNames: ["张三","李四"]`

### POST `/api/club-activity/publish` 🔒
```json
{
  "club": "篮球", "title": "迎新赛", "description": "...",
  "venue": "篮球场", "activityTime": "2024-09-10T15:00:00",
  "maxParticipants": 20
}
```

### POST `/api/club-activity/register?activityId=1` 🔒

### POST `/api/club-activity/cancel?activityId=1` 🔒

### GET `/api/club-activity/my-activities` 🔒
只返回未过期的活动

---

## 13. 投诉与信用分 (`/api/complaint`)

### POST `/api/complaint/submit` 🔒
投诉后被告信用分 -5，不能投诉自己
```json
{ "defendantId": 2, "productId": 1, "reason": "虚假宣传" }
```

### GET `/api/complaint/my-complaints` 🔒
```json
[{
  "id": 1, "defendantId": 2, "defendantName": "李四",
  "productId": 1, "reason": "虚假宣传",
  "status": 0, "createTime": "..."
}]
```
**status：** 0=待处理 1=已处理

### GET `/api/complaint/credit-score` 🔒
```json
{ "creditScore": 95, "canTrade": true }
```
canTrade 根据 creditScore >= 85 判断

### GET `/api/complaint/list` 🔒（管理端，需 admin）
所有投诉记录

### POST `/api/complaint/process/{id}` 🔒（管理端，需 admin）
标记投诉为已处理

---

## 14. 管理员接口 (`/api/admin`) 🔒（需 admin 角色 Token）

### 学期管理
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/semester/list` | 学期列表 |
| POST | `/api/admin/semester/add` | 新增 `{ name, startDate, endDate, weekCount, isCurrent }` |
| PUT | `/api/admin/semester/{id}` | 修改学期 |
| PUT | `/api/admin/semester/{id}/set-current` | 设为当前学期 |
| DELETE | `/api/admin/semester/{id}` | 删除学期 |

### 班级管理
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/class/list?grade=2023&collegeId=1&majorId=1` | 班级列表 |
| GET | `/api/admin/college/list` | 学院列表 |
| GET | `/api/admin/major/list?collegeId=1` | 专业列表 |
| GET | `/api/admin/class/grades` | 年级列表 |
| POST | `/api/admin/class/add` | 新增班级 `{ name, college, major, grade, majorId }` |
| POST | `/api/admin/class/batch-add` | 批量新增 `{ grade, collegeIds: [1], majorIds: [1], classCount: 2 }` |
| DELETE | `/api/admin/class/by-grade?grade=2023` | 按年级删除 |
| DELETE | `/api/admin/class/{id}` | 删除班级 |

### 课表管理
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/timetable/class?classId=1&semesterId=1` | 班级课表 |
| POST | `/api/admin/timetable/import` | 导入课表（Excel multipart，字段 file + classId + semesterId） |

### 公告管理
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/announcement/list` | 公告列表 |
| POST | `/api/admin/announcement/publish` | 发布（见下方请求体） |
| PUT | `/api/admin/announcement/{id}` | 修改公告（同发布请求体） |
| DELETE | `/api/admin/announcement/{id}` | 删除公告 |

发布公告请求体：
```json
{
  "title": "期末安排", "content": "...", "summary": "摘要",
  "category": "academic", "isTop": 0, "isPublished": 1,
  "targetTags": "[\"计算机学院\"]"
}
```
> ⚠️ summary/isTop/isPublished/targetTags 可选；category 必填

### 商品审核
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/product/pending` | 待审核商品列表 |
| POST | `/api/admin/product/approve?productId=1` | 审核通过 |
| POST | `/api/admin/product/reject?productId=1` | 审核拒绝 |

### 数据统计
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/stats/overview` | 总览 `{ userCount, productCount, lostFoundCount, announcementCount }` |
| GET | `/api/admin/stats/products-by-category` | `[{ name, value }]` |
| GET | `/api/admin/stats/products-by-status` | `[{ name, value }]` |
| GET | `/api/admin/stats/users-by-college` | `[{ name, value }]` |

> ⚠️ 统计字段名是 `value` 不是 `count`；overview 无 `tradeCount`，是 `lostFoundCount`

---

## 附录：接口总数 80 个

| Controller | 数量 |
|---|---|
| AdminController | 22 |
| ProductController | 9 |
| ChatController | 6 |
| ClubActivityController | 6 |
| ComplaintController | 5 |
| LostFoundController | 5 |
| PublicController | 5 |
| UserController | 5 |
| StatsController | 4 |
| FavoriteController | 4 |
| BrowseHistoryController | 3 |
| ClassroomController | 3 |
| ScheduleController | 2 |
| AnnouncementController | 1 |
