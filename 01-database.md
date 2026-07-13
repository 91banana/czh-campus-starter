# 校园助手 — 数据库结构 (campusdb2)

## 总表概览

数据库共 **33 张表**，**有效 19 张**，**废弃 15 张**（复制过来的无用表，teachers 也无使用）。

---

## 有效表（19 张）

### 1. `users` — 用户表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | 用户ID |
| student_id | varchar(20) | UNIQUE, NOT NULL | 学号 |
| name | varchar(50) | NOT NULL | 姓名 |
| password | varchar(200) | NOT NULL | 加密密码 |
| avatar | varchar(500) | YES | 头像URL |
| college | varchar(50) | INDEX | 学院 |
| major | varchar(50) | YES | 专业 |
| grade | varchar(10) | YES | 年级 |
| phone | varchar(20) | YES | 手机 |
| hometown | varchar(50) | YES | 籍贯 |
| role | varchar(20) | DEFAULT 'student' | 角色(student/admin) |
| credit_score | int | NOT NULL, DEFAULT 100 | 信用分 |
| status | tinyint | DEFAULT 1 | 状态(1正常 0禁用) |
| create_time | datetime | DEFAULT CURRENT_TIMESTAMP | |
| update_time | datetime | DEFAULT CURRENT_TIMESTAMP, ON UPDATE | |
| class_id | bigint | INDEX | 班级ID |
| class_name | varchar(50) | YES | 班级名称 |
| province | varchar(20) | YES | 省份 |

### 2. `colleges` — 学院表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| name | varchar(50) | UNIQUE, NOT NULL | 学院名 |
| create_time | datetime | | |

### 3. `majors` — 专业表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| college_id | bigint | FK, NOT NULL | 所属学院ID |
| name | varchar(50) | NOT NULL | 专业名 |
| create_time | datetime | | |

### 4. `classes` — 班级表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| name | varchar(50) | NOT NULL, INDEX | 班级名(如 物联网2202) |
| college | varchar(50) | YES, INDEX | 学院 |
| major | varchar(50) | YES | 专业 |
| grade | varchar(10) | YES | 年级 |
| major_id | bigint | YES | 专业ID |
| head_teacher | varchar(50) | YES | 班主任 |
| student_count | int | DEFAULT 0 | 学生数 |
| create_time | datetime | | |

### 5. `semesters` — 学期表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| name | varchar(50) | NOT NULL | 学期名(如 2024-2025-1) |
| start_date | date | NOT NULL | |
| end_date | date | NOT NULL | |
| week_count | int | NOT NULL | 周数 |
| is_current | tinyint | DEFAULT 0 | 是否当前学期 |
| create_time | datetime | | |

### 6. `class_timetable` — 班级课表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| class_id | bigint | FK, NOT NULL | 班级ID |
| semester_id | bigint | NOT NULL | 学期ID |
| course_name | varchar(100) | NOT NULL | 课程名 |
| teacher | varchar(50) | YES | 教师名 |
| classroom | varchar(50) | YES | 教室 |
| day_of_week | int | NOT NULL | 星期(1-7) |
| start_week | int | DEFAULT 1 | 起始周 |
| end_week | int | DEFAULT 16 | 结束周 |
| start_section | int | NOT NULL | 开始节次 |
| end_section | int | NOT NULL | 结束节次 |
| color | varchar(20) | DEFAULT '#4A90D9' | 显示颜色 |
| remark | varchar(200) | YES | 备注 |

### 7. `announcements` — 公告表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| title | varchar(200) | NOT NULL | |
| content | text | NOT NULL | |
| summary | varchar(500) | YES | 摘要 |
| category | varchar(30) | INDEX | 分类(notice/academic/... ) |
| publisher_id | bigint | NOT NULL | 发布者ID |
| is_top | tinyint | DEFAULT 0 | 置顶 |
| is_published | tinyint | INDEX, DEFAULT 0 | 发布状态 |
| publish_time | datetime | INDEX | |
| expire_time | datetime | YES | 过期时间 |
| target_tags | json | YES | 目标标签 |
| create_time | datetime | | |

### 8. `products` — 二手商品表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| seller_id | bigint | FK, NOT NULL | 卖家ID |
| category_id | bigint | FK, NOT NULL | 分类ID |
| title | varchar(200) | NOT NULL | |
| description | text | YES | |
| price | decimal(10,2) | NOT NULL | |
| original_price | decimal(10,2) | YES | 原价 |
| condition_level | tinyint | NOT NULL | 新旧程度(1全新 2几乎全新 3轻微痕迹 4明显痕迹 5成色一般) |
| images | json | YES | 图片数组 |
| status | tinyint | INDEX, DEFAULT 1 | 0=待审核 1=售卖中 2=已售出 3=已拒绝 |
| view_count | int | DEFAULT 0 | |
| favorite_count | int | DEFAULT 0 | |
| tags | json | YES | |
| campus_location | varchar(100) | YES | 校区位置 |
| create_time | datetime | INDEX | |
| update_time | datetime | | |

### 9. `product_categories` — 商品分类
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| name | varchar(50) | NOT NULL | |
| icon | varchar(100) | YES | |
| parent_id | bigint | DEFAULT 0 | 父分类ID |
| sort_order | int | DEFAULT 0 | |
| create_time | datetime | | |

### 10. `favorites` — 收藏表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| user_id | bigint | FK, NOT NULL | |
| product_id | bigint | NOT NULL | |
| create_time | datetime | | |

### 11. `browse_history` — 浏览记录
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| user_id | bigint | FK, NOT NULL | |
| product_id | bigint | FK, NOT NULL | |
| browse_time | datetime | | |

### 12. `complaints` — 投诉表
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| complainant_id | bigint | NOT NULL | 投诉人 |
| defendant_id | bigint | FK, NOT NULL | 被投诉人 |
| product_id | bigint | YES | 关联商品 |
| reason | varchar(500) | NOT NULL | |
| status | tinyint | NOT NULL, DEFAULT 0 | 0=未处理 1=已处理 |
| create_time | datetime | | |

### 13. `chat_conversations` — 会话
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| user1_id | bigint | FK, NOT NULL | |
| user2_id | bigint | FK, NOT NULL | |
| product_id | bigint | YES | 关联商品(二手交易) |
| last_message | text | YES | 最后一条消息 |
| last_time | datetime | YES | |
| create_time | datetime | | |

### 14. `chat_messages` — 聊天消息
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| conversation_id | bigint | FK, NOT NULL | |
| sender_id | bigint | FK, NOT NULL | |
| content | text | YES | |
| msg_type | varchar(20) | DEFAULT 'text' | |
| is_read | tinyint | DEFAULT 0 | |
| create_time | datetime | | |

### 15. `club_activities` — 社团活动
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| user_id | bigint | NOT NULL | 发布者 |
| club | varchar(50) | INDEX | 社团名 |
| title | varchar(200) | NOT NULL | |
| description | text | YES | |
| venue | varchar(100) | NOT NULL | 地点 |
| activity_time | datetime | NOT NULL | |
| max_participants | int | DEFAULT 20 | |
| current_participants | int | DEFAULT 0 | |
| status | tinyint | INDEX, DEFAULT 1 | |
| create_time | datetime | | |

### 16. `activity_registrations` — 活动报名
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| activity_id | bigint | FK, NOT NULL | |
| user_id | bigint | FK, NOT NULL | |
| register_time | datetime | | |

### 17. `lost_founds` — 失物招领
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| user_id | bigint | FK, NOT NULL | |
| type | tinyint | INDEX | 1=寻物 2=招领 |
| title | varchar(200) | NOT NULL | |
| description | text | YES | |
| images | json | YES | |
| location_desc | varchar(200) | YES | |
| category | varchar(50) | YES | 物品分类 |
| contact_phone | varchar(20) | YES | |
| status | tinyint | INDEX, DEFAULT 1 | |
| create_time | datetime | INDEX | |

### 18. `classrooms` — 教室
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | bigint | PK, AUTO_INCREMENT | |
| building | varchar(50) | INDEX | 楼名 |
| room_no | varchar(20) | NOT NULL | 房间号 |
| capacity | int | DEFAULT 60 | |
| has_projector | tinyint | DEFAULT 1 | |
| has_ac | tinyint | DEFAULT 1 | |
| type | varchar(20) | YES, DEFAULT '普通教室' | 类型(普通教室/实验室/阶梯教室/机房) |
| status | varchar(20) | YES, DEFAULT '空闲' | 状态(空闲/上课/维修) |
| create_time | datetime | | |

---

## ❌ 废弃表（15 张，不要动，可以忽略）

| 表名 | 说明 |
|------|------|
| `teachers` | 教师表，0行数据，当前课表用 class_timetable.teacher 字段存教师名 |
| `courses` | 旧课表系统的课程定义，当前没用 |
| `course_schedules` | 旧排课表，当前没用 |
| `course_class` | 旧课程-班级关联，当前没用 |
| `student_timetable` | 旧学生个人课表，当前没用 |
| `events` | 旧活动表 |
| `event_registrations` | 旧活动报名 |
| `notifications` | 旧通知表 |
| `offline_messages` | 旧离线消息 |
| `user_favorites` | 旧收藏表（当前用 `favorites`） |
| `user_subscriptions` | 旧订阅表 |
| `room_occupancy` | 旧教室占用 |
| `section_times` | 旧节次时间定义 |
| `knowledge_chunks` | 旧知识库分块（AI知识库相关） |
| `knowledge_docs` | 旧知识库文档（AI知识库相关） |

> ⚠️ **给组员：新功能需要新增表的，只操作有效表。废弃表不要碰。**
