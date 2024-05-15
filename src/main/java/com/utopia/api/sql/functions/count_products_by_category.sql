USE utopia;

DROP FUNCTION IF EXISTS count_products_by_category;

DELIMITER $$
CREATE FUNCTION count_products_by_category (category_id TINYINT UNSIGNED)
RETURNS INTEGER
READS SQL DATA
BEGIN
	DECLARE count INTEGER DEFAULT 0;
    
	IF category_id IS NULL OR NOT EXISTS (SELECT 1 FROM categories WHERE id = category_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid category_id';
    END IF;
	
	SELECT COUNT(*) INTO count
	FROM categorized_products cp
	JOIN products_view p
		ON cp.product_id = p.id
	WHERE cp.category_id = 1;
    
    RETURN count;
END $$
DELIMITER ;
