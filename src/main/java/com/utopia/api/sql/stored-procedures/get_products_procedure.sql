USE utopia;

DROP PROCEDURE IF EXISTS get_products;

DELIMITER $$
CREATE PROCEDURE get_products(
    IN page BIGINT UNSIGNED,
    IN amount BIGINT UNSIGNED,
    IN category_id TINYINT UNSIGNED
)
BEGIN
    DECLARE size BIGINT UNSIGNED;
    DECLARE start_index BIGINT UNSIGNED;

    -- Get total number of products and pass it to size variable accordingly
    IF category_id IS NOT NULL THEN
        -- Count products in the specified category
        SELECT count_products_by_category(category_id) INTO size;
    ELSE
        -- Count all products
        SELECT COUNT(*) INTO size FROM products;
    END IF;

    -- Validate and set default values for page and amount
    IF page IS NULL THEN
        SET page = 1;
    END IF;
    
    IF amount IS NULL THEN
        SET amount = size;
    END IF;

    -- Handle cases where page or amount is zero
    IF page = 0 OR amount = 0 THEN
        SET amount = 0;
        SET page = 1;
    END IF;

    -- Calculate the starting index for the pagination
    SET start_index = (page - 1) * amount;

    -- Check if the start index exceeds the size of the products
    IF start_index >= size AND size != 0 THEN
        SIGNAL SQLSTATE '22003' SET MESSAGE_TEXT = 'Invalid page or amount';
    END IF;

    -- Fetch the products based on category_id with pagination
    IF category_id IS NOT NULL THEN
        SELECT p.*
        FROM categorized_products cp
        JOIN products_view p
            ON cp.product_id = p.id
        WHERE cp.category_id = category_id
        LIMIT start_index, amount;
    ELSE
        -- Fetch all products with pagination
        SELECT * 
        FROM products_view 
        LIMIT start_index, amount;
    END IF;
END $$
DELIMITER ;
