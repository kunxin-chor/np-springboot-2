USE ecommerce;

-- Create a default category
INSERT INTO categories (name) VALUES ('Uncategorized');



-- Update all existing products to use this category
UPDATE products SET category_id = 1 WHERE category_id IS NULL;