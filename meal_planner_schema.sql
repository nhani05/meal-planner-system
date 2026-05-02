-- ============================================================
-- MEAL PLANNER - DATABASE SCHEMA
-- Học viện Công nghệ Bưu chính Viễn thông - Nhóm 04
-- ============================================================

-- Tắt kiểm tra FK tạm thời khi tạo bảng
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- MODULE 1: QUẢN LÝ TÀI KHOẢN
-- ============================================================

CREATE TABLE tblUserAccount (
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          ENUM('user', 'admin') NOT NULL DEFAULT 'user',
    status        ENUM('active', 'locked', 'deleted') NOT NULL DEFAULT 'active',
    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tblHealthProfile (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL UNIQUE,
    full_name  VARCHAR(100),
    age        TINYINT UNSIGNED,
    gender     ENUM('male', 'female', 'other'),
    height_cm  DECIMAL(5,2),
    weight_kg  DECIMAL(5,2),
    avatar_url VARCHAR(500),
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_hp_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

CREATE TABLE tblHealthGoal (
    id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id     BIGINT UNSIGNED NOT NULL,
    goal_type      ENUM('weight_loss', 'muscle_gain', 'maintain') NOT NULL,
    activity_level ENUM('low', 'medium', 'high') NOT NULL DEFAULT 'medium',
    target_weight_kg    DECIMAL(5,2),
    daily_calories_kcal INT UNSIGNED,
    protein_g_day       DECIMAL(6,2),
    carb_g_day          DECIMAL(6,2),
    fat_g_day           DECIMAL(6,2),
    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_hg_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

-- OTP để lấy lại mật khẩu
CREATE TABLE tblPasswordResetToken (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    token      VARCHAR(64) NOT NULL UNIQUE,
    expires_at DATETIME NOT NULL,
    used       TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prt_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

-- ============================================================
-- MODULE 3: QUẢN LÝ MÓN ĂN
-- ============================================================

CREATE TABLE tblDishCategory (
    id         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tblDish (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    category_id INT UNSIGNED,
    image_url   VARCHAR(500),
    source      ENUM('system', 'custom') NOT NULL DEFAULT 'system',
    account_id  BIGINT UNSIGNED NULL COMMENT 'NULL nếu là món hệ thống',
    difficulty  ENUM('easy', 'medium', 'hard'),
    total_time_min INT UNSIGNED COMMENT 'Tổng thời gian chế biến (phút)',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_dish_category FOREIGN KEY (category_id)
        REFERENCES tblDishCategory(id) ON DELETE SET NULL,
    CONSTRAINT fk_dish_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

CREATE TABLE tblNutritionInfo (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dish_id         BIGINT UNSIGNED NOT NULL UNIQUE,
    calories_per_100g DECIMAL(7,2) NOT NULL DEFAULT 0,
    protein_per_100g  DECIMAL(6,2) NOT NULL DEFAULT 0,
    carb_per_100g     DECIMAL(6,2) NOT NULL DEFAULT 0,
    fat_per_100g      DECIMAL(6,2) NOT NULL DEFAULT 0,
    fiber_per_100g    DECIMAL(6,2),
    sat_fat_per_100g  DECIMAL(6,2),
    vitamin_a_mcg   DECIMAL(8,2),
    vitamin_c_mg    DECIMAL(8,2),
    vitamin_d_mcg   DECIMAL(8,2),
    calcium_mg      DECIMAL(8,2),
    iron_mg         DECIMAL(8,2),
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_ni_dish FOREIGN KEY (dish_id)
        REFERENCES tblDish(id) ON DELETE CASCADE
);

CREATE TABLE tblIngredient (
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dish_id          BIGINT UNSIGNED NOT NULL,
    name             VARCHAR(200) NOT NULL,
    quantity_g       DECIMAL(8,2) NOT NULL COMMENT 'Khối lượng trong công thức gốc (gram)',
    unit             VARCHAR(30) DEFAULT 'g',
    CONSTRAINT fk_ing_dish FOREIGN KEY (dish_id)
        REFERENCES tblDish(id) ON DELETE CASCADE
);

CREATE TABLE tblDishRating (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    dish_id    BIGINT UNSIGNED NOT NULL,
    score      TINYINT UNSIGNED NOT NULL COMMENT '1–5',
    comment    TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_rating (account_id, dish_id),
    CONSTRAINT fk_dr_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE,
    CONSTRAINT fk_dr_dish FOREIGN KEY (dish_id)
        REFERENCES tblDish(id) ON DELETE CASCADE
);

CREATE TABLE tblFavoriteDish (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    dish_id    BIGINT UNSIGNED NOT NULL,
    saved_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_fav (account_id, dish_id),
    CONSTRAINT fk_fd_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE,
    CONSTRAINT fk_fd_dish FOREIGN KEY (dish_id)
        REFERENCES tblDish(id) ON DELETE CASCADE
);

-- ============================================================
-- MODULE 2: QUẢN LÝ KẾ HOẠCH BỮA ĂN
-- ============================================================

CREATE TABLE tblMealPlan (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    plan_name  VARCHAR(200),
    plan_date  DATE NOT NULL COMMENT 'Ngày áp dụng kế hoạch',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_mp_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

CREATE TABLE tblMeal (
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    meal_plan_id BIGINT UNSIGNED NOT NULL,
    meal_type    ENUM('breakfast', 'lunch', 'dinner', 'snack') NOT NULL,
    -- Tổng dinh dưỡng tính động từ portions, lưu cache để hiển thị nhanh
    total_calories_kcal DECIMAL(8,2) GENERATED ALWAYS AS (NULL) VIRTUAL,
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_meal_plan FOREIGN KEY (meal_plan_id)
        REFERENCES tblMealPlan(id) ON DELETE CASCADE,
    UNIQUE KEY uq_meal_slot (meal_plan_id, meal_type)
);

-- Bỏ cột GENERATED (không phải tất cả MySQL version hỗ trợ với FK) 
-- Dùng bảng portions để tính động
ALTER TABLE tblMeal DROP COLUMN total_calories_kcal;

CREATE TABLE tblPortion (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    meal_id         BIGINT UNSIGNED NOT NULL,
    dish_id         BIGINT UNSIGNED NOT NULL,
    quantity_g      DECIMAL(8,2) NOT NULL COMMENT 'Khẩu phần thực tế (gram)',
    -- Giá trị tính tự động (lưu để tránh tính lại liên tục)
    calories_kcal   DECIMAL(8,2),
    protein_g       DECIMAL(7,2),
    carb_g          DECIMAL(7,2),
    fat_g           DECIMAL(7,2),
    CONSTRAINT fk_portion_meal FOREIGN KEY (meal_id)
        REFERENCES tblMeal(id) ON DELETE CASCADE,
    CONSTRAINT fk_portion_dish FOREIGN KEY (dish_id)
        REFERENCES tblDish(id) ON DELETE RESTRICT
);

-- Kế hoạch mẫu để tái sử dụng
CREATE TABLE tblMealPlanTemplate (
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id   BIGINT UNSIGNED NOT NULL,
    template_name VARCHAR(200) NOT NULL,
    saved_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tpl_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

-- Cấu trúc bữa ăn trong mẫu
CREATE TABLE tblTemplateMeal (
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    template_id  BIGINT UNSIGNED NOT NULL,
    meal_type    ENUM('breakfast', 'lunch', 'dinner', 'snack') NOT NULL,
    CONSTRAINT fk_tm_template FOREIGN KEY (template_id)
        REFERENCES tblMealPlanTemplate(id) ON DELETE CASCADE
);

CREATE TABLE tblTemplatePortion (
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    template_meal_id BIGINT UNSIGNED NOT NULL,
    dish_id          BIGINT UNSIGNED NOT NULL,
    quantity_g       DECIMAL(8,2) NOT NULL,
    CONSTRAINT fk_tp_template_meal FOREIGN KEY (template_meal_id)
        REFERENCES tblTemplateMeal(id) ON DELETE CASCADE,
    CONSTRAINT fk_tp_dish FOREIGN KEY (dish_id)
        REFERENCES tblDish(id) ON DELETE RESTRICT
);

-- Gợi ý điều chỉnh thực đơn
CREATE TABLE tblAdjustmentSuggestion (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id      BIGINT UNSIGNED NOT NULL,
    meal_plan_id    BIGINT UNSIGNED,
    suggestion_type ENUM('add_dish', 'reduce_portion', 'swap_dish') NOT NULL,
    content         TEXT NOT NULL,
    status          ENUM('pending', 'applied', 'dismissed') NOT NULL DEFAULT 'pending',
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_as_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE,
    CONSTRAINT fk_as_plan FOREIGN KEY (meal_plan_id)
        REFERENCES tblMealPlan(id) ON DELETE SET NULL
);

-- ============================================================
-- MODULE 4: QUẢN TRỊ HỆ THỐNG (ADMIN)
-- ============================================================

CREATE TABLE tblUserFeedback (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT UNSIGNED NOT NULL,
    content    TEXT NOT NULL,
    status     ENUM('pending', 'processing', 'resolved') NOT NULL DEFAULT 'pending',
    admin_note TEXT,
    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_uf_account FOREIGN KEY (account_id)
        REFERENCES tblUserAccount(id) ON DELETE CASCADE
);

CREATE TABLE tblAdminAuditLog (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    admin_id    BIGINT UNSIGNED NOT NULL,
    action      VARCHAR(100) NOT NULL COMMENT 'Ví dụ: lock_account, delete_dish, resolve_feedback',
    target_type VARCHAR(50)  NOT NULL COMMENT 'Loại đối tượng bị tác động: tblUserAccount, tblDish, tblUserFeedback',
    target_id   BIGINT UNSIGNED NOT NULL,
    note        TEXT,
    acted_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_aal_admin FOREIGN KEY (admin_id)
        REFERENCES tblUserAccount(id) ON DELETE RESTRICT
);

-- ============================================================
-- INDEX HỖ TRỢ TRUY VẤN THƯỜNG GẶP
-- ============================================================

CREATE INDEX idx_meal_plans_account_date ON tblMealPlan (account_id, plan_date);
CREATE INDEX idx_meals_plan             ON tblMeal (meal_plan_id);
CREATE INDEX idx_portions_meal          ON tblPortion (meal_id);
CREATE INDEX idx_dishes_category        ON tblDish (category_id);
CREATE INDEX idx_dishes_source_account  ON tblDish (source, account_id);
CREATE INDEX idx_feedbacks_status       ON tblUserFeedback (status);
CREATE INDEX idx_audit_admin            ON tblAdminAuditLog (admin_id, acted_at);
CREATE INDEX idx_suggestions_account    ON tblAdjustmentSuggestion (account_id, status);

SET FOREIGN_KEY_CHECKS = 1;
