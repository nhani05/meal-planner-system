# Meal Planner System — Backend API

> Backend REST API cho hệ thống lập kế hoạch bữa ăn (Meal Planner), cung cấp đầy đủ các endpoint để Frontend tương tác: quản lý người dùng, món ăn, nguyên liệu, kế hoạch bữa ăn, dinh dưỡng và quản trị hệ thống.

---

## 📋 Mục lục

- [Tech Stack](#-tech-stack)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Chạy local](#-chạy-local)
- [Xác thực & Phân quyền](#-xác-thực--phân-quyền)
- [Base URL](#-base-url)
- [API Endpoints](#-api-endpoints)
  - [Auth](#1-auth)
  - [Người dùng & Hồ sơ sức khỏe](#2-người-dùng--hồ-sơ-sức-khỏe)
  - [Món ăn (Dish)](#3-món-ăn-dish)
  - [Nguyên liệu (Ingredient)](#4-nguyên-liệu-ingredient)
  - [Kế hoạch bữa ăn (Meal Plan)](#5-kế-hoạch-bữa-ăn-meal-plan)
  - [Bữa ăn (Meal) & Khẩu phần (Portion)](#6-bữa-ăn-meal--khẩu-phần-portion)
  - [Mẫu kế hoạch (Meal Plan Template)](#7-mẫu-kế-hoạch-meal-plan-template)
  - [Quản trị (Admin)](#8-quản-trị-admin)
- [DTO Schemas](#-dto-schemas)
- [Enum Values](#-enum-values)
- [Phân trang](#-phân-trang)
- [Xử lý lỗi](#-xử-lý-lỗi)
- [Postman Collection](#-postman-collection)
- [Cơ sở dữ liệu](#-cơ-sở-dữ-liệu)

---

## 🛠 Tech Stack

| Công nghệ | Phiên bản | Mục đích |
|---|---|---|
| **Java** | 17 | Ngôn ngữ chính |
| **Spring Boot** | 4.0.6 | Framework backend |
| **Spring Data JPA** | 4.0.6 | ORM & truy vấn CSDL |
| **Spring Security** | 4.0.6 | Xác thực & phân quyền |
| **MySQL** | 8.x | Cơ sở dữ liệu |
| **JWT (jjwt)** | 0.11.5 | Token xác thực |
| **Lombok** | latest | Giảm boilerplate code |
| **Maven** | - | Build tool |

---

## 🏗 Kiến trúc hệ thống

Project tuân theo kiến trúc **3-layer** chuẩn của Spring Boot:

```
controller/     → Nhận HTTP requests, trả về ResponseEntity
service/        → Business logic (interface + impl)
repository/     → Truy vấn CSDL (Spring Data JPA)
entity/         → JPA Entities (mapping bảng MySQL)
dto/            → Data Transfer Objects (request/response)
converter/      → Chuyển đổi Entity ↔ DTO
security/       → JWT filter & security config
exception/      → Custom exceptions + global handler
config/         → Cấu hình ứng dụng
```

---

## 🚀 Chạy local

### Yêu cầu
- Java 17+
- MySQL 8.x đang chạy
- Maven (đã có `mvnw` wrapper)

### Bước 1: Tạo database

```sql
CREATE DATABASE meal_planner_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Bước 2: Cấu hình kết nối

File: `src/main/resources/application-local.yaml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meal_planner_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8
    username: root
    password: 123456
```

> **Lưu ý:** Nếu dùng mật khẩu khác, sửa file trên hoặc dùng biến môi trường.

### Bước 3: Chạy ứng dụng

```bash
# Windows
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local

# hoặc build & chạy jar
.\mvnw.cmd clean package
java -jar target/meal-planner-system-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

### Bước 4: Kiểm tra

```bash
curl http://localhost:8081/api/health
```

Server chạy tại: `http://localhost:8081/api`

---

## 🔐 Xác thực & Phân quyền

Hệ thống sử dụng **JWT Bearer Token**.

### Cách sử dụng

Thêm header vào mọi request được bảo vệ:

```
Authorization: Bearer <token>
```

### Các loại endpoint

| Loại | Yêu cầu | Ví dụ |
|---|---|---|
| **Public** | Không cần token | `POST /auth/login`, `POST /auth/register` |
| **User** | Token hợp lệ | `GET /meal-plans`, `POST /dishes` |
| **Admin** | Token + role = `admin` | `GET /admin/statistics`, `POST /admin/dishes` |

### Token

- **Access Token:** Hết hạn sau **1 ngày**
- **Refresh Token:** Hết hạn sau **7 ngày**

---

## 🌐 Base URL

```
http://localhost:8081/api
```

**CORS:** Đã bật `@CrossOrigin(origins = "*")` cho tất cả controller, cho phép FE chạy trên bất kỳ domain nào (development).

---

## 📡 API Endpoints

### 1. Auth (`/auth`)

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Đăng ký tài khoản mới | ❌ |
| `POST` | `/auth/login` | Đăng nhập, nhận JWT token | ❌ |
| `POST` | `/auth/logout` | Đăng xuất (invalidate token) | ✅ |
| `POST` | `/auth/forgot-password` | Gửi OTP qua email | ❌ |
| `POST` | `/auth/verify-otp` | Xác minh OTP | ❌ |
| `POST` | `/auth/reset-password` | Đặt lại mật khẩu | ❌ |
| `PUT` | `/auth/change-password` | Đổi mật khẩu (đã đăng nhập) | ✅ |

**Request login:**
```json
{
  "username": "user01",
  "password": "123456"
}
```

**Response login:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresIn": 86400000
}
```

---

### 2. Người dùng & Hồ sơ sức khỏe

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `GET` | `/health-profile` | Lấy hồ sơ sức khỏe của user hiện tại | ✅ |
| `PUT` | `/health-profile` | Cập nhật hồ sơ sức khỏe | ✅ |
| `GET` | `/health-goals` | Lấy danh sách mục tiêu sức khỏe | ✅ |

---

### 3. Món ăn (Dish)

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `GET` | `/dishes/system` | Lấy món ăn hệ thống (SYSTEM source) | ✅ |
| `GET` | `/dishes/account/{accountId}` | Lấy món ăn của 1 user | ✅ |
| `POST` | `/dishes` | Tạo món ăn tùy chỉnh (CUSTOM source) | ✅ |
| `PUT` | `/dishes/{id}` | Cập nhật món ăn | ✅ |
| `DELETE` | `/dishes/{id}` | Xóa món ăn | ✅ |

**DishCategory**

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `GET` | `/dish-categories` | Lấy danh sách danh mục món ăn | ✅ |

**Rating**

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `POST` | `/dishes/{dishId}/ratings` | Đánh giá món ăn | ✅ |
| `GET` | `/dishes/{dishId}/ratings` | Xem đánh giá của món ăn | ✅ |

**Favorite**

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `POST` | `/favorites` | Thêm món vào yêu thích | ✅ |
| `DELETE` | `/favorites/{dishId}` | Xóa khỏi yêu thích | ✅ |
| `GET` | `/favorites` | Danh sách yêu thích của user | ✅ |

---

### 4. Nguyên liệu (Ingredient)

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `GET` | `/ingredients?page=0&size=20&search=keyword` | Lấy danh sách + phân trang + tìm kiếm | ✅ |
| `GET` | `/ingredients/{id}` | Lấy chi tiết nguyên liệu | ✅ |
| `POST` | `/ingredients` | Tạo nguyên liệu | ✅ |
| `PUT` | `/ingredients/{id}` | Cập nhật nguyên liệu | ✅ |
| `DELETE` | `/ingredients/{id}` | Xóa nguyên liệu | ✅ |

---

### 5. Kế hoạch bữa ăn (Meal Plan)

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `POST` | `/meal-plans` | Tạo kế hoạch bữa ăn mới | ✅ |
| `PUT` | `/meal-plans/{id}` | Cập nhật kế hoạch | ✅ |
| `DELETE` | `/meal-plans/{id}` | Xóa kế hoạch | ✅ |
| `GET` | `/meal-plans/{id}` | Lấy chi tiết kế hoạch theo ID | ✅ |
| `GET` | `/meal-plans?accountId={id}` | Lấy kế hoạch theo user | ✅ |

---

### 6. Bữa ăn (Meal) & Khẩu phần (Portion)

**Meal**

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `GET` | `/meal-plans/{planId}/meals` | Lấy danh sách bữa ăn trong kế hoạch | ✅ |

**Portion**

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `POST` | `/portions` | Thêm khẩu phần ăn vào bữa | ✅ |
| `GET` | `/meals/{mealId}/portions` | Lấy khẩu phần của 1 bữa | ✅ |
| `GET` | `/portions/{portionId}/nutrition` | Tính toán dinh dưỡng của khẩu phần | ✅ |

---

### 7. Mẫu kế hoạch (Meal Plan Template)

| Method | Endpoint | Mô tả | Auth |
|---|---|---|---|
| `GET` | `/meal-plan-templates?accountId={id}` | Lấy mẫu kế hoạch đã lưu của user | ✅ |

---

### 8. Quản trị (Admin)

> **Yêu cầu:** Bearer Token + role = `admin`

**Thống kê**

| Method | Endpoint | Mô tả |
|---|---|---|
| `GET` | `/admin/statistics` | Tổng quan: users, dishes, plans, feedbacks |

**Quản lý người dùng**

| Method | Endpoint | Mô tả |
|---|---|---|
| `GET` | `/admin/users?keyword=&status=&page=&size=` | Danh sách user (phân trang, lọc) |
| `GET` | `/admin/users/{id}` | Chi tiết user theo ID |
| `PATCH` | `/admin/users/{id}/lock` | Khóa tài khoản |
| `PATCH` | `/admin/users/{id}/unlock` | Mở khóa tài khoản |
| `DELETE` | `/admin/users/{id}` | Xóa tài khoản |

**Quản lý món ăn (Admin)**

| Method | Endpoint | Mô tả |
|---|---|---|
| `GET` | `/admin/dishes?keyword=&categoryId=&page=&size=` | Danh sách món ăn (phân trang, lọc) |
| `POST` | `/admin/dishes` | Tạo món ăn hệ thống (+ dinh dưỡng + nguyên liệu) |
| `PUT` | `/admin/dishes/{id}` | Cập nhật toàn bộ món ăn |
| `DELETE` | `/admin/dishes/{id}` | Xóa món ăn (kiểm tra ràng buộc portions) |

**Quản lý phản hồi**

| Method | Endpoint | Mô tả |
|---|---|---|
| `GET` | `/admin/feedbacks?status=&page=&size=` | Danh sách phản hồi (phân trang, lọc) |
| `PATCH` | `/admin/feedbacks/{id}/status` | Cập nhật trạng thái phản hồi |

---

## 📝 DTO Schemas

### UserAccountDTO
```json
{
  "id": 1,
  "username": "user01",
  "email": "user01@example.com",
  "role": "user",
  "status": "active"
}
```

### DishDTO
```json
{
  "id": 1,
  "name": "Grilled Chicken Salad",
  "categoryId": 1,
  "imageUrl": "https://example.com/salad.jpg",
  "source": "system",
  "difficulty": "easy",
  "totalTimeMin": 30
}
```

### NutritionInfoDTO
```json
{
  "id": 1,
  "dishId": 1,
  "caloriesPer100g": 120.50,
  "proteinPer100g": 15.00,
  "carbPer100g": 5.00,
  "fatPer100g": 4.00,
  "fiberPer100g": 2.00,
  "satFatPer100g": 1.00,
  "vitaminAMcg": 50.00,
  "vitaminCMg": 10.00,
  "vitaminDMcg": 5.00,
  "calciumMg": 20.00,
  "ironMg": 2.00
}
```

### IngredientDTO
```json
{
  "id": 1,
  "dishId": 1,
  "name": "Chicken Breast",
  "quantityG": 200.0,
  "unit": "g"
}
```

### AdminDishRequestDTO (POST/PUT `/admin/dishes`)
```json
{
  "dish": {
    "name": "Grilled Chicken Salad",
    "categoryId": 1,
    "imageUrl": "https://example.com/salad.jpg",
    "difficulty": "easy",
    "totalTimeMin": 30
  },
  "nutrition": {
    "caloriesPer100g": 120.50,
    "proteinPer100g": 15.00,
    "carbPer100g": 5.00,
    "fatPer100g": 4.00
  },
  "ingredients": [
    { "name": "Chicken Breast", "quantityG": 200.0, "unit": "g" },
    { "name": "Lettuce", "quantityG": 100.0, "unit": "g" }
  ]
}
```

### MealPlanDTO
```json
{
  "id": 1,
  "planName": "Weekly Plan",
  "planDate": "2026-05-05"
}
```

### MealDTO
```json
{
  "id": 1,
  "mealPlanId": 1,
  "mealType": "lunch"
}
```

### MealPlanTemplateDTO
```json
{
  "id": 1,
  "templateName": "High Protein Template",
  "savedAt": "2026-05-01T10:00:00"
}
```

### HealthProfileDTO
```json
{
  "id": 1,
  "fullName": "Nguyễn Văn A",
  "age": 25,
  "gender": "male",
  "heightCm": 175.0,
  "weightKg": 70.0,
  "avatarUrl": "https://example.com/avatar.jpg"
}
```

### HealthGoalDTO
```json
{
  "id": 1,
  "goalType": "muscle_gain",
  "activityLevel": "high",
  "targetWeightKg": 75.0,
  "dailyCaloriesKcal": 2500,
  "proteinGDay": 150.0,
  "carbGDay": 300.0,
  "fatGDay": 70.0
}
```

### FeedbackDTO
```json
{
  "id": 1,
  "accountId": 2,
  "username": "user01",
  "content": "App rất tốt!",
  "status": "pending",
  "submittedAt": "2026-05-05T08:30:00"
}
```

### AdminStatsDTO
```json
{
  "totalUsers": 100,
  "totalDishes": 50,
  "activePlansToday": 15,
  "newFeedbacks": 3
}
```

---

## 🔖 Enum Values

Tất cả enum trong hệ thống trả về dạng **lowercase string**.

| Enum | Giá trị hợp lệ |
|---|---|
| `UserRole` | `user`, `admin` |
| `UserStatus` | `active`, `locked`, `deleted` |
| `FeedbackStatus` | `pending`, `processing`, `resolved` |
| `DishDifficulty` | `easy`, `medium`, `hard` |
| `DishSource` | `system`, `custom` |
| `MealType` | `breakfast`, `lunch`, `dinner`, `snack` |
| `Gender` | `male`, `female`, `other` |
| `GoalType` | `weight_loss`, `muscle_gain`, `maintain` |
| `ActivityLevel` | `low`, `medium`, `high` |

---

## 📄 Phân trang

Các endpoint hỗ trợ phân trang trả về định dạng `Page<T>`:

```json
{
  "content": [...],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 100,
  "totalPages": 5,
  "last": false,
  "first": true,
  "numberOfElements": 20,
  "empty": false
}
```

**Tham số mặc định:**
- `page`: 0
- `size`: 20

---

## ⚠️ Xử lý lỗi

Hệ thống trả về lỗi dạng JSON với `message` rõ ràng:

```json
{
  "message": "Dish not found with id 999"
}
```

| HTTP Status | Ý nghĩa |
|---|---|
| `400 Bad Request` | Dữ liệu không hợp lệ, vi phạm business rule (ví dụ: xóa dish đang dùng trong meal plan) |
| `401 Unauthorized` | Thiếu token hoặc token hết hạn |
| `403 Forbidden` | Không đủ quyền (cần admin) |
| `404 Not Found` | Không tìm thấy resource |
| `409 Conflict` | Xung đột dữ liệu (ví dụ: username/email đã tồn tại) |
| `500 Internal Server Error` | Lỗi server |

---

## 📮 Postman Collection

File Postman đầy đủ nằm tại:

```
docs/postman_collection.json
```

Import file này vào Postman và cấu hình 2 biến:
- `baseUrl`: `http://localhost:8081/api`
- `token`: JWT access token (lấy từ response login)

---

## 🗄 Cơ sở dữ liệu

- **DBMS:** MySQL 8.x
- **Database:** `meal_planner_system`
- **Encoding:** `utf8mb4`

Script tạo bảng: `meal_planner_schema.sql`

### Các bảng chính

| Bảng | Mô tả |
|---|---|
| `tblUserAccount` | Tài khoản người dùng |
| `tblHealthProfile` | Hồ sơ sức khỏe |
| `tblHealthGoal` | Mục tiêu sức khỏe |
| `tblDishCategory` | Danh mục món ăn |
| `tblDish` | Món ăn |
| `tblNutritionInfo` | Thông tin dinh dưỡng |
| `tblIngredient` | Nguyên liệu |
| `tblMealPlan` | Kế hoạch bữa ăn |
| `tblMeal` | Bữa ăn trong kế hoạch |
| `tblPortion` | Khẩu phần ăn |
| `tblMealPlanTemplate` | Mẫu kế hoạch |
| `tblDishRating` | Đánh giá món ăn |
| `tblFavoriteDish` | Món ăn yêu thích |
| `tblUserFeedback` | Phản hồi người dùng |
| `tblPasswordResetToken` | Token đặt lại mật khẩu |

---

## 📁 Cấu trúc thư mục

```
meal-planner-system/
├── docs/
│   ├── api_endpoints.md           # Chi tiết từng endpoint
│   ├── implementation_plan.md     # Kế hoạch triển khai
│   ├── implementation_document.md # Tài liệu thiết kế chi tiết
│   └── postman_collection.json    # Collection Postman
├── src/main/java/com/example/javaweb/meal_planner_system/
│   ├── config/
│   ├── controller/                # 13 controllers
│   ├── converter/
│   ├── dto/                       # 21 DTOs
│   ├── entity/                    # 26 entities
│   ├── exception/
│   ├── repository/                # 16 repositories
│   ├── security/
│   └── service/                   # Interfaces + Impls
├── src/main/resources/
│   ├── application.yaml           # Profile: local
│   ├── application-local.yaml     # Cấu hình local
│   └── application-cloud.yaml   # Cấu hình cloud
├── meal_planner_schema.sql
├── pom.xml
└── README.md
```

---

## 👨‍💻 Tác giả

Project phát triển bởi nhóm **Meal Planner System** — PTIT.

---

> **Lưu ý cho FE Developer:**
> - Luôn gửi `Authorization: Bearer <token>` cho endpoint cần auth.
> - Enum nhận/gửi đều là **lowercase string**.
> - Phân trang dùng `page` (bắt đầu từ 0) và `size`.
> - Admin endpoints yêu cầu user có `role: "admin"`.
> - Nếu cần chi tiết request/response từng endpoint, xem file `docs/api_endpoints.md`.
