# Meal Planner System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1.svg)](https://www.mysql.com/)

Backend REST API cho hệ thống lập kế hoạch bữa ăn: người dùng, món ăn, nguyên liệu, kế hoạch bữa ăn, dinh dưỡng, phản hồi và quản trị.

---

## Mục lục

- [Tính năng](#tính-năng)
- [Công nghệ](#công-nghệ)
- [Kiến trúc](#kiến-trúc)
- [Yêu cầu](#yêu-cầu)
- [Cài đặt và chạy local](#cài-đặt-và-chạy-local)
- [Cấu hình](#cấu-hình)
- [Build và kiểm thử](#build-và-kiểm-thử)
- [Tài liệu](#tài-liệu)
- [Đóng góp](#đóng-góp)
- [Bảo mật](#bảo-mật)
- [Giấy phép](#giấy-phép)
- [Tác giả](#tác-giả)

---

## Tính năng

- Xác thực JWT (access / refresh), đăng ký, đăng nhập, quên mật khẩu (OTP), đổi mật khẩu
- Hồ sơ sức khỏe và mục tiêu dinh dưỡng
- Món ăn (hệ thống / tùy chỉnh), danh mục, đánh giá, yêu thích
- Nguyên liệu, kế hoạch bữa ăn, bữa, khẩu phần và tính toán dinh dưỡng
- Mẫu kế hoạch đã lưu
- Khu vực quản trị: thống kê, người dùng, món ăn hệ thống, phản hồi

---

## Công nghệ

| Thành phần | Phiên bản / ghi chú |
|------------|---------------------|
| Java | 17 |
| Spring Boot | 4.0.6 (Web, Data JPA, Security, Validation) |
| MySQL | 8.x |
| JWT | jjwt 0.11.x |
| Build | Maven (`mvnw` / `mvnw.cmd`) |
| Khác | Lombok, spring-dotenv |

---

## Kiến trúc

Ứng dụng theo mô hình **3 lớp** (Spring Boot):

```text
controller/   → HTTP, ResponseEntity
service/      → Nghiệp vụ (interface + impl)
repository/   → Spring Data JPA
entity/       → JPA entities
dto/          → Request/response
converter/    → Entity ↔ DTO
security/     → JWT, cấu hình bảo mật
exception/    → Exception tùy chỉnh, xử lý tập trung
config/       → Bean và cấu hình ứng dụng
```

---

## Yêu cầu

- JDK 17+
- MySQL 8.x
- Maven (hoặc chỉ dùng Maven Wrapper có sẵn trong repo)

---

## Cài đặt và chạy local

### 1. Tạo cơ sở dữ liệu

```sql
CREATE DATABASE meal_planner_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Schema tham khảo: [`db-data/meal_planner_schema.sql`](db-data/meal_planner_schema.sql).

### 2. Cấu hình kết nối

Chỉnh [`src/main/resources/application-local.yaml`](src/main/resources/application-local.yaml) (URL, `username`, `password`) cho môi trường của bạn. Không commit thông tin nhạy cảm thật; có thể dùng biến môi trường nếu dự án đã hỗ trợ.

### 3. Chạy ứng dụng

```bash
# Windows
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local

# Linux / macOS
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

JAR (tùy chọn):

```bash
.\mvnw.cmd clean package
java -jar target/meal-planner-system-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

### 4. Kiểm tra

- Base URL: `http://localhost:8081/api` (cổng và `context-path` theo `application-local.yaml`).
- Xác nhận server đã lên: gọi một endpoint công khai (ví dụ đăng nhập thiếu dữ liệu sẽ trả về lỗi validation thay vì mất kết nối):

```bash
curl -X POST http://localhost:8081/api/auth/login -H "Content-Type: application/json" -d "{}"
```

### Postman

Import [`docs/POSTMAN_COLLECTION.json`](docs/POSTMAN_COLLECTION.json) và biến môi trường (ví dụ `baseUrl` = `http://localhost:8081/api`, `token` = JWT sau khi đăng nhập). Có thêm [`docs/postman_environment.json`](docs/postman_environment.json) nếu cần.

---

## Cấu hình

| Profile | Mục đích |
|---------|----------|
| `local` | Phát triển trên máy (`application-local.yaml`) |
| `cloud` | Triển khai / môi trường từ xa (`application-cloud.yaml`) |

Profile mặc định được kích hoạt trong [`src/main/resources/application.yaml`](src/main/resources/application.yaml).

**Xác thực:** header `Authorization: Bearer <access_token>` cho các endpoint được bảo vệ. Vai trò `admin` cần cho nhóm endpoint quản trị.

---

## Build và kiểm thử

```bash
.\mvnw.cmd clean verify
```

Chỉ chạy test:

```bash
.\mvnw.cmd test
```

---

## Tài liệu

Tài liệu chi tiết (API, OpenAPI, entity, kế hoạch triển khai) nằm trong thư mục [`docs/`](docs/README.md).

| Nhu cầu | File |
|---------|------|
| Danh sách endpoint, DTO, enum | [`docs/API.md`](docs/API.md) |
| OpenAPI 3 | [`docs/openapi.yaml`](docs/openapi.yaml) |
| Postman | [`docs/POSTMAN_COLLECTION.json`](docs/POSTMAN_COLLECTION.json) |
| Báo cáo môn Nhập môn CNPM (N4) | [`docs/planning/N4_SE.md`](docs/planning/N4_SE.md) |
| Index tài liệu | [`docs/README.md`](docs/README.md) |

---

## Đóng góp

1. Fork repository (nếu làm việc trên bản sao từ remote).
2. Tạo nhánh tính năng: `git checkout -b feature/mo-ta-ngan`.
3. Commit rõ ràng, message mô tả thay đổi.
4. Mở Pull Request hướng về nhánh chính (`main`), mô tả mục đích và cách kiểm tra.

Tuân thủ phong cách code hiện có; ưu tiên thay đổi nhỏ, tập trung vào một vấn đề mỗi PR. Cập nhật tài liệu trong `docs/` khi thay đổi hợp đồng API.

---

## Bảo mật

Nếu phát hiện lỗ hổng, **không** mở issue công khai chứa chi tiết khai thác. Liên hệ maintainer qua kênh riêng (email / bảo mật của tổ chức) theo chính sách nội bộ của bạn.

---

## Giấy phép

Repository hiện **chưa** có file `LICENSE` ở thư mục gốc. Để đúng chuẩn mã nguồn mở, nên thêm giấy phép rõ ràng (ví dụ MIT, Apache-2.0) và cập nhật mục này trỏ tới file đó.

---

## Tác giả

Phát triển bởi nhóm **Meal Planner System** — PTIT.
