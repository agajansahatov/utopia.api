USE utopia;

DROP FUNCTION IF EXISTS get_categorized_products_as_array;

DELIMITER $$
CREATE FUNCTION get_categorized_products_as_array(product_id BIGINT UNSIGNED)
RETURNS JSON
READS SQL DATA
BEGIN
	DECLARE product_categories JSON;
    
	SELECT 
		JSON_ARRAYAGG(
			(JSON_OBJECT(
				"id", cp.category_id, 
				"name", c.name
			))
		) INTO product_categories
	FROM categorized_products cp
    JOIN categories c
		ON c.id = cp.category_id
	WHERE cp.product_id = product_id;
    
    RETURN product_categories;
END $$
DELIMITER ;
