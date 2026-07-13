# 校园助手 - czh-campus-starter

## 分工

| 人 | 负责 | 项目 |
|---|---|---|
| **白雪峰** | 课表 / 空教室 / 改密码 / 个人中心（5页） | CampusAssistant |
| **谢易霖** | 商品 / 收藏 / 浏览 / 投诉 / 发布（6页） | CampusAssistant |
| **何隆** | 聊天 / 失物招领 / 社团活动 / 公告（6页） | CampusAssistant |
| **杨晨** | Web 管理员端（8页） | web-admin |
| **组长** | 后端 + 核心文件 + 兜底 | Campus-Server |

## 快速开始

### 后端（组长部署）
```bash
# 1. 导入数据库
mysql -u root < campusdb2.sql

# 2. 修改 application.yml 里的数据库密码

# 3. 启动
cd Campus-Server
mvn spring-boot:run
```

### 鸿蒙端（白雪峰/谢易霖/何隆）
用 DevEco Studio 打开 `CampusAssistant/`
- API 调用见 `04-api-template.md`
- 接口定义见 `02-api-doc.md`
- 数据库表结构见 `01-database.md`

### Web 管理端（杨晨）
```bash
cd web-admin
npm install
npm run dev
```

## 铁律
- `ApiService.ets` / `AppStore.ets` / `WebSocketService.ets` / `Models.ets` 不许改
- API 路径必须跟 `02-api-doc.md` 一致
- 新文件放在指定目录，不允许自己乱建目录
