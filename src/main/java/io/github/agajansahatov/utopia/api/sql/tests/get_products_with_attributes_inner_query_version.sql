SELECT
	*,
	get_medias_as_array(id) AS medias,
    get_categorized_products_as_array(id) AS categories,
    (SELECT COUNT(*) FROM favourites WHERE product_id = p.id) AS likes_count,
    (SELECT COUNT(*) FROM traces WHERE product_id = p.id) AS visits_count,
    (SELECT COUNT(*) FROM orders WHERE product_id = p.id) AS orders_count,
    (SELECT COUNT(*) FROM comments WHERE product_id = p.id) AS comments_count
FROM products p;