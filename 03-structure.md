# 校园助手 — 项目结构规范

## 一、鸿蒙端 (CampusAssistant) 目录结构

```
CampusAssistant/
├── entry/src/main/ets/
│   ├── pages/              ← 页面文件
│   │   ├── Index.ets                首页入口
│   │   ├── LoginPage.ets            登录
│   │   ├── RegisterPage.ets         注册
│   │   ├── MainPage.ets             主页（Tab容器）
│   │   ├── AiChatPage.ets           AI 对话
│   │   ├── ChatPage.ets             私聊
│   │   ├── ConversationListPage.ets 会话列表
│   │   ├── ProductDetailPage.ets    商品详情
│   │   ├── PublishPage.ets          发布商品
│   │   ├── MyPublishPage.ets        我的发布
│   │   ├── MyFavoritesPage.ets      我的收藏
│   │   ├── MyHistoryPage.ets        浏览历史
│   │   ├── MyComplaintsPage.ets     我的投诉
│   │   ├── MyLostFoundPage.ets      我的失物
│   │   ├── PasswordChangePage.ets   修改密码
│   │   ├── LostFoundListPage.ets    失物招领列表
│   │   ├── LostFoundDetailPage.ets  失物详情
│   │   ├── LostFoundPublishPage.ets 发布失物
│   │   ├── EmptyRoomPage.ets        空教室
│   │   ├── RoomSchedulePage.ets     教室课表
│   │   ├── ClubActivityListPage.ets    社团活动列表
│   │   ├── ClubActivityDetailPage.ets  活动详情
│   │   ├── ClubActivityPublishPage.ets 发布活动
│   │   └── ...                   ← 新页面放这里
│   │
│   ├── common/              ← 公共组件
│   │   └── Constants.ets       常量
│   │
│   ├── model/               ← 数据模型（已写好，别改）
│   │   └── Models.ets
│   │
│   └── service/             ← 服务层（已封装好，别改！）
│       ├── ApiService.ets       ← 所有 API 请求方法
│       ├── AppStore.ets         ← 全局状态（登录信息等）
│       ├── WebSocketService.ets ← WebSocket 连接/心跳/重连
│       ├── ChatMockData.ets     ← Mock 数据
│       └── MockData.ets         ← Mock 数据
│
├── entry/src/main/resources/
│   └── rawfile/             ← 静态资源
│
├── AppScope/                 ← 应用配置
└── oh_modules/               ← 第三方库
```

### 鸿蒙端开发规则

1. **页面** 统一放在 `pages/` 目录，文件名用大驼峰 + `Page.ets` 结尾
2. **组件** 统一放在 `common/` 目录
3. **API 请求** 只调用 `ApiService.ets` 里的方法，不允许自己写 `http.request`
4. **数据模型** 使用 `model/Models.ets` 里定义好的类，不允许自己定义新模型
5. **新增页面调用 API**：先看 `ApiService.ets` 有没有现成方法，没有就用 `ApiService.request(method, path, data, needToken)`
6. **ArkTS 限制**：不能用 `any`、不能解构、对象字面量必须有显式类型声明、`null as string` 会崩溃需先判空

---

## 二、后端 (Campus-Server) 目录结构

```
Campus-Server/
├── src/main/java/com/campus/
│   ├── controller/     ← 接口控制器
│   │   ├── UserController.java         用户
│   │   ├── PublicController.java       公共数据
│   │   ├── AdminController.java        管理员（学期/班级/课表/公告/审核）
│   │   ├── AnnouncementController.java 公告
│   │   ├── ProductController.java      商品
│   │   ├── FavoriteController.java     收藏
│   │   ├── BrowseHistoryController.java 浏览记录
│   │   ├── ChatController.java         聊天
│   │   ├── ClassroomController.java    空教室
│   │   ├── LostFoundController.java    失物招领
│   │   ├── ClubActivityController.java 社团活动
│   │   ├── ComplaintController.java    投诉/信用分
│   │   ├── ScheduleController.java     课表
│   │   └── StatsController.java        数据统计
│   │
│   ├── service/        ← 业务逻辑
│   ├── mapper/         ← 数据库访问
│   ├── entity/         ← 数据实体
│   ├── dto/            ← 数据传输对象
│   │   └── BatchClassRequest.java  批量添加班级请求体
│   ├── common/         ← 工具类
│   │   ├── Result.java      统一响应 Result.ok(data)/Result.error(msg)
│   │   ├── JwtUtil.java     JWT 工具
│   │   └── GlobalExceptionHandler.java
│   ├── config/         ← Spring 配置
│   │   ├── CorsConfig.java
│   │   ├── JwtAuthFilter.java
│   │   └── WebMvcConfig.java
│   └── websocket/      ← WebSocket 相关
│       ├── ChatWebSocketHandler.java  消息路由/心跳/在线广播/离线补推
│       ├── WebSocketConfig.java       注册 /ws/chat 端点
│       └── WebSocketHeartbeat.java    30s 定时 ping
│
├── src/main/resources/
│   ├── application.yml     ← 数据源配置
│   └── mapper/             ← MyBatis XML（当前为空，用了注解 SQL）
│
├── uploads/                ← 上传文件存储
├── pom.xml                 ← Maven 配置
└── target/                 ← 构建产物（不提交）
```

### 后端开发规则

1. 新增接口放现有 Controller 里，别建新 Controller 文件
2. 接口路径 `/api/模块名/...`，统一风格
3. 返回统一用 `Result.ok(data)` / `Result.error(msg)`，不是 `success()`
4. 需要登录的接口用 `HttpServletRequest` 的 `getAttribute("userId")` 获取当前用户
5. 数据库用 MyBatis-Plus，使用 `LambdaQueryWrapper` 查询
6. JwtAuthFilter 白名单见 02-api-doc.md

---

## 三、管理员前端 (web-admin) 目录结构

```
web-admin/
├── src/
│   ├── api/               ← API 封装
│   │   ├── request.ts        基础请求封装（axios + baseURL + token拦截器）
│   │   └── admin.ts          管理员 API 方法
│   │
│   ├── views/             ← 页面
│   │   ├── login/Index.vue       登录
│   │   ├── dashboard/Index.vue   仪表盘（数据大屏 ECharts）
│   │   ├── semester/Index.vue    学期管理
│   │   ├── schedule/Index.vue    课表管理（Excel导入）
│   │   ├── classManage/Index.vue 班级管理
│   │   ├── announcement/Index.vue 公告管理
│   │   ├── product/Index.vue     商品审核
│   │   └── complaint/Index.vue   投诉处理
│   │
│   ├── router/index.ts    ← 路由配置（createWebHashHistory）
│   ├── stores/user.ts     ← 用户状态（Pinia）
│   ├── components/        ← 公共组件
│   │   └── Layout.vue        侧边栏布局
│   ├── main.ts
│   └── App.vue
│
├── public/                ← 静态资源
├── dist/                  ← 构建产物（不提交）
├── package.json
└── vite.config.ts         ← Vite 代理 /api → localhost:8080
```

### 管理员端开发规则

1. 页面统一放 `views/模块名/Index.vue`
2. API 请求调用 `api/admin.ts` 里的方法，不允许直接 `fetch()`
3. 路由在 `router/index.ts` 注册
4. 使用 Naive UI 组件库（单独导入，不全局注册）
5. `useMessage` 需在 `NMessageProvider` 内使用
