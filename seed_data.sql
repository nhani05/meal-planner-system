-- ============================================================
-- MEAL PLANNER - SEED DATA (ĐẦY ĐỦ)
-- Sử dụng UserAccount đã có (id 1-6)
-- id 1: admin_system (admin)
-- id 2: nguyen_an (user)
-- id 3: tran_binh (user)
-- id 4: le_chau (user)
-- id 5: pham_dung (user)
-- id 6: hoang_em (user - locked)
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- TRUNCATE CÁC BẢNG (thứ tự tránh lỗi FK)
-- ============================================================
TRUNCATE TABLE tblAdminAuditLog;
TRUNCATE TABLE tblAdjustmentSuggestion;
TRUNCATE TABLE tblUserFeedback;
TRUNCATE TABLE tblTemplatePortion;
TRUNCATE TABLE tblTemplateMeal;
TRUNCATE TABLE tblMealPlanTemplate;
TRUNCATE TABLE tblPortion;
TRUNCATE TABLE tblMeal;
TRUNCATE TABLE tblMealPlan;
TRUNCATE TABLE tblFavoriteDish;
TRUNCATE TABLE tblDishRating;
TRUNCATE TABLE tblIngredient;
TRUNCATE TABLE tblNutritionInfo;
TRUNCATE TABLE tblDish;
TRUNCATE TABLE tblDishCategory;
TRUNCATE TABLE tblPasswordResetToken;
TRUNCATE TABLE tblHealthGoal;
TRUNCATE TABLE tblHealthProfile;

-- ============================================================
-- MODULE 1: HỒ SƠ SỨC KHOẺ & MỤC TIÊU
-- ============================================================

INSERT INTO tblHealthProfile (id, account_id, full_name, age, gender, height_cm, weight_kg, avatar_url, updated_at) VALUES
(1,  1,  'Quản Trị Viên',       30, 'male',   175.00, 70.00,  'https://cdn.mealplanner.vn/avatars/admin.jpg',  NOW()),
(2,  2,  'Nguyễn Văn An',       24, 'male',   172.50, 68.00,  'https://cdn.mealplanner.vn/avatars/user2.jpg', NOW()),
(3,  3,  'Trần Thị Bình',       28, 'female', 160.00, 55.00,  'https://cdn.mealplanner.vn/avatars/user3.jpg', NOW()),
(4,  4,  'Lê Ngọc Châu',        22, 'female', 158.00, 50.00,  NULL,                                           NOW()),
(5,  5,  'Phạm Tiến Dũng',      35, 'male',   170.00, 82.00,  'https://cdn.mealplanner.vn/avatars/user5.jpg', NOW()),
(6,  6,  'Hoàng Thị Em',        19, 'female', 163.00, 48.00,  NULL,                                           NOW());

INSERT INTO tblHealthGoal (id, account_id, goal_type, activity_level, target_weight_kg, daily_calories_kcal, protein_g_day, carb_g_day, fat_g_day) VALUES
(1,  1,  'maintain',     'medium', 70.00, 2200, 110.00, 275.00, 73.33),
(2,  2,  'maintain',     'medium', 68.00, 2200, 110.00, 275.00, 73.33),
(3,  3,  'weight_loss',  'high',   50.00, 1600,  80.00, 180.00, 53.33),
(4,  4,  'weight_loss',  'low',    47.00, 1400,  70.00, 157.50, 46.67),
(5,  5,  'muscle_gain',  'high',   80.00, 2800, 175.00, 315.00, 77.78),
(6,  6,  'maintain',     'medium', 48.00, 1800,  90.00, 225.00, 60.00);

INSERT INTO tblPasswordResetToken (id, account_id, token, expires_at, used) VALUES
(1,  3,  '123456', DATE_ADD(NOW(), INTERVAL 5 MINUTE), 0),
(2,  4,  '654321', DATE_ADD(NOW(), INTERVAL -10 MINUTE), 1),
(3,  2,  '112233', DATE_ADD(NOW(), INTERVAL -1 HOUR), 1),
(4,  5,  '998877', DATE_ADD(NOW(), INTERVAL 5 MINUTE), 0),
(5,  6,  '445566', DATE_ADD(NOW(), INTERVAL -30 MINUTE), 1);

-- ============================================================
-- MODULE 3: DANH MỤC MÓN ĂN
-- ============================================================

INSERT INTO tblDishCategory (id, name, created_at) VALUES
(1,  'Cơm',                    NOW()),
(2,  'Phở & Bún',              NOW()),
(3,  'Salad & Rau trộn',       NOW()),
(4,  'Sandwich & Bánh mì',     NOW()),
(5,  'Canh & Súp',             NOW()),
(6,  'Thịt nướng & Áp chảo',  NOW()),
(7,  'Hải sản',                NOW()),
(8,  'Đồ uống & Sinh tố',      NOW()),
(9,  'Tráng miệng',            NOW()),
(10, 'Snack & Ăn vặt',         NOW()),
(11, 'Mì & Hủ tiếu',           NOW()),
(12, 'Bánh',                   NOW()),
(13, 'Đồ chay',                NOW()),
(14, 'Món Âu',                 NOW()),
(15, 'Dim sum & Điểm tâm',     NOW());

-- ============================================================
-- MODULE 3: MÓN ĂN (50+ món)
-- ============================================================

INSERT INTO tblDish (id, name, category_id, image_url, source, account_id, difficulty, total_time_min, created_at, updated_at) VALUES
-- ---- CƠM (category 1) ----
(1,  'Cơm gà xối mỡ',              1,  'https://cdn.mealplanner.vn/dishes/com_ga_xoi_mo.jpg',       'system', NULL, 'medium', 45,  NOW(), NOW()),
(2,  'Cơm tấm sườn bì chả',        1,  'https://cdn.mealplanner.vn/dishes/com_tam.jpg',              'system', NULL, 'medium', 60,  NOW(), NOW()),
(3,  'Cơm chiên dương châu',        1,  'https://cdn.mealplanner.vn/dishes/com_chien.jpg',            'system', NULL, 'easy',   20,  NOW(), NOW()),
(4,  'Cơm rang thập cẩm',           1,  'https://cdn.mealplanner.vn/dishes/com_rang.jpg',             'system', NULL, 'easy',   25,  NOW(), NOW()),
(5,  'Cơm niêu cá kho',             1,  'https://cdn.mealplanner.vn/dishes/com_ca_kho.jpg',           'system', NULL, 'medium', 50,  NOW(), NOW()),
-- ---- PHỞ & BÚN (category 2) ----
(6,  'Phở bò tái nạm',              2,  'https://cdn.mealplanner.vn/dishes/pho_bo.jpg',               'system', NULL, 'hard',   120, NOW(), NOW()),
(7,  'Bún bò Huế',                   2,  'https://cdn.mealplanner.vn/dishes/bun_bo_hue.jpg',           'system', NULL, 'hard',   150, NOW(), NOW()),
(8,  'Bún chả Hà Nội',              2,  'https://cdn.mealplanner.vn/dishes/bun_cha.jpg',              'system', NULL, 'medium', 60,  NOW(), NOW()),
(9,  'Phở gà',                       2,  'https://cdn.mealplanner.vn/dishes/pho_ga.jpg',               'system', NULL, 'medium', 90,  NOW(), NOW()),
(10, 'Bún riêu cua',                 2,  'https://cdn.mealplanner.vn/dishes/bun_rieu.jpg',             'system', NULL, 'hard',   120, NOW(), NOW()),
-- ---- SALAD (category 3) ----
(11, 'Salad rau cải trộn',           3,  'https://cdn.mealplanner.vn/dishes/salad_rau.jpg',            'system', NULL, 'easy',   15,  NOW(), NOW()),
(12, 'Salad ức gà quinoa',           3,  'https://cdn.mealplanner.vn/dishes/salad_ga_quinoa.jpg',      'system', NULL, 'easy',   20,  NOW(), NOW()),
(13, 'Salad cá ngừ',                 3,  'https://cdn.mealplanner.vn/dishes/salad_ca_ngu.jpg',         'system', NULL, 'easy',   15,  NOW(), NOW()),
(14, 'Gỏi cuốn tôm thịt',           3,  'https://cdn.mealplanner.vn/dishes/goi_cuon.jpg',             'system', NULL, 'medium', 30,  NOW(), NOW()),
-- ---- SANDWICH (category 4) ----
(15, 'Bánh mì thịt nguội',           4,  'https://cdn.mealplanner.vn/dishes/banh_mi_thit.jpg',         'system', NULL, 'easy',   10,  NOW(), NOW()),
(16, 'Bánh mì trứng pate',           4,  'https://cdn.mealplanner.vn/dishes/banh_mi_trung.jpg',        'system', NULL, 'easy',   10,  NOW(), NOW()),
(17, 'Sandwich gà sốt mayo',         4,  'https://cdn.mealplanner.vn/dishes/sandwich_ga.jpg',          'system', NULL, 'easy',   15,  NOW(), NOW()),
-- ---- CANH & SÚP (category 5) ----
(18, 'Canh chua cá lóc',             5,  'https://cdn.mealplanner.vn/dishes/canh_chua.jpg',            'system', NULL, 'medium', 40,  NOW(), NOW()),
(19, 'Canh bí xanh nấu tôm',         5,  'https://cdn.mealplanner.vn/dishes/canh_bi.jpg',              'system', NULL, 'easy',   25,  NOW(), NOW()),
(20, 'Cháo gà',                       5,  'https://cdn.mealplanner.vn/dishes/chao_ga.jpg',              'system', NULL, 'easy',   60,  NOW(), NOW()),
(21, 'Súp khoai tây cà rốt',         5,  'https://cdn.mealplanner.vn/dishes/sup_khoai.jpg',            'system', NULL, 'easy',   35,  NOW(), NOW()),
(22, 'Canh khổ qua nhồi thịt',       5,  'https://cdn.mealplanner.vn/dishes/canh_kho_qua.jpg',        'system', NULL, 'medium', 45,  NOW(), NOW()),
-- ---- THỊT NƯỚNG (category 6) ----
(23, 'Sườn heo nướng BBQ',           6,  'https://cdn.mealplanner.vn/dishes/suon_bbq.jpg',             'system', NULL, 'medium', 50,  NOW(), NOW()),
(24, 'Gà hấp gừng hành',             6,  'https://cdn.mealplanner.vn/dishes/ga_hap.jpg',               'system', NULL, 'medium', 45,  NOW(), NOW()),
(25, 'Thịt bò lúc lắc',              6,  'https://cdn.mealplanner.vn/dishes/bo_luc_lac.jpg',           'system', NULL, 'medium', 30,  NOW(), NOW()),
(26, 'Ức gà nướng tiêu xanh',        6,  'https://cdn.mealplanner.vn/dishes/ga_nuong.jpg',             'system', NULL, 'easy',   35,  NOW(), NOW()),
(27, 'Thịt heo áp chảo sốt mật ong', 6, 'https://cdn.mealplanner.vn/dishes/heo_mat_ong.jpg',          'system', NULL, 'medium', 30,  NOW(), NOW()),
-- ---- HẢI SẢN (category 7) ----
(28, 'Cá hồi áp chảo',               7,  'https://cdn.mealplanner.vn/dishes/ca_hoi.jpg',               'system', NULL, 'medium', 25,  NOW(), NOW()),
(29, 'Tôm sốt cà chua',              7,  'https://cdn.mealplanner.vn/dishes/tom_sot.jpg',              'system', NULL, 'easy',   20,  NOW(), NOW()),
(30, 'Mực xào sả ớt',                7,  'https://cdn.mealplanner.vn/dishes/muc_xao.jpg',              'system', NULL, 'easy',   20,  NOW(), NOW()),
(31, 'Cua rang muối',                 7,  'https://cdn.mealplanner.vn/dishes/cua_rang.jpg',             'system', NULL, 'hard',   45,  NOW(), NOW()),
-- ---- ĐỒ UỐNG (category 8) ----
(32, 'Sinh tố bơ mật ong',           8,  'https://cdn.mealplanner.vn/dishes/sinh_to_bo.jpg',           'system', NULL, 'easy',   5,   NOW(), NOW()),
(33, 'Smoothie chuối dâu tây',        8,  'https://cdn.mealplanner.vn/dishes/smoothie_chuoi.jpg',       'system', NULL, 'easy',   5,   NOW(), NOW()),
(34, 'Nước ép cà rốt táo gừng',      8,  'https://cdn.mealplanner.vn/dishes/nuoc_ep.jpg',              'system', NULL, 'easy',   10,  NOW(), NOW()),
(35, 'Trà sữa ít đường',             8,  'https://cdn.mealplanner.vn/dishes/tra_sua.jpg',              'system', NULL, 'easy',   10,  NOW(), NOW()),
-- ---- TRÁNG MIỆNG (category 9) ----
(36, 'Yaourt trái cây',               9,  'https://cdn.mealplanner.vn/dishes/yaourt.jpg',               'system', NULL, 'easy',   5,   NOW(), NOW()),
(37, 'Pudding trứng',                  9,  'https://cdn.mealplanner.vn/dishes/pudding.jpg',              'system', NULL, 'medium', 60,  NOW(), NOW()),
(38, 'Chè đậu xanh',                  9,  'https://cdn.mealplanner.vn/dishes/che_dau_xanh.jpg',         'system', NULL, 'medium', 45,  NOW(), NOW()),
-- ---- SNACK (category 10) ----
(39, 'Trứng luộc',                    10, 'https://cdn.mealplanner.vn/dishes/trung_luoc.jpg',            'system', NULL, 'easy',   15,  NOW(), NOW()),
(40, 'Hạt điều rang muối',            10, 'https://cdn.mealplanner.vn/dishes/hat_dieu.jpg',             'system', NULL, 'easy',   10,  NOW(), NOW()),
(41, 'Phô mai que',                    10, 'https://cdn.mealplanner.vn/dishes/pho_mai.jpg',              'system', NULL, 'easy',   5,   NOW(), NOW()),
-- ---- MÌ (category 11) ----
(42, 'Mì Ý bò bằm sốt cà chua',      11, 'https://cdn.mealplanner.vn/dishes/mi_y.jpg',                'system', NULL, 'medium', 35,  NOW(), NOW()),
(43, 'Hủ tiếu Nam Vang',              11, 'https://cdn.mealplanner.vn/dishes/hu_tieu.jpg',              'system', NULL, 'hard',   90,  NOW(), NOW()),
(44, 'Mì xào hải sản',                11, 'https://cdn.mealplanner.vn/dishes/mi_xao.jpg',              'system', NULL, 'medium', 25,  NOW(), NOW()),
-- ---- BÁNH (category 12) ----
(45, 'Bánh yến mạch chuối',           12, 'https://cdn.mealplanner.vn/dishes/banh_yen_mach.jpg',       'system', NULL, 'easy',   30,  NOW(), NOW()),
(46, 'Bánh mì nguyên cám',            12, 'https://cdn.mealplanner.vn/dishes/banh_mi_nguyen_cam.jpg',  'system', NULL, 'hard',   90,  NOW(), NOW()),
-- ---- ĐỒ CHAY (category 13) ----
(47, 'Đậu hũ xào sả ớt',             13, 'https://cdn.mealplanner.vn/dishes/dau_hu_xao.jpg',          'system', NULL, 'easy',   20,  NOW(), NOW()),
(48, 'Rau muống xào tỏi',             13, 'https://cdn.mealplanner.vn/dishes/rau_muong.jpg',           'system', NULL, 'easy',   10,  NOW(), NOW()),
-- ---- MÓN ÂU (category 14) ----
(49, 'Salad Caesar',                   14, 'https://cdn.mealplanner.vn/dishes/salad_caesar.jpg',         'system', NULL, 'easy',   15,  NOW(), NOW()),
(50, 'Gà rán kiểu Mỹ',               14, 'https://cdn.mealplanner.vn/dishes/ga_ran.jpg',               'system', NULL, 'medium', 40,  NOW(), NOW()),
-- ---- DIM SUM (category 15) ----
(51, 'Bánh bao nhân thịt',            15, 'https://cdn.mealplanner.vn/dishes/banh_bao.jpg',            'system', NULL, 'hard',   60,  NOW(), NOW()),
(52, 'Há cảo tôm',                    15, 'https://cdn.mealplanner.vn/dishes/ha_cao.jpg',               'system', NULL, 'hard',   60,  NOW(), NOW()),
-- ---- MÓN TỰ TẠO (custom) ----
(53, 'Salad ức gà tự làm',            3,  NULL, 'custom', 2, 'easy',   20,  NOW(), NOW()),
(54, 'Cơm chiên trứng tôm nhà làm',  1,  NULL, 'custom', 5, 'easy',   25,  NOW(), NOW()),
(55, 'Bowl protein bơ chuối',          3,  NULL, 'custom', 3, 'easy',   10,  NOW(), NOW()),
(56, 'Sinh tố rau xanh detox',         8,  NULL, 'custom', 4, 'easy',   5,   NOW(), NOW());

-- ============================================================
-- NUTRITION INFO (tblNutritionInfo) - cho tất cả 56 món
-- ============================================================

INSERT INTO tblNutritionInfo (dish_id, calories_per_100g, protein_per_100g, carb_per_100g, fat_per_100g, fiber_per_100g, sat_fat_per_100g, vitamin_c_mg, calcium_mg, iron_mg) VALUES
(1,  185.0, 14.5, 22.0,  4.5,  0.5, 1.2,  2.0,  18.0, 1.5),
(2,  210.0, 16.0, 25.0,  6.0,  0.3, 2.0,  1.5,  22.0, 2.1),
(3,  195.0, 8.0,  28.0,  6.0,  0.5, 1.5,  3.0,  15.0, 1.2),
(4,  200.0, 9.0,  26.0,  7.0,  0.6, 1.8,  4.0,  20.0, 1.4),
(5,  165.0, 12.0, 18.0,  5.0,  0.4, 1.0,  2.0,  25.0, 1.8),
(6,  75.0,  8.5,  6.5,   2.0,  0.2, 0.8,  3.0,  30.0, 1.8),
(7,  95.0,  9.0,  8.0,   3.0,  0.3, 1.0,  4.0,  28.0, 2.0),
(8,  130.0, 12.0, 10.0,  4.5,  0.3, 1.5,  2.0,  20.0, 1.6),
(9,  68.0,  7.5,  5.5,   1.8,  0.2, 0.5,  2.0,  22.0, 1.2),
(10, 88.0,  8.0,  8.0,   2.5,  0.4, 0.7,  3.5,  35.0, 1.5),
(11, 35.0,  2.5,  5.5,   0.5,  2.0, 0.1,  25.0, 80.0, 1.0),
(12, 120.0, 18.0, 7.0,   3.0,  2.5, 0.5,  15.0, 45.0, 1.8),
(13, 115.0, 16.0, 5.0,   4.0,  1.5, 0.8,  8.0,  25.0, 1.2),
(14, 105.0, 9.0,  12.0,  2.5,  1.5, 0.6,  10.0, 40.0, 1.0),
(15, 270.0, 12.0, 35.0,  8.0,  1.0, 2.5,  5.0,  40.0, 2.5),
(16, 240.0, 10.0, 32.0,  7.5,  1.0, 2.8,  2.0,  55.0, 2.0),
(17, 255.0, 14.0, 30.0,  9.0,  1.2, 2.0,  3.0,  35.0, 1.8),
(18, 60.0,  6.0,  5.5,   1.5,  0.8, 0.4,  15.0, 55.0, 1.2),
(19, 45.0,  4.5,  4.0,   1.0,  1.2, 0.2,  12.0, 70.0, 0.8),
(20, 80.0,  5.5,  12.0,  1.0,  0.2, 0.3,  2.0,  18.0, 0.8),
(21, 55.0,  1.5,  10.5,  0.8,  1.8, 0.1,  18.0, 30.0, 0.5),
(22, 70.0,  7.0,  4.5,   2.5,  1.0, 0.8,  25.0, 20.0, 1.0),
(23, 295.0, 18.0, 5.0,  22.0,  0.0, 8.0,  1.0,  20.0, 1.8),
(24, 165.0, 18.0, 1.0,  10.0,  0.0, 2.8,  1.0,  12.0, 1.0),
(25, 215.0, 20.0, 3.0,  14.0,  0.0, 5.0,  2.0,  15.0, 2.5),
(26, 160.0, 28.0, 1.5,   5.0,  0.0, 1.2,  2.0,  10.0, 0.8),
(27, 220.0, 16.0, 12.0, 12.0,  0.0, 4.0,  3.0,  18.0, 1.5),
(28, 208.0, 20.0, 0.0,  13.0,  0.0, 3.0,  0.0,  15.0, 0.5),
(29, 95.0,  12.0, 5.0,   3.5,  0.5, 0.8,  8.0,  70.0, 1.5),
(30, 92.0,  14.0, 3.0,   3.0,  0.0, 0.8,  3.0,  30.0, 1.2),
(31, 180.0, 17.0, 2.0,  11.0,  0.0, 3.0,  2.0,  80.0, 0.8),
(32, 160.0, 2.0,  12.0, 13.0,  3.5, 2.8,  8.0,  20.0, 0.6),
(33, 85.0,  1.5,  18.0,  1.0,  1.5, 0.3,  45.0, 15.0, 0.4),
(34, 42.0,  0.8,  9.5,   0.2,  0.8, 0.0,  12.0,  8.0, 0.3),
(35, 120.0, 1.5,  25.0,  2.0,  0.0, 0.8,  0.0,  50.0, 0.1),
(36, 120.0, 4.5,  18.0,  3.5,  0.3, 2.2,  5.0, 140.0, 0.2),
(37, 135.0, 5.5,  16.0,  6.0,  0.0, 2.5,  0.0,  80.0, 0.5),
(38, 95.0,  4.0,  18.0,  1.5,  2.0, 0.4,  2.0,  40.0, 1.5),
(39, 155.0, 13.0, 1.0,  11.0,  0.0, 3.0,  0.0,  55.0, 1.8),
(40, 560.0, 18.0, 28.0, 46.0,  3.0, 9.0,  0.0,  45.0, 5.0),
(41, 380.0, 22.0, 2.0,  31.0,  0.0,20.0,  0.0, 700.0, 0.2),
(42, 155.0, 9.0,  22.0,  3.5,  1.5, 1.2,  8.0,  30.0, 2.0),
(43, 88.0,  7.0,  11.0,  1.5,  0.3, 0.5,  3.0,  28.0, 1.0),
(44, 120.0, 8.5,  14.0,  3.5,  0.8, 1.0,  5.0,  35.0, 1.2),
(45, 280.0, 8.0,  45.0,  8.0,  4.0, 2.0,  2.0,  40.0, 3.5),
(46, 250.0, 9.0,  48.0,  2.5,  5.0, 0.4,  0.0,  30.0, 2.5),
(47, 75.0,  7.5,  3.0,   4.5,  0.5, 0.7,  5.0, 180.0, 2.5),
(48, 25.0,  2.5,  2.5,   0.5,  1.5, 0.1,  30.0, 65.0, 2.0),
(49, 150.0, 8.0,  8.0,  10.0,  1.5, 3.0,  10.0,100.0, 1.0),
(50, 280.0, 20.0, 12.0, 18.0,  0.5, 4.5,  2.0,  18.0, 1.5),
(51, 220.0, 10.0, 32.0,  6.0,  1.0, 2.0,  1.0,  35.0, 2.0),
(52, 185.0, 12.0, 22.0,  5.5,  0.8, 1.5,  2.0,  40.0, 1.8),
(53, 130.0, 22.0, 5.0,   3.0,  1.5, 0.6,  18.0, 30.0, 1.2),
(54, 190.0, 9.5,  25.0,  6.0,  0.5, 1.5,  4.0,  22.0, 1.2),
(55, 145.0, 15.0, 14.0,  5.0,  3.0, 1.0,  12.0, 35.0, 1.5),
(56, 35.0,  2.0,  6.0,   0.5,  2.5, 0.1,  40.0, 50.0, 1.0);

-- ============================================================
-- NGUYÊN LIỆU (tblIngredient) - khoảng 20 món có nguyên liệu
-- ============================================================

INSERT INTO tblIngredient (dish_id, name, quantity_g, unit) VALUES
-- Cơm gà xối mỡ (1)
(1, 'Gạo tẻ',           200, 'g'),
(1, 'Ức gà',            150, 'g'),
(1, 'Dầu ăn',            30, 'ml'),
(1, 'Hành tím',          20, 'g'),
(1, 'Nước mắm',          15, 'ml'),
-- Cơm chiên dương châu (3)
(3, 'Cơm nguội',        200, 'g'),
(3, 'Trứng gà',          55, 'g'),
(3, 'Tôm',               50, 'g'),
(3, 'Xúc xích',          30, 'g'),
(3, 'Hành lá',           10, 'g'),
(3, 'Dầu ăn',            20, 'ml'),
-- Phở bò tái nạm (6)
(6, 'Bánh phở tươi',    200, 'g'),
(6, 'Thịt bò tái',      100, 'g'),
(6, 'Xương bò',         300, 'g'),
(6, 'Hành tây',          50, 'g'),
(6, 'Gừng nướng',        30, 'g'),
(6, 'Quế hồi',            5, 'g'),
(6, 'Ngò gai',           10, 'g'),
-- Bún chả Hà Nội (8)
(8, 'Bún tươi',         200, 'g'),
(8, 'Thịt ba chỉ heo',  100, 'g'),
(8, 'Chả viên',          80, 'g'),
(8, 'Nước mắm',          20, 'ml'),
(8, 'Đường',             15, 'g'),
(8, 'Tỏi ớt',           10, 'g'),
-- Salad rau cải (11)
(11, 'Rau xà lách',     100, 'g'),
(11, 'Cà chua bi',       50, 'g'),
(11, 'Dưa leo',          50, 'g'),
(11, 'Dầu olive',        15, 'ml'),
(11, 'Giấm táo',         10, 'ml'),
-- Salad ức gà quinoa (12)
(12, 'Ức gà luộc',      100, 'g'),
(12, 'Quinoa',           50, 'g'),
(12, 'Rau xà lách',      80, 'g'),
(12, 'Cà chua bi',       40, 'g'),
(12, 'Dầu olive',        10, 'ml'),
-- Gỏi cuốn tôm thịt (14)
(14, 'Bánh tráng',        50, 'g'),
(14, 'Tôm luộc',          80, 'g'),
(14, 'Thịt heo luộc',     60, 'g'),
(14, 'Bún tươi',          60, 'g'),
(14, 'Rau thơm',          30, 'g'),
-- Canh chua cá lóc (18)
(18, 'Cá lóc',           200, 'g'),
(18, 'Cà chua',           80, 'g'),
(18, 'Dứa',               60, 'g'),
(18, 'Giá đỗ',            50, 'g'),
(18, 'Nước mắm',          15, 'ml'),
-- Cháo gà (20)
(20, 'Gạo tẻ',            80, 'g'),
(20, 'Thịt gà',          120, 'g'),
(20, 'Gừng',              10, 'g'),
(20, 'Hành lá',           10, 'g'),
-- Cá hồi áp chảo (28)
(28, 'Cá hồi phi lê',    200, 'g'),
(28, 'Dầu olive',         20, 'ml'),
(28, 'Tỏi',               10, 'g'),
(28, 'Chanh',             30, 'g'),
(28, 'Tiêu',               3, 'g'),
-- Tôm sốt cà chua (29)
(29, 'Tôm sú',           200, 'g'),
(29, 'Cà chua',          100, 'g'),
(29, 'Hành tây',          50, 'g'),
(29, 'Tỏi',              10,  'g'),
(29, 'Dầu ăn',           20,  'ml'),
-- Sinh tố bơ mật ong (32)
(32, 'Bơ chín',          150, 'g'),
(32, 'Sữa tươi',         150, 'ml'),
(32, 'Mật ong',           20, 'ml'),
(32, 'Đá viên',           80, 'g'),
-- Yaourt trái cây (36)
(36, 'Yaourt không đường', 150, 'g'),
(36, 'Dâu tây',           60, 'g'),
(36, 'Chuối',             50, 'g'),
(36, 'Mật ong',           15, 'ml'),
-- Mì Ý bò bằm (42)
(42, 'Mì spaghetti',     120, 'g'),
(42, 'Thịt bò bằm',     100, 'g'),
(42, 'Sốt cà chua',     100, 'g'),
(42, 'Hành tây',          50, 'g'),
(42, 'Tỏi',              10,  'g'),
(42, 'Dầu olive',        15,  'ml'),
-- Salad ức gà tự làm custom (53)
(53, 'Ức gà luộc',       120, 'g'),
(53, 'Rau xà lách xoăn',  80, 'g'),
(53, 'Bơ chín',           60, 'g'),
(53, 'Nước cốt chanh',    15, 'ml'),
(53, 'Dầu olive',         10, 'ml'),
-- Bowl protein bơ chuối custom (55)
(55, 'Ức gà luộc',       100, 'g'),
(55, 'Bơ chín',           80, 'g'),
(55, 'Chuối',             60, 'g'),
(55, 'Hạt chia',          10, 'g'),
(55, 'Mật ong',           10, 'ml');

-- ============================================================
-- ĐÁNH GIÁ MÓN ĂN (tblDishRating) - 25 bản ghi
-- ============================================================

INSERT INTO tblDishRating (account_id, dish_id, score, comment, created_at) VALUES
(2,  1,  5, 'Cơm gà xối mỡ ngon tuyệt, đúng vị Sài Gòn!',             NOW()),
(2,  6,  4, 'Phở nước dùng đậm đà, rau thơm tươi.',                    NOW()),
(2,  28, 5, 'Cá hồi da giòn, thịt mềm, rất ngon.',                     NOW()),
(2,  11, 4, 'Salad thanh mát, phù hợp eat clean.',                       NOW()),
(2,  32, 5, 'Sinh tố bơ béo ngậy, thêm chút mật ong rất hợp vị.',      NOW()),
(3,  1,  4, 'Cơm gà ngon nhưng hơi nhiều dầu.',                         NOW()),
(3,  11, 5, 'Rau tươi, dầu olive thơm, ăn rất nhẹ bụng.',              NOW()),
(3,  12, 5, 'Salad quinoa bổ dưỡng, protein cao, thích hợp giảm cân.', NOW()),
(3,  36, 4, 'Yaourt trái cây ngon mát, ít calo.',                        NOW()),
(3,  20, 5, 'Cháo gà thơm ngọt, ăn lúc ốm tuyệt vời.',                 NOW()),
(4,  11, 5, 'Salad phù hợp chế độ ăn kiêng, rất tươi.',                 NOW()),
(4,  36, 4, 'Ngon và lành mạnh.',                                         NOW()),
(4,  56, 5, 'Sinh tố rau xanh detox, uống buổi sáng rất tốt!',          NOW()),
(4,  14, 4, 'Gỏi cuốn tươi, chấm tương ngon.',                           NOW()),
(4,  47, 5, 'Đậu hũ xào sả ớt chay mà ngon không kém gì mặn.',         NOW()),
(5,  23, 5, 'Sườn BBQ thấm gia vị, nướng đều lửa, rất ngon.',          NOW()),
(5,  2,  4, 'Cơm tấm chuẩn vị Sài Gòn.',                                NOW()),
(5,  25, 5, 'Bò lúc lắc mềm, thơm bơ, đậm đà.',                        NOW()),
(5,  28, 4, 'Cá hồi áp chảo chuẩn nhà hàng, healthy.',                  NOW()),
(5,  42, 3, 'Mì Ý ổn, nhưng chưa đủ đậm đà như mong đợi.',             NOW()),
(2,  26, 4, 'Ức gà nướng tiêu xanh protein cao, ít mỡ.',                NOW()),
(3,  48, 5, 'Rau muống xào tỏi dân dã mà ngon khó cưỡng.',             NOW()),
(4,  19, 4, 'Canh bí xanh mát, thanh nhẹ.',                              NOW()),
(5,  50, 4, 'Gà rán giòn rụm, ăn kèm salad rất cân bằng.',             NOW()),
(2,  39, 5, 'Trứng luộc đơn giản nhưng tiện lợi, snack lành mạnh.',    NOW());

-- ============================================================
-- MÓN ĂN YÊU THÍCH (tblFavoriteDish) - 22 bản ghi
-- ============================================================

INSERT INTO tblFavoriteDish (account_id, dish_id, saved_at) VALUES
(2,  1,  NOW()),
(2,  28, NOW()),
(2,  32, NOW()),
(2,  11, NOW()),
(2,  26, NOW()),
(3,  11, NOW()),
(3,  12, NOW()),
(3,  36, NOW()),
(3,  20, NOW()),
(3,  53, NOW()),
(4,  11, NOW()),
(4,  36, NOW()),
(4,  47, NOW()),
(4,  56, NOW()),
(4,  14, NOW()),
(5,  23, NOW()),
(5,  2,  NOW()),
(5,  25, NOW()),
(5,  28, NOW()),
(5,  54, NOW()),
(1,  6,  NOW()),
(1,  28, NOW());

-- ============================================================
-- MODULE 2: KẾ HOẠCH BỮA ĂN (tblMealPlan) - 20 bản ghi
-- ============================================================

INSERT INTO tblMealPlan (id, account_id, plan_name, plan_date, created_at, updated_at) VALUES
(1,  2, 'Kế hoạch thứ Hai',        '2026-05-04', NOW(), NOW()),
(2,  2, 'Kế hoạch thứ Ba',         '2026-05-05', NOW(), NOW()),
(3,  2, 'Kế hoạch thứ Tư',         '2026-05-06', NOW(), NOW()),
(4,  2, 'Kế hoạch thứ Năm',        '2026-05-07', NOW(), NOW()),
(5,  3, 'Thực đơn giảm cân - N1',  '2026-05-04', NOW(), NOW()),
(6,  3, 'Thực đơn giảm cân - N2',  '2026-05-05', NOW(), NOW()),
(7,  3, 'Thực đơn giảm cân - N3',  '2026-05-06', NOW(), NOW()),
(8,  4, 'Kế hoạch eat clean - N1', '2026-05-04', NOW(), NOW()),
(9,  4, 'Kế hoạch eat clean - N2', '2026-05-05', NOW(), NOW()),
(10, 4, 'Kế hoạch eat clean - N3', '2026-05-06', NOW(), NOW()),
(11, 5, 'Tăng cơ nạc - Ngày 1',    '2026-05-04', NOW(), NOW()),
(12, 5, 'Tăng cơ nạc - Ngày 2',    '2026-05-05', NOW(), NOW()),
(13, 5, 'Tăng cơ nạc - Ngày 3',    '2026-05-06', NOW(), NOW()),
(14, 1, 'Thực đơn admin mẫu',      '2026-05-04', NOW(), NOW()),
(15, 2, 'Kế hoạch thứ Sáu',        '2026-05-08', NOW(), NOW()),
(16, 3, 'Thực đơn giảm cân - N4',  '2026-05-07', NOW(), NOW()),
(17, 4, 'Kế hoạch eat clean - N4', '2026-05-07', NOW(), NOW()),
(18, 5, 'Tăng cơ nạc - Ngày 4',    '2026-05-07', NOW(), NOW()),
(19, 2, 'Kế hoạch thứ Bảy',        '2026-05-09', NOW(), NOW()),
(20, 3, 'Thực đơn giảm cân - N5',  '2026-05-08', NOW(), NOW());

-- ============================================================
-- BỮA ĂN (tblMeal) - mỗi plan có 4 bữa = 20 plans * tối đa 4 bữa
-- ============================================================

INSERT INTO tblMeal (id, meal_plan_id, meal_type, created_at) VALUES
-- Plan 1 (user 2)
(1,  1,  'breakfast', NOW()),
(2,  1,  'lunch',     NOW()),
(3,  1,  'dinner',    NOW()),
(4,  1,  'snack',     NOW()),
-- Plan 2 (user 2)
(5,  2,  'breakfast', NOW()),
(6,  2,  'lunch',     NOW()),
(7,  2,  'dinner',    NOW()),
(8,  2,  'snack',     NOW()),
-- Plan 3 (user 2)
(9,  3,  'breakfast', NOW()),
(10, 3,  'lunch',     NOW()),
(11, 3,  'dinner',    NOW()),
-- Plan 5 (user 3)
(12, 5,  'breakfast', NOW()),
(13, 5,  'lunch',     NOW()),
(14, 5,  'dinner',    NOW()),
(15, 5,  'snack',     NOW()),
-- Plan 6 (user 3)
(16, 6,  'breakfast', NOW()),
(17, 6,  'lunch',     NOW()),
(18, 6,  'dinner',    NOW()),
(19, 6,  'snack',     NOW()),
-- Plan 7 (user 3)
(20, 7,  'breakfast', NOW()),
(21, 7,  'lunch',     NOW()),
(22, 7,  'dinner',    NOW()),
-- Plan 8 (user 4)
(23, 8,  'breakfast', NOW()),
(24, 8,  'lunch',     NOW()),
(25, 8,  'dinner',    NOW()),
(26, 8,  'snack',     NOW()),
-- Plan 9 (user 4)
(27, 9,  'breakfast', NOW()),
(28, 9,  'lunch',     NOW()),
(29, 9,  'dinner',    NOW()),
-- Plan 11 (user 5)
(30, 11, 'breakfast', NOW()),
(31, 11, 'lunch',     NOW()),
(32, 11, 'dinner',    NOW()),
(33, 11, 'snack',     NOW()),
-- Plan 12 (user 5)
(34, 12, 'breakfast', NOW()),
(35, 12, 'lunch',     NOW()),
(36, 12, 'dinner',    NOW()),
-- Plan 14 (admin)
(37, 14, 'breakfast', NOW()),
(38, 14, 'lunch',     NOW()),
(39, 14, 'dinner',    NOW());

-- ============================================================
-- KHẨU PHẦN (tblPortion) - có tính nutrition sẵn
-- ============================================================

INSERT INTO tblPortion (meal_id, dish_id, quantity_g, calories_kcal, protein_g, carb_g, fat_g) VALUES
-- Plan 1 - Breakfast (meal 1): Bánh yến mạch + Sinh tố
(1,  45, 120.0, 336.0, 9.6,  54.0,  9.6),
(1,  32, 200.0, 320.0, 4.0,  24.0, 26.0),
-- Plan 1 - Lunch (meal 2): Cơm gà xối mỡ
(2,   1, 350.0, 647.5,50.75, 77.0, 15.75),
-- Plan 1 - Dinner (meal 3): Cá hồi áp chảo + Salad rau cải
(3,  28, 200.0, 416.0, 40.0,  0.0, 26.0),
(3,  11, 150.0,  52.5,  3.75, 8.25, 0.75),
-- Plan 1 - Snack (meal 4): Yaourt trái cây + Trứng luộc
(4,  36, 150.0, 180.0,  6.75,27.0,  5.25),
(4,  39, 110.0, 170.5, 14.3,  1.1, 12.1),
-- Plan 2 - Breakfast (meal 5): Cháo gà
(5,  20, 300.0, 240.0, 16.5, 36.0,  3.0),
-- Plan 2 - Lunch (meal 6): Phở bò tái nạm
(6,   6, 500.0, 375.0, 42.5, 32.5, 10.0),
-- Plan 2 - Dinner (meal 7): Gà hấp gừng hành + Salad ức gà quinoa
(7,  24, 200.0, 330.0, 36.0,  2.0, 20.0),
(7,  12, 150.0, 180.0, 27.0, 10.5,  4.5),
-- Plan 2 - Snack (meal 8): Hạt điều rang muối
(8,  40,  30.0, 168.0,  5.4,  8.4, 13.8),
-- Plan 3 - Breakfast (meal 9): Sandwich gà sốt mayo + Nước ép
(9,  17, 150.0, 382.5, 21.0, 45.0, 13.5),
(9,  34, 200.0,  84.0,  1.6, 19.0,  0.4),
-- Plan 3 - Lunch (meal 10): Bún chả Hà Nội
(10,  8, 400.0, 520.0, 48.0, 40.0, 18.0),
-- Plan 3 - Dinner (meal 11): Thịt bò lúc lắc + Cơm chiên dương châu
(11, 25, 150.0, 322.5, 30.0,  4.5, 21.0),
(11,  3, 200.0, 390.0, 16.0, 56.0, 12.0),
-- Plan 5 (user 3) - Breakfast (meal 12): Salad rau + Trứng luộc
(12, 11, 200.0,  70.0,  5.0, 11.0,  1.0),
(12, 39, 110.0, 170.5, 14.3,  1.1, 12.1),
-- Plan 5 - Lunch (meal 13): Canh chua cá lóc + ít cơm
(13, 18, 300.0, 180.0, 18.0, 16.5,  4.5),
(13,  1, 150.0, 277.5, 21.75,33.0,  6.75),
-- Plan 5 - Dinner (meal 14): Cá hồi áp chảo + Salad ức gà tự làm
(14, 28, 150.0, 312.0, 30.0,  0.0, 19.5),
(14, 53, 200.0, 260.0, 44.0, 10.0,  6.0),
-- Plan 5 - Snack (meal 15): Yaourt
(15, 36, 100.0, 120.0,  4.5, 18.0,  3.5),
-- Plan 6 (user 3) - Breakfast (meal 16): Bowl protein tự làm
(16, 55, 250.0, 362.5, 37.5, 35.0, 12.5),
-- Plan 6 - Lunch (meal 17): Bún bò Huế
(17,  7, 450.0, 427.5, 40.5, 36.0, 13.5),
-- Plan 6 - Dinner (meal 18): Ức gà nướng tiêu xanh + Canh bí
(18, 26, 200.0, 320.0, 56.0,  3.0, 10.0),
(18, 19, 200.0,  90.0,  9.0,  8.0,  2.0),
-- Plan 6 - Snack (meal 19): Phô mai que
(19, 41,  40.0, 152.0,  8.8,  0.8, 12.4),
-- Plan 7 (user 3) - Breakfast (meal 20): Salad Caesar
(20, 49, 200.0, 300.0, 16.0, 16.0, 20.0),
-- Plan 7 - Lunch (meal 21): Gỏi cuốn tôm thịt
(21, 14, 300.0, 315.0, 27.0, 36.0,  7.5),
-- Plan 7 - Dinner (meal 22): Đậu hũ xào sả ớt + Rau muống xào tỏi
(22, 47, 200.0, 150.0, 15.0,  6.0,  9.0),
(22, 48, 200.0,  50.0,  5.0,  5.0,  1.0),
-- Plan 8 (user 4) - Breakfast (meal 23): Sinh tố detox + Bánh yến mạch
(23, 56, 300.0, 105.0,  6.0, 18.0,  1.5),
(23, 45, 100.0, 280.0,  8.0, 45.0,  8.0),
-- Plan 8 - Lunch (meal 24): Salad ức gà quinoa + Canh khổ qua
(24, 12, 200.0, 240.0, 36.0, 14.0,  6.0),
(24, 22, 250.0, 175.0, 17.5, 11.25, 6.25),
-- Plan 8 - Dinner (meal 25): Cá hồi áp chảo
(25, 28, 200.0, 416.0, 40.0,  0.0, 26.0),
-- Plan 8 - Snack (meal 26): Trứng luộc + Hạt điều
(26, 39, 110.0, 170.5, 14.3,  1.1, 12.1),
(26, 40,  20.0, 112.0,  3.6,  5.6,  9.2),
-- Plan 9 (user 4) - Breakfast (meal 27): Smoothie chuối + Bánh mì nguyên cám
(27, 33, 250.0, 212.5,  3.75,45.0,  2.5),
(27, 46, 100.0, 250.0,  9.0, 48.0,  2.5),
-- Plan 9 - Lunch (meal 28): Phở gà
(28,  9, 450.0, 306.0, 33.75,24.75, 8.1),
-- Plan 9 - Dinner (meal 29): Tôm sốt cà chua + Cơm gà
(29, 29, 200.0, 190.0, 24.0, 10.0,  7.0),
(29,  1, 200.0, 370.0, 29.0, 44.0,  9.0),
-- Plan 11 (user 5) - Breakfast (meal 30): Cơm tấm sườn bì chả
(30,  2, 400.0, 840.0, 64.0,100.0, 24.0),
-- Plan 11 - Lunch (meal 31): Sườn BBQ + Cơm chiên
(31, 23, 250.0, 737.5, 45.0, 12.5, 55.0),
(31,  3, 200.0, 390.0, 16.0, 56.0, 12.0),
-- Plan 11 - Dinner (meal 32): Cá hồi áp chảo + Tôm sốt cà chua
(32, 28, 200.0, 416.0, 40.0,  0.0, 26.0),
(32, 29, 150.0, 142.5, 18.0,  7.5,  5.25),
-- Plan 11 - Snack (meal 33): Sinh tố bơ mật ong
(33, 32, 300.0, 480.0,  6.0, 36.0, 39.0),
-- Plan 12 (user 5) - Breakfast (meal 34): Cơm niêu cá kho
(34,  5, 300.0, 495.0, 36.0, 54.0, 15.0),
-- Plan 12 - Lunch (meal 35): Bò lúc lắc + Cơm rang
(35, 25, 200.0, 430.0, 40.0,  6.0, 28.0),
(35,  4, 200.0, 400.0, 18.0, 52.0, 14.0),
-- Plan 12 - Dinner (meal 36): Mực xào sả ớt
(36, 30, 250.0, 230.0, 35.0,  7.5,  7.5),
-- Plan 14 (admin) - Breakfast (meal 37): Trứng chiên + Nước ép
(37, 39, 110.0, 170.5, 14.3,  1.1, 12.1),
(37, 34, 250.0, 105.0,  2.0, 23.75, 0.5),
-- Plan 14 - Lunch (meal 38): Phở bò tái nạm
(38,  6, 500.0, 375.0, 42.5, 32.5, 10.0),
-- Plan 14 - Dinner (meal 39): Gà hấp gừng hành + Súp khoai tây
(39, 24, 200.0, 330.0, 36.0,  2.0, 20.0),
(39, 21, 200.0, 110.0,  3.0, 21.0,  1.6);

-- ============================================================
-- KẾ HOẠCH MẪU (tblMealPlanTemplate) - 6 mẫu
-- ============================================================

INSERT INTO tblMealPlanTemplate (id, account_id, template_name, saved_at) VALUES
(1, 2, 'Tuần duy trì cân nặng - cơ bản',    NOW()),
(2, 3, 'Thực đơn giảm cân 7 ngày',           NOW()),
(3, 5, 'Tăng cơ nạc - mẫu cơ bản',          NOW()),
(4, 4, 'Eat clean cả tuần',                   NOW()),
(5, 2, 'Kế hoạch protein cao',                NOW()),
(6, 1, 'Mẫu quản trị cân đối',               NOW());

INSERT INTO tblTemplateMeal (id, template_id, meal_type) VALUES
(1,  1, 'breakfast'),
(2,  1, 'lunch'),
(3,  1, 'dinner'),
(4,  1, 'snack'),
(5,  2, 'breakfast'),
(6,  2, 'lunch'),
(7,  2, 'dinner'),
(8,  2, 'snack'),
(9,  3, 'breakfast'),
(10, 3, 'lunch'),
(11, 3, 'dinner'),
(12, 3, 'snack'),
(13, 4, 'breakfast'),
(14, 4, 'lunch'),
(15, 4, 'dinner'),
(16, 4, 'snack'),
(17, 5, 'breakfast'),
(18, 5, 'lunch'),
(19, 5, 'dinner'),
(20, 6, 'breakfast'),
(21, 6, 'lunch'),
(22, 6, 'dinner');

INSERT INTO tblTemplatePortion (template_meal_id, dish_id, quantity_g) VALUES
-- Template 1 - duy trì cân nặng
(1,  45, 120.0),  -- bánh yến mạch
(1,  32, 200.0),  -- sinh tố bơ
(2,   1, 350.0),  -- cơm gà
(3,  28, 200.0),  -- cá hồi áp chảo
(3,  11, 150.0),  -- salad rau
(4,  36, 150.0),  -- yaourt
-- Template 2 - giảm cân
(5,  11, 200.0),  -- salad rau
(5,  39, 110.0),  -- trứng luộc
(6,  12, 200.0),  -- salad quinoa
(6,  18, 250.0),  -- canh chua
(7,  28, 150.0),  -- cá hồi
(7,  53, 150.0),  -- salad ức gà tự làm
(8,  36,  80.0),  -- yaourt nhỏ
-- Template 3 - tăng cơ
(9,   2, 400.0),  -- cơm tấm
(10, 23, 250.0),  -- sườn BBQ
(10,  3, 200.0),  -- cơm chiên
(11, 28, 200.0),  -- cá hồi
(11, 29, 150.0),  -- tôm
(12, 32, 300.0),  -- sinh tố bơ
-- Template 4 - eat clean
(13, 56, 300.0),  -- sinh tố detox
(13, 45, 100.0),  -- bánh yến mạch
(14, 12, 200.0),  -- salad quinoa
(14, 22, 250.0),  -- canh khổ qua
(15, 28, 200.0),  -- cá hồi
(16, 39, 110.0),  -- trứng luộc
-- Template 5 - protein cao
(17, 39, 110.0),  -- trứng luộc
(18, 26, 200.0),  -- ức gà nướng
(19, 28, 200.0),  -- cá hồi
-- Template 6 - admin
(20,  9, 400.0),  -- phở gà
(21,  6, 500.0),  -- phở bò
(22, 24, 200.0);  -- gà hấp

-- ============================================================
-- GỢI Ý ĐIỀU CHỈNH (tblAdjustmentSuggestion) - 20 bản ghi
-- ============================================================

INSERT INTO tblAdjustmentSuggestion (account_id, meal_plan_id, suggestion_type, content, status, created_at) VALUES
(2, 1,  'add_dish',       'Bữa sáng còn thiếu ~150 kcal. Thêm 1 lát bánh mì nguyên cám hoặc hũ yaourt.',                               'pending',   NOW()),
(2, 1,  'reduce_portion', 'Khẩu phần sinh tố bơ 200g cung cấp khá nhiều chất béo. Cân nhắc giảm xuống 150g.',                            'dismissed', NOW()),
(2, 2,  'add_dish',       'Bữa chiều thiếu protein. Thêm 2 trứng luộc hoặc 100g ức gà để đủ mục tiêu.',                                  'applied',   NOW()),
(3, 5,  'swap_dish',      'Thay bún bò Huế bằng phở gà hoặc canh chua để giảm ~80 kcal và chất béo.',                                    'applied',   NOW()),
(3, 5,  'add_dish',       'Bữa tối đang thấp hơn mục tiêu ~120 kcal. Thêm khoai lang hấp hoặc đậu hũ.',                                  'pending',   NOW()),
(3, 6,  'reduce_portion', 'Khẩu phần bún bò 450g hơi nhiều so với mục tiêu giảm cân. Thử giảm xuống 350g.',                              'pending',   NOW()),
(4, 8,  'add_dish',       'Bữa sáng thiếu carbohydrate. Thêm 1 lát bánh mì nguyên cám để cung cấp năng lượng ban ngày.',                 'pending',   NOW()),
(4, 8,  'swap_dish',      'Salad Caesar (150 kcal/100g) chứa nhiều dầu. Thử Salad rau cải (35 kcal/100g) để ít calo hơn.',               'dismissed', NOW()),
(4, 9,  'reduce_portion', 'Smoothie chuối 250ml đang cung cấp 212 kcal. Giảm xuống 200ml tiết kiệm ~30 kcal.',                           'pending',   NOW()),
(5, 11, 'add_dish',       'Để đạt protein 175g/ngày, bữa snack cần thêm nguồn protein. Gợi ý: trứng luộc hoặc 100g ức gà.',              'applied',   NOW()),
(5, 11, 'add_dish',       'Tổng calo hôm nay mới đạt 85% mục tiêu tăng cơ. Thêm sinh tố bơ mật ong vào buổi tối.',                      'pending',   NOW()),
(5, 12, 'swap_dish',      'Cơm rang thập cẩm chứa nhiều dầu. Thay bằng cơm gà xối mỡ để giảm 30 kcal/100g.',                            'pending',   NOW()),
(2, 3,  'add_dish',       'Bữa trưa chưa có rau. Thêm salad rau cải hoặc canh bí xanh để bổ sung chất xơ.',                              'applied',   NOW()),
(3, 7,  'add_dish',       'Hôm nay chưa đủ vitamin C. Thêm 1 ly nước ép cà rốt táo gừng hoặc ăn thêm rau.',                             'pending',   NOW()),
(4, 10, 'reduce_portion', 'Khẩu phần cơm gà 300g đang cao hơn mục tiêu. Giảm xuống 200g và bổ sung thêm rau.',                           'pending',   NOW()),
(2, 4,  'add_dish',       'Bữa sáng cần nhiều protein hơn. Thêm 2 trứng luộc hoặc 120g ức gà nướng.',                                    'pending',   NOW()),
(5, 13, 'add_dish',       'Canxi hôm nay thấp. Thêm 1 hũ yaourt hoặc phô mai que vào bữa snack.',                                        'pending',   NOW()),
(3, 16, 'swap_dish',      'Có thể thay bún riêu bằng canh chua cá lóc để giảm calo mà vẫn đủ dinh dưỡng.',                               'pending',   NOW()),
(4, 17, 'add_dish',       'Bữa tối còn thiếu chất béo lành mạnh. Thêm bơ chín hoặc hạt điều vào bữa snack.',                             'applied',   NOW()),
(2, 19, 'add_dish',       'Cuối tuần nên tăng thêm chất xơ. Thêm gỏi cuốn tôm thịt hoặc salad quinoa vào bữa trưa.',                    'pending',   NOW());

-- ============================================================
-- PHẢN HỒI NGƯỜI DÙNG (tblUserFeedback) - 20 bản ghi
-- ============================================================

INSERT INTO tblUserFeedback (id, account_id, content, status, admin_note, submitted_at) VALUES
(1,  2, 'Ứng dụng rất hữu ích! Mong sớm thêm tính năng quét mã vạch thực phẩm.',                                'pending',    NULL,                                                                         NOW()),
(2,  3, 'Gợi ý điều chỉnh thực đơn chính xác và thiết thực. Mong có thêm gợi ý.',                               'resolved',   'Cảm ơn phản hồi! Chúng tôi đang phát triển tính năng AI gợi ý nâng cao.',   NOW()),
(3,  4, 'Không đăng nhập được, báo lỗi "account locked" dù chưa nhập sai mật khẩu.',                            'processing', 'Đang kiểm tra hệ thống, sẽ phản hồi trong 24h.',                             NOW()),
(4,  5, 'Database thiếu nhiều món Việt vùng miền: bánh xèo, bún chả, mì Quảng, bánh cuốn...',                   'pending',    NULL,                                                                         NOW()),
(5,  6, 'Tài khoản bị khoá không có thông báo rõ ràng lý do.',                                                    'processing', 'Đang xem xét mở lại tài khoản.',                                             NOW()),
(6,  2, 'Giao diện lịch tuần rất trực quan. Nhưng cần có nút "Sao chép thực đơn" sang tuần sau.',                'pending',    NULL,                                                                         NOW()),
(7,  3, 'Tính năng tính toán calo rất chính xác. Cảm ơn nhóm phát triển!',                                       'resolved',   'Cảm ơn phản hồi tích cực!',                                                  NOW()),
(8,  4, 'Nên thêm bộ lọc tìm món theo chế độ ăn: Keto, Vegan, Low-carb...',                                      'pending',    NULL,                                                                         NOW()),
(9,  5, 'Phần thống kê dinh dưỡng rất chi tiết. Mong có thêm biểu đồ theo tháng.',                              'processing', 'Đang lên kế hoạch phát triển biểu đồ dài hạn.',                              NOW()),
(10, 2, 'Ứng dụng đôi khi load chậm khi mở trang kế hoạch bữa ăn tuần.',                                        'processing', 'Đang tối ưu hiệu năng backend.',                                              NOW()),
(11, 3, 'Nên cho phép người dùng nhập thêm món ăn với đầy đủ thông tin dinh dưỡng tự động từ AI.',               'pending',    NULL,                                                                         NOW()),
(12, 4, 'Tính năng lưu kế hoạch mẫu rất tiện. Nên cho phép đặt tên và mô tả chi tiết hơn.',                     'resolved',   'Đã ghi nhận và sẽ bổ sung trong version tiếp theo.',                        NOW()),
(13, 5, 'Mong có thể chia sẻ kế hoạch bữa ăn cho bạn bè hoặc gia đình.',                                         'pending',    NULL,                                                                         NOW()),
(14, 2, 'Nút "Thêm món" đôi khi không phản hồi trên mobile. Cần kiểm tra lại.',                                  'processing', 'Đang fix bug trên mobile.',                                                   NOW()),
(15, 3, 'Thông tin dinh dưỡng một số món chưa đầy đủ (thiếu vitamin và khoáng chất).',                          'pending',    NULL,                                                                         NOW()),
(16, 4, 'Rất thích tính năng so sánh dinh dưỡng thực tế vs mục tiêu. Rất trực quan!',                            'resolved',   'Cảm ơn!',                                                                     NOW()),
(17, 5, 'Nên có tính năng nhắc nhở uống nước mỗi 2 tiếng.',                                                       'pending',    NULL,                                                                         NOW()),
(18, 2, 'Có thể thêm chức năng export kế hoạch bữa ăn ra file PDF hoặc Excel không?',                            'pending',    NULL,                                                                         NOW()),
(19, 3, 'Mong thêm bộ lọc tìm kiếm món theo thời gian nấu (dưới 15 phút, 30 phút).',                             'processing', 'Đã lên kế hoạch triển khai trong sprint tới.',                               NOW()),
(20, 4, 'Tính năng gợi ý nguyên liệu thay thế rất hay. Mong mở rộng thêm nhiều món hơn.',                        'resolved',   'Cảm ơn! Đang mở rộng database nguyên liệu thay thế.',                        NOW());

-- ============================================================
-- NHẬT KÝ ADMIN (tblAdminAuditLog) - 20 bản ghi
-- ============================================================

INSERT INTO tblAdminAuditLog (admin_id, action, target_type, target_id, note, acted_at) VALUES
(1, 'lock_account',     'tblUserAccount',  6,  'Khoá tài khoản do phát hiện đăng nhập bất thường từ nhiều IP.',           NOW()),
(1, 'resolve_feedback', 'tblUserFeedback', 2,  'Đã phản hồi người dùng và ghi nhận yêu cầu tính năng.',                   NOW()),
(1, 'update_dish',      'tblDish',         6,  'Cập nhật ảnh và thông tin dinh dưỡng cho Phở bò tái nạm.',                 NOW()),
(1, 'create_dish',      'tblDish',         28, 'Thêm món Cá hồi áp chảo vào thư viện hệ thống.',                           NOW()),
(1, 'process_feedback', 'tblUserFeedback', 3,  'Bắt đầu xử lý phản hồi lỗi đăng nhập của le_chau.',                       NOW()),
(1, 'resolve_feedback', 'tblUserFeedback', 7,  'Ghi nhận phản hồi tích cực từ tran_binh.',                                 NOW()),
(1, 'process_feedback', 'tblUserFeedback', 9,  'Đã tiếp nhận yêu cầu biểu đồ tháng, chuyển cho team phát triển.',         NOW()),
(1, 'update_dish',      'tblDish',         1,  'Cập nhật công thức dinh dưỡng Cơm gà xối mỡ theo chuẩn mới.',             NOW()),
(1, 'create_dish',      'tblDish',         50, 'Thêm món Gà rán kiểu Mỹ vào danh mục Món Âu.',                             NOW()),
(1, 'process_feedback', 'tblUserFeedback', 10, 'Đang điều tra vấn đề load chậm trang kế hoạch bữa ăn.',                   NOW()),
(1, 'resolve_feedback', 'tblUserFeedback', 12, 'Đã ghi nhận yêu cầu cải thiện tính năng lưu kế hoạch mẫu.',               NOW()),
(1, 'process_feedback', 'tblUserFeedback', 14, 'Phân công developer sửa bug trên mobile.',                                  NOW()),
(1, 'create_dish',      'tblDish',         51, 'Thêm Bánh bao nhân thịt vào danh mục Dim sum & Điểm tâm.',                 NOW()),
(1, 'create_dish',      'tblDish',         52, 'Thêm Há cảo tôm vào danh mục Dim sum & Điểm tâm.',                         NOW()),
(1, 'resolve_feedback', 'tblUserFeedback', 16, 'Ghi nhận phản hồi tích cực về tính năng so sánh dinh dưỡng.',             NOW()),
(1, 'process_feedback', 'tblUserFeedback', 19, 'Sprint tiếp theo sẽ thêm bộ lọc theo thời gian nấu.',                     NOW()),
(1, 'resolve_feedback', 'tblUserFeedback', 20, 'Đã ghi nhận, đang mở rộng database nguyên liệu thay thế.',                NOW()),
(1, 'update_dish',      'tblDish',         23, 'Cập nhật thông tin dinh dưỡng Sườn heo nướng BBQ.',                        NOW()),
(1, 'unlock_account',   'tblUserAccount',  6,  'Đã xác minh, mở khoá tài khoản hoang_em sau khi người dùng liên hệ.',     NOW()),
(1, 'create_category',  'tblDishCategory', 15, 'Thêm danh mục Dim sum & Điểm tâm theo yêu cầu mở rộng.',                  NOW());

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- THỐNG KÊ TỔNG QUAN
-- ============================================================
-- tblHealthProfile:       6 bản ghi
-- tblHealthGoal:          6 bản ghi
-- tblPasswordResetToken:  5 bản ghi
-- tblDishCategory:       15 danh mục
-- tblDish:               56 món ăn (52 system + 4 custom)
-- tblNutritionInfo:      56 bản ghi
-- tblIngredient:         ~80 nguyên liệu
-- tblDishRating:         25 đánh giá
-- tblFavoriteDish:       22 yêu thích
-- tblMealPlan:           20 kế hoạch
-- tblMeal:               39 bữa ăn
-- tblPortion:            ~70 khẩu phần
-- tblMealPlanTemplate:    6 mẫu kế hoạch
-- tblTemplateMeal:       22 bữa trong mẫu
-- tblTemplatePortion:    28 khẩu phần mẫu
-- tblAdjustmentSuggestion: 20 gợi ý
-- tblUserFeedback:       20 phản hồi
-- tblAdminAuditLog:      20 nhật ký
-- ============================================================
