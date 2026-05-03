-- Mock data for local testing (columns and enum values match schema)

INSERT INTO tblUserAccount (id, username, email, password_hash, role, status, created_at, updated_at)
VALUES (1, 'tester1', 'tester1@example.com', 'mocked-hash', 'user', 'active', NOW(), NOW());

INSERT INTO tblHealthProfile (id, account_id, full_name, age, gender, height_cm, weight_kg, avatar_url, updated_at)
VALUES (1, 1, 'Nguyen Van A', 30, 'male', 175.00, 70.00, 'https://example.com/avatar.jpg', NOW());

-- Dish categories
INSERT INTO tblDishCategory (id, name, created_at)
VALUES (1, 'Main Course', NOW());

-- Dishes (note: `source`, `difficulty` and enum values are lowercase)
INSERT INTO tblDish (id, name, category_id, image_url, source, account_id, difficulty, total_time_min, created_at, updated_at)
VALUES (1, 'Grilled Chicken', 1, 'https://example.com/chicken.jpg', 'system', 1, 'easy', 45, NOW(), NOW()),
	   (2, 'Pasta Primavera', 1, 'https://example.com/pasta.jpg', 'system', NULL, 'medium', 30, NOW(), NOW());

-- Nutrition info for dishes
INSERT INTO tblNutritionInfo (id, dish_id, calories_per_100g, protein_per_100g, carb_per_100g, fat_per_100g, updated_at)
VALUES (1, 1, 250.00, 27.00, 0.00, 8.00, NOW()),
	   (2, 2, 180.00, 6.00, 30.00, 5.00, NOW());

-- Ingredients
INSERT INTO tblIngredient (id, dish_id, name, quantity_g, unit)
VALUES (1, 1, 'Chicken breast', 200.00, 'g'),
	   (2, 1, 'Olive oil', 10.00, 'g'),
	   (3, 2, 'Pasta', 150.00, 'g');

-- Meal plan and meal
INSERT INTO tblMealPlan (id, account_id, plan_name, plan_date, created_at, updated_at)
VALUES (1, 1, 'Lunch Plan', '2026-05-03', NOW(), NOW());

INSERT INTO tblMeal (id, meal_plan_id, meal_type, created_at)
VALUES (1, 1, 'lunch', NOW());

-- Portion linking meal and dish
INSERT INTO tblPortion (id, meal_id, dish_id, quantity_g, calories_kcal, protein_g, carb_g, fat_g)
VALUES (1, 1, 1, 200.00, 500.00, 54.00, 0.00, 16.00);
