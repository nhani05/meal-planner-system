# Hướng dẫn viết test cho dự án Meal Planner System

Tài liệu này mô tả quy trình, tiêu chuẩn và ví dụ để viết test cho dự án thực tế. Mục tiêu: đảm bảo chất lượng, khả năng bảo trì và tích hợp liên tục.

1. Nguyên tắc chung
- Tuân theo Test Pyramid: nhiều unit test, ít integration test, ít nhất e2e.
- Mỗi test phải rõ ràng, độc lập, có thể chạy lặp lại.
- Viết test trước khi sửa lỗi quan trọng hoặc khi thêm tính năng (TDD khi hợp lý).

2. Loại test và phạm vi
- Unit tests: kiểm tra logic nhỏ trong lớp/service/repository. Sử dụng JUnit + Mockito.
- Integration tests: kiểm tra tích hợp giữa tầng (ví dụ controller → service → repository) với Spring Boot Test và testcontainers hoặc H2.
- Contract/API tests: kiểm thử contract của API bằng MockMvc hoặc RestAssured; đối với thay đổi API, cập nhật test cùng lúc.
- End-to-end (E2E): kiểm tra luồng nghiệp vụ chính bằng Postman/Newman hoặc Playwright (frontend + backend).
- Performance/Load: tách riêng; dùng JMeter/Gatling khi cần.

3. Công cụ tiêu biểu cho dự án
- JUnit 5: framework test chính.
- Mockito: mock dependencies.
- Spring Boot Test: context, @WebMvcTest, @SpringBootTest.
- Testcontainers: khi cần DB thật (Postgres) trong CI.
- Jacoco: đo coverage.
- Maven Surefire / Failsafe: chạy unit/integration tests.

4. Cấu trúc và đặt tên
- Đặt test trong `src/test/java` tương ứng với package của source.
- Tên lớp test: `ClassNameTest` hoặc `ClassNameTests`.
- Tên method test: mô tả hành vi, ví dụ `shouldReturn400WhenInvalidInput()`.

5. Viết Unit Test tốt
- Arrange / Act / Assert: tách rõ 3 bước.
- Mocks: chỉ mock những dependency bên ngoài, không mock class đang test.
- Tránh logic phức tạp trong test; giữ test nhỏ, tập trung.
- Dùng dữ liệu mẫu (fixtures) dễ đọc; nếu cần nhiều trường hợp, dùng parameterized tests.

6. Viết Integration Test
- Sử dụng `@SpringBootTest` với profile `test` và cấu hình DB in-memory hoặc Testcontainers.
- Dọn dẹp dữ liệu giữa các test: dùng `@Transactional` rollback hoặc khởi tạo state rõ ràng.
- Kiểm tra mapping, cấu hình JPA, repository và các bean.

7. Kiểm thử API
- Dùng `MockMvc` hoặc `WebTestClient` để test controller layer với các request/response chính.
- Tạo test cho: validation errors, authentication/authorization, success path, edge cases.

8. Test dữ liệu và fixture
- Tạo factory hoặc builder để sinh entities test.
- Tránh nạp toàn bộ SQL schema trong unit test; chỉ dùng schema đầy đủ trong integration test nếu cần.

9. Coverage và mức độ chấp nhận
- Mục tiêu coverage: >= 70% (tùy module). Coverage không thay thế test chất lượng.
- Ưu tiên cover logic business-critical và các branch quan trọng.

10. CI / Pipeline
- Trong CI (GitHub Actions / Azure DevOps / Jenkins):
  - Chạy `mvn -DskipTests=false test` cho unit tests (Surefire).
  - Chạy integration tests (Failsafe) nếu cần, hoặc bật Testcontainers.
  - Thu thập báo cáo Jacoco và fail build nếu coverage giảm dưới ngưỡng.

11. Quy trình review test trong PR
- Mỗi PR phải có test cover cho thay đổi chức năng.
- Reviewer kiểm tra: tính độc lập, trường hợp biên, readability của test.

12. Ví dụ mẫu (Unit test)
```java
@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @Mock
    DishRepository repo;
    @InjectMocks
    DishService service;

    @Test
    void shouldReturnDishWhenExists() {
        // Arrange
        Dish d = new Dish(1L, "Pho", ...);
        when(repo.findById(1L)).thenReturn(Optional.of(d));
        // Act
        var result = service.findById(1L);
        // Assert
        assertTrue(result.isPresent());
        assertEquals("Pho", result.get().getName());
    }
}
```

13. Ví dụ mẫu (Integration test)
```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DishControllerIT {
    @Autowired MockMvc mvc;

    @Test
    void createDish_returns201() throws Exception {
        var payload = "{ \"name\": \"Salad\" }";
        mvc.perform(post("/api/dishes").contentType(MediaType.APPLICATION_JSON).content(payload))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.name").value("Salad"));
    }
}
```

14. Checklist trước khi merge
- Có unit/integration test liên quan cho thay đổi.
- Tests chạy xanh trên local và CI.
- Không có test flaky; nếu có, ghi chú rõ lý do.

15. Xử lý test flaky
- Isolate nguyên nhân: thời gian, race condition, dữ liệu chung.
- Sử dụng retry khi không thể tránh, nhưng ghi nhận và mở issue fix.

16. Tài nguyên và tham khảo
- JUnit 5, Mockito, Spring Boot Testing, Testcontainers, Jacoco.

---
Ghi chú: tài liệu này là khung sườn — hãy điều chỉnh mức coverage và công cụ phù hợp với yêu cầu dự án và môi trường CI của bạn.
