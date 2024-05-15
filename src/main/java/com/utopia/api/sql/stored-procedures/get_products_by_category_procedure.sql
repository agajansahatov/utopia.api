USE utopia;

DROP PROCEDURE IF EXISTS get_products_by_category;

DELIMITER $$
CREATE PROCEDURE get_products_by_category(category_id TINYINT UNSIGNED)
BEGIN
    IF category_id IS NULL OR NOT EXISTS (SELECT 1 FROM categories WHERE id = category_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid category_id';
    END IF;
	
	SELECT 
		p.id,
		p.title,
		p.original_price,
		p.sales_price,
		p.description,
		p.date,
		p.media
	FROM categorized_products cp
	JOIN products_view p
		ON cp.product_id = p.id
	WHERE cp.category_id = category_id;
END $$
DELIMITER ;
