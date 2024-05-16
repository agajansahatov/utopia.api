USE utopia;

DROP PROCEDURE IF EXISTS get_product;

DELIMITER $$
CREATE PROCEDURE get_product(id BIGINT UNSIGNED)
BEGIN
	SELECT
		*,
		(SELECT COUNT(*) FROM favourites WHERE product_id = p.id) AS likes_count,
		(SELECT COUNT(*) FROM traces WHERE product_id = p.id) AS visits_count,
		(SELECT COUNT(*) FROM orders WHERE product_id = p.id) AS orders_count,
		(SELECT COUNT(*) FROM comments WHERE product_id = p.id) AS comments_count,
		get_medias_as_array(p.id) AS medias,
		get_categorized_products_as_array(p.id) AS category_ids
	FROM products p
    WHERE p.id = id;
END $$
DELIMITER ;