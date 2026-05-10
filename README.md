# 🥗 NutriPlan – Meal Planning & Nutrition Management System

> Ứng dụng web full-stack giúp người dùng lập kế hoạch bữa ăn theo tuần, theo dõi lượng calo/macros và quản lý thực đơn cá nhân theo mục tiêu sức khỏe.

---

## 📋 Mục lục

- [Giới thiệu](#-giới-thiệu)
- [Tính năng](#-tính-năng)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Cài đặt & Chạy dự án](#-cài-đặt--chạy-dự-án)
- [Cấu hình môi trường](#-cấu-hình-môi-trường)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Thành viên nhóm](#-thành-viên-nhóm)

---

## 🌟 Giới thiệu

**NutriPlan** là một hệ thống quản lý dinh dưỡng và lập kế hoạch bữa ăn được xây dựng cho đồ án môn **Lập trình Web Java** tại **Học viện Công nghệ Bưu chính Viễn thông – Nhóm 04**.

Hệ thống cho phép người dùng:
- Lên kế hoạch bữa ăn theo từng ngày trong tuần
- Theo dõi lượng calo và macros (Protein, Carb, Fat) tự động
- Quản lý thư viện món ăn cá nhân và hệ thống
- Thiết lập mục tiêu sức khỏe (giảm cân, tăng cơ, duy trì)

---

## ✨ Tính năng

### 👤 Quản lý tài khoản
- Đăng ký / Đăng nhập với JWT Authentication
- Quên mật khẩu qua OTP 6 số (5 phút hết hạn)
- Đổi mật khẩu sau khi đăng nhập
- Khóa tài khoản tự động sau **5 lần đăng nhập sai**
- Phân quyền: `User` / `Admin`

### 🥘 Quản lý Món ăn
- Thư viện món ăn hệ thống (source: `system`)
- Tạo món ăn tùy chỉnh (source: `custom`)
- Thông tin dinh dưỡng đầy đủ (Calo, Protein, Carb, Fat, Fiber, Vitamin...)
- Tìm kiếm & lọc theo danh mục, calo
- Đánh giá sao + bình luận
- Danh sách yêu thích

### 📅 Kế hoạch Bữa ăn
- Lịch tuần trực quan
- Tạo/sửa/xóa kế hoạch theo ngày
- 4 bữa: Sáng / Trưa / Tối / Bữa phụ
- Tự động tính calo & macros theo khẩu phần (gram)
- Lưu mẫu thực đơn để tái sử dụng

### 💪 Hồ sơ Sức khỏe
- Nhập chỉ số cơ thể (chiều cao, cân nặng, tuổi, giới tính)
- Thiết lập mục tiêu sức khỏe & mức độ vận động
- Chỉ tiêu macro hàng ngày (Protein/Carb/Fat)

### 🛡️ Quản trị (Admin)
- Dashboard thống kê tổng quan
- Quản lý người dùng: khóa / mở khóa / xóa mềm
- Quản lý món ăn hệ thống (CRUD)
- Xử lý phản hồi người dùng
- Audit Log theo dõi thao tác admin

---

## 🛠️ Công nghệ sử dụng

### Back-end
| Công nghệ | Phiên bản |
|-----------|-----------|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Security | Latest |
| Spring Data JPA | Latest |
| JWT (JJWT) | 0.11.5 |
| MySQL Connector | Latest |
| Lombok | Latest |
| Maven | 3.9.14 |

### Front-end
| Công nghệ | Phiên bản |
|-----------|-----------|
| React | 19.2.5 |
| Vite | 8.0 |
| TailwindCSS | 3.4 |
| shadcn/ui | Latest |
| Zustand | 5.0 |
| Axios | 1.16 |
| React Router | 7.14 |
| React Hook Form | 7.75 |
| Zod | 4.4 |
| Recharts | 3.8 |

### Database
- **MySQL** – Relational database
- **20+ bảng** với đầy đủ quan hệ FK

---

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────────────────────────────────────────┐
│                    Frontend (React)                  │
│          Vite + TailwindCSS + shadcn/ui             │
│              Zustand (State Management)              │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP/REST (Axios)
                      │ Bearer JWT Token
┌─────────────────────▼───────────────────────────────┐
│              Backend (Spring Boot)                   │
│                                                      │
│  ┌──────────────┐  ┌─────────────┐  ┌────────────┐ │
│  │  Controller  │→ │   Service   │→ │ Repository │ │
│  │   (REST API) │  │ (Business)  │  │   (JPA)    │ │
│  └──────────────┘  └─────────────┘  └─────┬──────┘ │
│                                            │        │
│  Spring Security + JWT Filter              │        │
└────────────────────────────────────────────┼────────┘
                                             │
                             ┌───────────────▼──────┐
                             │      MySQL Database   │
                             └──────────────────────┘
```

**Kiến trúc 3 lớp:**
- **Controller Layer** – Tiếp nhận HTTP request, validate input, trả response
- **Service Layer** – Xử lý business logic
- **Repository Layer** – Tương tác với database qua Spring Data JPA

---

## 🚀 Cài đặt & Chạy dự án

### Yêu cầu hệ thống
- Java 17+
- Node.js 20+
- MySQL 8.0+
- Maven 3.9+

### 1. Clone repository

```bash
git clone https://github.com/<your-username>/meal-planner-system.git
cd meal-planner-system
```

### 2. Thiết lập Database

```sql
CREATE DATABASE meal_planner_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Sau đó chạy file schema:
```bash
mysql -u root -p meal_planner_system < meal_planner_schema.sql
```

Chạy mock data (tùy chọn):
```bash
mysql -u root -p meal_planner_system < src/main/resources/mockdata_seed.sql
```

### 3. Chạy Back-end

Tạo file `.env` từ file mẫu:
```bash
cp src/main/resources/env.example .env
```

Chỉnh sửa cấu hình trong `src/main/resources/application-local.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meal_planner_system
    username: root
    password: your_password
```

Chạy Spring Boot:
```bash
./mvnw spring-boot:run
```

Backend khởi động tại: `http://localhost:8081/api`

### 4. Chạy Front-end

```bash
# Di chuyển vào thư mục frontend (nếu tách riêng)
cd frontend

# Tạo file .env
cp .env.example .env
# Chỉnh VITE_API_BASE_URL=http://localhost:8081/api

# Cài dependencies
npm install

# Chạy development server
npm run dev
```

Frontend khởi động tại: `http://localhost:5173`

---

## ⚙️ Cấu hình môi trường

### Back-end (`application-local.yaml`)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meal_planner_system
    username: root
    password: 123456

server:
  port: 8081
  servlet:
    context-path: /api

app:
  jwt:
    secret: your-secret-key-min-32-chars
    expiration-ms: 86400000  # 1 ngày
```

### Front-end (`.env`)
```env
VITE_API_BASE_URL=http://localhost:8081/api
```

---

## 📖 API Documentation

Base URL: `http://localhost:8081/api`

### Authentication
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | `/auth/register` | Đăng ký tài khoản |
| POST | `/auth/login` | Đăng nhập |
| POST | `/auth/logout` | Đăng xuất |
| POST | `/auth/forgot-password` | Gửi OTP qua email |
| POST | `/auth/verify-otp` | Xác thực OTP |
| POST | `/auth/reset-password` | Đặt lại mật khẩu |
| PUT | `/auth/change-password` | Đổi mật khẩu |

### Meal Plans
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/meal-plans/account/{id}` | Lấy danh sách kế hoạch |
| GET | `/meal-plans/{id}` | Chi tiết kế hoạch |
| POST | `/meal-plans` | Tạo kế hoạch mới |
| PUT | `/meal-plans/{id}` | Cập nhật kế hoạch |
| DELETE | `/meal-plans/{id}` | Xóa kế hoạch |
| POST | `/meal-plans/{id}/meals/{type}/portions` | Thêm khẩu phần |

### Dishes
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/dishes` | Danh sách món ăn (có filter) |
| GET | `/dishes/{id}` | Chi tiết món ăn |
| POST | `/dishes` | Tạo món tùy chỉnh |
| PUT | `/dishes/{id}` | Cập nhật món ăn |
| DELETE | `/dishes/{id}` | Xóa món ăn |

### Admin
| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/admin/statistics` | Thống kê hệ thống |
| GET | `/admin/users` | Danh sách người dùng |
| PATCH | `/admin/users/{id}/lock` | Khóa tài khoản |
| PATCH | `/admin/users/{id}/unlock` | Mở khóa tài khoản |
| GET | `/admin/feedbacks` | Danh sách phản hồi |

> 📁 Import file Postman Collection tại: `docs/postman_collection.json`

---

## 🗄️ Database Schema

Hệ thống gồm **4 module chính** với tổng cộng **20+ bảng**:

```
Module 1: Quản lý Tài khoản
├── tblUserAccount
├── tblHealthProfile
├── tblHealthGoal
└── tblPasswordResetToken

Module 2: Kế hoạch Bữa ăn
├── tblMealPlan
├── tblMeal
├── tblPortion
├── tblMealPlanTemplate
├── tblTemplateMeal
├── tblTemplatePortion
└── tblAdjustmentSuggestion

Module 3: Món ăn
├── tblDishCategory
├── tblDish
├── tblNutritionInfo
├── tblIngredient
├── tblDishRating
└── tblFavoriteDish

Module 4: Quản trị
├── tblUserFeedback
└── tblAdminAuditLog
```
