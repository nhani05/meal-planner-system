-- ============================================================
-- MEAL PLANNER - MOCK DATA (SEED)
-- Dùng để test API
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- MODULE 1: TÀI KHOẢN & HỒ SƠ SỨC KHOẺ
-- ============================================================

INSERT INTO tblUserAccount (id, username, email, password_hash, role, status) VALUES
(1,  'admin_system',  'admin@mealplanner.vn',    '$2b$12$adminHashXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'admin', 'active'),
(2,  'nguyen_an',     'an.nguyen@gmail.com',      '$2b$12$userHash1XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'user',  'active'),
(3,  'tran_binh',     'binh.tran@gmail.com',      '$2b$12$userHash2XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'user',  'active'),
(4,  'le_chau',       'chau.le@yahoo.com',         '$2b$12$userHash3XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'user',  'active'),
(5,  'pham_dung',     'dung.pham@outlook.com',     '$2b$12$userHash4XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'user',  'active'),
(6,  'hoang_em',      'em.hoang@gmail.com',        '$2b$12$userHash5XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'user',  'locked');

INSERT INTO tblHealthProfile (id, account_id, full_name, age, gender, height_cm, weight_kg, avatar_url) VALUES
(1, 1, 'Quản Trị Viên',    30, 'male',   175.00, 70.00,  NULL),
(2, 2, 'Nguyễn Văn An',    24, 'male',   172.50, 68.00,  'https://cdn.mealplanner.vn/avatars/user2.jpg'),
(3, 3, 'Trần Thị Bình',    28, 'female', 160.00, 55.00,  'https://cdn.mealplanner.vn/avatars/user3.jpg'),
(4, 4, 'Lê Ngọc Châu',     22, 'female', 158.00, 50.00,  NULL),
(5, 5, 'Phạm Tiến Dũng',   35, 'male',   170.00, 82.00,  'https://cdn.mealplanner.vn/avatars/user5.jpg'),
(6, 6, 'Hoàng Thị Em',     19, 'female', 163.00, 48.00,  NULL);

INSERT INTO tblHealthGoal (id, account_id, goal_type, activity_level, target_weight_kg, daily_calories_kcal, protein_g_day, carb_g_day, fat_g_day) VALUES
(1, 2, 'maintain',     'medium', 68.00, 2200, 110.00, 275.00, 73.00),
(2, 3, 'weight_loss',  'high',   50.00, 1600,  80.00, 180.00, 53.00),
(3, 4, 'weight_loss',  'low',    47.00, 1400,  70.00, 157.50, 46.67),
(4, 5, 'muscle_gain',  'high',   80.00, 2800, 175.00, 315.00, 77.78),
(5, 6, 'maintain',     'medium', 48.00, 1800,  90.00, 225.00, 60.00);

INSERT INTO tblPasswordResetToken (id, account_id, token, expires_at, used) VALUES
(1, 3, 'tok_a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6', '2025-06-01 10:00:00', 0),
(2, 4, 'tok_expired000000000000000000000000', '2024-01-01 00:00:00', 1);

-- ============================================================
-- MODULE 3: DANH MỤC & MÓN ĂN
-- ============================================================

INSERT INTO tblDishCategory (id, name) VALUES
(1,  'Cơm'),
(2,  'Phở & Bún'),
(3,  'Salad'),
(4,  'Sandwich & Bánh mì'),
(5,  'Canh & Súp'),
(6,  'Thịt nướng'),
(7,  'Hải sản'),
(8,  'Đồ uống & Sinh tố'),
(9,  'Tráng miệng'),
(10, 'Snack & Ăn vặt');

INSERT INTO tblDish (id, name, category_id, image_url, source, account_id, difficulty, total_time_min) VALUES
-- Món hệ thống
(1,  'Cơm gà xối mỡ',           1, 'https://cdn.mealplanner.vn/dishes/com_ga.jpg',         'system', NULL, 'medium', 45),
(2,  'Cơm tấm sườn bì chả',     1, 'https://cdn.mealplanner.vn/dishes/com_tam.jpg',         'system', NULL, 'medium', 60),
(3,  'Phở bò tái nạm',          2, 'https://cdn.mealplanner.vn/dishes/pho_bo.jpg',          'system', NULL, 'hard',   120),
(4,  'Bún bò Huế',              2, 'https://cdn.mealplanner.vn/dishes/bun_bo.jpg',           'system', NULL, 'hard',   150),
(5,  'Salad rau cải trộn',      3, 'https://cdn.mealplanner.vn/dishes/salad_rau.jpg',        'system', NULL, 'easy',   15),
(6,  'Bánh mì thịt nguội',      4, 'https://cdn.mealplanner.vn/dishes/banh_mi.jpg',          'system', NULL, 'easy',   10),
(7,  'Canh chua cá lóc',        5, 'https://cdn.mealplanner.vn/dishes/canh_chua.jpg',        'system', NULL, 'medium', 40),
(8,  'Cá hồi áp chảo',         7, 'https://cdn.mealplanner.vn/dishes/ca_hoi.jpg',            'system', NULL, 'medium', 25),
(9,  'Sườn heo nướng BBQ',      6, 'https://cdn.mealplanner.vn/dishes/suon_nuong.jpg',        'system', NULL, 'medium', 50),
(10, 'Sinh tố bơ mật ong',      8, 'https://cdn.mealplanner.vn/dishes/sinh_to_bo.jpg',       'system', NULL, 'easy',   5),
(11, 'Trứng chiên rau củ',      1, 'https://cdn.mealplanner.vn/dishes/trung_chien.jpg',      'system', NULL, 'easy',   15),
(12, 'Cháo gà',                 5, 'https://cdn.mealplanner.vn/dishes/chao_ga.jpg',           'system', NULL, 'easy',   60),
(13, 'Gà hấp gừng hành',        6, 'https://cdn.mealplanner.vn/dishes/ga_hap.jpg',           'system', NULL, 'medium', 45),
(14, 'Tôm sốt cà chua',         7, 'https://cdn.mealplanner.vn/dishes/tom_sot.jpg',          'system', NULL, 'easy',   20),
(15, 'Yaourt trái cây',         9, 'https://cdn.mealplanner.vn/dishes/yaourt.jpg',            'system', NULL, 'easy',   5),
-- Món do user tạo
(16, 'Salad ức gà tự làm',      3, NULL, 'custom', 2, 'easy',   20),
(17, 'Cơm chiên trứng tôm',     1, NULL, 'custom', 5, 'easy',   25);

INSERT INTO tblNutritionInfo (dish_id, calories_per_100g, protein_per_100g, carb_per_100g, fat_per_100g, fiber_per_100g, sat_fat_per_100g, vitamin_c_mg, calcium_mg, iron_mg) VALUES
(1,  185.0, 14.5, 22.0,  4.5,  0.5, 1.2,  2.0,  18.0, 1.5),
(2,  210.0, 16.0, 25.0,  6.0,  0.3, 2.0,  1.5,  22.0, 2.1),
(3,  75.0,  8.5,  6.5,   2.0,  0.2, 0.8,  3.0,  30.0, 1.8),
(4,  95.0,  9.0,  8.0,   3.0,  0.3, 1.0,  4.0,  28.0, 2.0),
(5,  35.0,  2.5,  5.5,   0.5,  2.0, 0.1,  25.0, 80.0, 1.0),
(6,  270.0, 12.0, 35.0,  8.0,  1.0, 2.5,  5.0,  40.0, 2.5),
(7,  60.0,  6.0,  5.5,   1.5,  0.8, 0.4,  15.0, 55.0, 1.2),
(8,  208.0, 20.0, 0.0,  13.0,  0.0, 3.0,  0.0,  15.0, 0.5),
(9,  295.0, 18.0, 5.0,  22.0,  0.0, 8.0,  1.0,  20.0, 1.8),
(10, 160.0, 2.0,  12.0, 13.0,  3.5, 2.8,  8.0,  20.0, 0.6),
(11, 155.0, 10.0, 3.5,  11.0,  0.5, 3.5,  2.0,  55.0, 1.5),
(12, 80.0,  5.5,  12.0,  1.0,  0.2, 0.3,  2.0,  18.0, 0.8),
(13, 165.0, 18.0, 1.0,  10.0,  0.0, 2.8,  1.0,  12.0, 1.0),
(14, 95.0,  12.0, 5.0,   3.5,  0.5, 0.8,  8.0,  70.0, 1.5),
(15, 120.0, 4.5,  18.0,  3.5,  0.3, 2.2,  5.0, 140.0, 0.2),
(16, 130.0, 22.0, 5.0,   3.0,  1.5, 0.6,  18.0, 30.0, 1.2),
(17, 190.0, 8.5,  28.0,  5.5,  0.5, 1.5,  3.0,  20.0, 1.0);

INSERT INTO tblIngredient (dish_id, name, quantity_g, unit) VALUES
-- Cơm gà xối mỡ (1)
(1, 'Gạo tẻ',           200, 'g'),
(1, 'Ức gà',            150, 'g'),
(1, 'Dầu ăn',            30, 'ml'),
(1, 'Hành tím',          20, 'g'),
(1, 'Nước mắm',          15, 'ml'),
-- Phở bò (3)
(3, 'Bánh phở tươi',    200, 'g'),
(3, 'Thịt bò tái',      100, 'g'),
(3, 'Xương bò',         300, 'g'),
(3, 'Hành tây',          50, 'g'),
(3, 'Gừng',              20, 'g'),
(3, 'Quế hồi',            5, 'g'),
-- Salad rau cải (5)
(5, 'Rau xà lách',      100, 'g'),
(5, 'Cà chua bi',        50, 'g'),
(5, 'Dưa leo',           50, 'g'),
(5, 'Dầu olive',         15, 'ml'),
(5, 'Giấm táo',          10, 'ml'),
-- Cá hồi áp chảo (8)
(8, 'Cá hồi phi lê',    200, 'g'),
(8, 'Dầu olive',         20, 'ml'),
(8, 'Tỏi',               10, 'g'),
(8, 'Chanh',             30, 'g'),
-- Trứng chiên rau củ (11)
(11, 'Trứng gà',         110, 'g'),
(11, 'Cà rốt',            30, 'g'),
(11, 'Hành lá',           20, 'g'),
(11, 'Dầu ăn',            10, 'ml'),
-- Salad ức gà tự làm (16)
(16, 'Ức gà luộc',       120, 'g'),
(16, 'Rau xà lách xoăn',  80, 'g'),
(16, 'Bơ chín',           60, 'g'),
(16, 'Nước cốt chanh',    15, 'ml');

INSERT INTO tblDishRating (account_id, dish_id, score, comment) VALUES
(2, 1, 5, 'Ngon tuyệt vời, đúng vị truyền thống!'),
(2, 3, 4, 'Phở ngon, nước dùng đậm đà.'),
(2, 8, 5, 'Cá hồi áp chảo cực kỳ ngon, da giòn thịt mềm.'),
(3, 1, 4, 'Cơm gà ngon nhưng hơi nhiều dầu.'),
(3, 5, 5, 'Salad thanh mát, phù hợp ăn kiêng.'),
(3, 8, 4, 'Thơm ngon, healthy.'),
(4, 5, 5, 'Rất phù hợp với chế độ eat clean!'),
(4, 15, 4, 'Yaourt trái cây ngon, mát.'),
(5, 9, 5, 'Sườn nướng ngon, thấm gia vị.'),
(5, 2, 4, 'Cơm tấm chuẩn vị Sài Gòn.');

INSERT INTO tblFavoriteDish (account_id, dish_id) VALUES
(2, 1), (2, 8), (2, 10),
(3, 5), (3, 8), (3, 15),
(4, 5), (4, 15),
(5, 9), (5, 2), (5, 13),
(6, 10), (6, 15);

-- ============================================================
-- MODULE 2: KẾ HOẠCH BỮA ĂN
-- ============================================================

INSERT INTO tblMealPlan (id, account_id, plan_name, plan_date) VALUES
(1, 2, 'Kế hoạch thứ Hai tuần này',  '2025-05-05'),
(2, 2, 'Kế hoạch thứ Ba tuần này',   '2025-05-06'),
(3, 3, 'Thực đơn giảm cân ngày 1',   '2025-05-05'),
(4, 3, 'Thực đơn giảm cân ngày 2',   '2025-05-06'),
(5, 5, 'Tăng cơ - ngày 1',           '2025-05-05'),
(6, 4, 'Kế hoạch eat clean',          '2025-05-05');

INSERT INTO tblMeal (id, meal_plan_id, meal_type) VALUES
-- Plan 1 (user 2)
(1,  1, 'breakfast'),
(2,  1, 'lunch'),
(3,  1, 'dinner'),
(4,  1, 'snack'),
-- Plan 2 (user 2)
(5,  2, 'breakfast'),
(6,  2, 'lunch'),
(7,  2, 'dinner'),
-- Plan 3 (user 3)
(8,  3, 'breakfast'),
(9,  3, 'lunch'),
(10, 3, 'dinner'),
(11, 3, 'snack'),
-- Plan 4 (user 3)
(12, 4, 'breakfast'),
(13, 4, 'lunch'),
(14, 4, 'dinner'),
-- Plan 5 (user 5)
(15, 5, 'breakfast'),
(16, 5, 'lunch'),
(17, 5, 'dinner'),
(18, 5, 'snack'),
-- Plan 6 (user 4)
(19, 6, 'breakfast'),
(20, 6, 'lunch'),
(21, 6, 'dinner');

INSERT INTO tblPortion (meal_id, dish_id, quantity_g, calories_kcal, protein_g, carb_g, fat_g) VALUES
-- Plan 1 - breakfast (meal 1): trứng chiên + sinh tố
(1,  11, 150.0,  232.5,  15.0, 5.25, 16.5),
(1,  10, 250.0,  400.0,   5.0, 30.0, 32.5),
-- Plan 1 - lunch (meal 2): cơm gà
(2,   1, 350.0,  647.5,  50.75, 77.0, 15.75),
-- Plan 1 - dinner (meal 3): cá hồi + salad
(3,   8, 200.0,  416.0,  40.0,  0.0,  26.0),
(3,   5, 150.0,   52.5,   3.75, 8.25,  0.75),
-- Plan 1 - snack (meal 4): yaourt
(4,  15, 100.0,  120.0,   4.5, 18.0,  3.5),
-- Plan 2 - breakfast (meal 5): cháo gà
(5,  12, 300.0,  240.0,  16.5, 36.0,  3.0),
-- Plan 2 - lunch (meal 6): phở bò
(6,   3, 500.0,  375.0,  42.5, 32.5, 10.0),
-- Plan 2 - dinner (meal 7): gà hấp + salad
(7,  13, 200.0,  330.0,  36.0,  2.0, 20.0),
(7,   5, 100.0,   35.0,   2.5,  5.5,  0.5),
-- Plan 3 - breakfast (meal 8): salad + trứng
(8,   5, 200.0,   70.0,   5.0, 11.0,  1.0),
(8,  11, 110.0,  170.5,  11.0,  3.85, 12.1),
-- Plan 3 - lunch (meal 9): canh chua + cơm ít
(9,   7, 300.0,  180.0,  18.0, 16.5,  4.5),
(9,   1, 150.0,  277.5,  21.75, 33.0, 6.75),
-- Plan 3 - dinner (meal 10): cá hồi + salad
(10,  8, 150.0,  312.0,  30.0,  0.0, 19.5),
(10, 16, 200.0,  260.0,  44.0, 10.0,  6.0),
-- Plan 3 - snack (meal 11): yaourt
(11, 15, 80.0,    96.0,   3.6, 14.4,  2.8),
-- Plan 4 - breakfast (meal 12): salad ức gà
(12, 16, 200.0,  260.0,  44.0, 10.0,  6.0),
-- Plan 4 - lunch (meal 13): bún bò ít
(13,  4, 300.0,  285.0,  27.0, 24.0,  9.0),
-- Plan 4 - dinner (meal 14): canh chua + salad
(14,  7, 250.0,  150.0,  15.0, 13.75, 3.75),
(14,  5, 150.0,   52.5,   3.75, 8.25,  0.75),
-- Plan 5 (tăng cơ) - breakfast (meal 15): cơm tấm
(15,  2, 400.0,  840.0,  64.0, 100.0, 24.0),
-- Plan 5 - lunch (meal 16): sườn nướng + cơm
(16,  9, 250.0,  737.5,  45.0, 12.5, 55.0),
(16,  1, 200.0,  370.0,  29.0, 44.0,  9.0),
-- Plan 5 - dinner (meal 17): cá hồi + tôm sốt
(17,  8, 200.0,  416.0,  40.0,  0.0, 26.0),
(17, 14, 150.0,  142.5,  18.0,  7.5,  5.25),
-- Plan 5 - snack (meal 18): sinh tố bơ
(18, 10, 300.0,  480.0,   6.0, 36.0, 39.0),
-- Plan 6 (eat clean) - breakfast (meal 19): trứng + salad
(19, 11, 120.0,  186.0,  12.0,  4.2, 13.2),
(19,  5, 150.0,   52.5,   3.75, 8.25,  0.75),
-- Plan 6 - lunch (meal 20): salad ức gà + canh chua
(20, 16, 200.0,  260.0,  44.0, 10.0,  6.0),
(20,  7, 200.0,  120.0,  12.0, 11.0,  3.0),
-- Plan 6 - dinner (meal 21): gà hấp
(21, 13, 180.0,  297.0,  32.4,  1.8, 18.0);

-- ============================================================
-- TEMPLATE KẾ HOẠCH MẪU
-- ============================================================

INSERT INTO tblMealPlanTemplate (id, account_id, template_name) VALUES
(1, 2, 'Tuần duy trì cân nặng - chuẩn'),
(2, 3, 'Thực đơn giảm cân 7 ngày'),
(3, 5, 'Tăng cơ nạc - mẫu cơ bản');

INSERT INTO tblTemplateMeal (id, template_id, meal_type) VALUES
(1, 1, 'breakfast'),
(2, 1, 'lunch'),
(3, 1, 'dinner'),
(4, 1, 'snack'),
(5, 2, 'breakfast'),
(6, 2, 'lunch'),
(7, 2, 'dinner'),
(8, 3, 'breakfast'),
(9, 3, 'lunch'),
(10,3, 'dinner'),
(11,3, 'snack');

INSERT INTO tblTemplatePortion (template_meal_id, dish_id, quantity_g) VALUES
(1,  11, 150.0),
(1,  10, 200.0),
(2,   1, 350.0),
(3,   8, 200.0),
(3,   5, 150.0),
(4,  15, 100.0),
(5,   5, 200.0),
(5,  11, 110.0),
(6,   3, 400.0),
(7,  16, 200.0),
(8,   2, 350.0),
(9,   9, 250.0),
(9,   1, 200.0),
(10,  8, 200.0),
(10, 14, 150.0),
(11, 10, 300.0);

-- ============================================================
-- GỢI Ý ĐIỀU CHỈNH
-- ============================================================

INSERT INTO tblAdjustmentSuggestion (account_id, meal_plan_id, suggestion_type, content, status) VALUES
(2, 1, 'add_dish',       'Bữa sáng của bạn đang thiếu ~150 kcal so với mục tiêu. Bạn có thể thêm 1 lát bánh mì nguyên cám hoặc 1 hũ yaourt.', 'pending'),
(2, 1, 'reduce_portion', 'Khẩu phần sinh tố bơ (250g) đang cung cấp khá nhiều chất béo. Hãy thử giảm xuống 150g để cân đối bữa sáng.', 'dismissed'),
(3, 3, 'swap_dish',      'Thay bún bò Huế bằng phở gà hoặc canh chua để giảm ~80 kcal và giảm lượng chất béo bão hòa trong ngày.', 'applied'),
(3, 4, 'add_dish',       'Bữa tối ngày 2 đang thấp hơn mục tiêu ~120 kcal. Thêm một ít cơm trắng hoặc khoai lang hấp để đủ năng lượng.', 'pending'),
(5, 5, 'add_dish',       'Để đạt mục tiêu tăng cơ (protein 175g/ngày), bạn cần thêm nguồn protein vào bữa snack. Gợi ý: trứng luộc hoặc protein shake.', 'pending');

-- ============================================================
-- MODULE 4: PHẢN HỒI & AUDIT LOG
-- ============================================================

INSERT INTO tblUserFeedback (id, account_id, content, status, admin_note) VALUES
(1, 2, 'Ứng dụng rất hữu ích, tuy nhiên tôi muốn có thêm chức năng nhập liệu bằng giọng nói.', 'pending', NULL),
(2, 3, 'Gợi ý điều chỉnh thực đơn rất thông minh và chính xác. Mong có thêm nhiều gợi ý hơn.', 'resolved', 'Cảm ơn phản hồi! Chúng tôi đang phát triển tính năng AI gợi ý nâng cao.'),
(3, 4, 'Tôi không thể đăng nhập được, báo lỗi "account locked" dù tôi chưa nhập sai mật khẩu.', 'processing', 'Đang kiểm tra hệ thống, sẽ phản hồi trong 24h.'),
(4, 5, 'Database món ăn cần bổ sung thêm các món Việt vùng miền như bánh xèo, bún chả, mì Quảng.', 'pending', NULL),
(5, 6, 'Tài khoản của tôi bị khoá mà không có thông báo rõ ràng lý do.', 'processing', 'Tài khoản bị khoá tạm thời do đăng nhập bất thường. Đang xem xét mở lại.');

INSERT INTO tblAdminAuditLog (admin_id, action, target_type, target_id, note) VALUES
(1, 'lock_account',      'tblUserAccount',  6, 'Khoá tài khoản do phát hiện đăng nhập bất thường từ nhiều IP khác nhau.'),
(1, 'resolve_feedback',  'tblUserFeedback', 2, 'Đã phản hồi người dùng và ghi nhận yêu cầu tính năng.'),
(1, 'delete_dish',       'tblDish',        17, 'Xoá món ăn custom vi phạm quy định (trùng lặp nội dung hệ thống).'),
(1, 'update_dish',       'tblDish',         3, 'Cập nhật ảnh và thông tin dinh dưỡng cho Phở bò tái nạm.'),
(1, 'process_feedback',  'tblUserFeedback', 3, 'Bắt đầu xử lý phản hồi lỗi đăng nhập của user le_chau.');

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- GỢI Ý QUERY TEST
-- ============================================================
-- 1. Lấy kế hoạch ăn + chi tiết dinh dưỡng theo ngày của user:
--    SELECT mp.*, m.meal_type, d.name, p.quantity_g, p.calories_kcal
--    FROM tblMealPlan mp
--    JOIN tblMeal m ON m.meal_plan_id = mp.id
--    JOIN tblPortion p ON p.meal_id = m.id
--    JOIN tblDish d ON d.id = p.dish_id
--    WHERE mp.account_id = 2 AND mp.plan_date = '2025-05-05';

-- 2. Tổng dinh dưỡng theo ngày của user:
--    SELECT mp.plan_date,
--           SUM(p.calories_kcal) total_cal,
--           SUM(p.protein_g)  total_protein,
--           SUM(p.carb_g)     total_carb,
--           SUM(p.fat_g)      total_fat
--    FROM tblMealPlan mp
--    JOIN tblMeal m ON m.meal_plan_id = mp.id
--    JOIN tblPortion p ON p.meal_id = m.id
--    WHERE mp.account_id = 3
--    GROUP BY mp.plan_date;

-- 3. Top món ăn được yêu thích nhất:
--    SELECT d.name, COUNT(f.id) fav_count
--    FROM tblFavoriteDish f JOIN tblDish d ON d.id = f.dish_id
--    GROUP BY d.id ORDER BY fav_count DESC LIMIT 5;
