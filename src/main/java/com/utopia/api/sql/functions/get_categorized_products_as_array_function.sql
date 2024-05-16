USE utopia;

DROP FUNCTION IF EXISTS get_categorized_products_as_array;

DELIMITER $$
CREATE FUNCTION get_categorized_products_as_array(product_id BIGINT UNSIGNED)
RETURNS JSON
READS SQL DATA
BEGIN
	DECLARE product_categories JSON;
    
	SELECT JSON_ARRAYAGG(category_id) INTO product_categories
	FROM categorized_products cp
	WHERE cp.product_id = product_id;
    
    RETURN product_categories;
END $$
DELIMITER ;
