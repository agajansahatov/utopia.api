SELECT 
    p.*,
    JSON_ARRAYAGG(JSON_OBJECT("id", m.id, "name", m.name, "is_main", m.is_main)) AS medias,
    JSON_ARRAYAGG(JSON_OBJECT("id", c.id, "name", c.name))  AS categories,
    IFNULL((SELECT COUNT(f.product_id)), 0) AS likes_count,
    IFNULL((SELECT COUNT(t.product_id)), 0) AS visits_count,
    IFNULL((SELECT COUNT(o.product_id)), 0) AS orders_count,
    IFNULL((SELECT COUNT(com.product_id)), 0) AS comments_count
FROM 
    products p
LEFT JOIN 
    medias m ON p.id = m.product_id
LEFT JOIN 
    categorized_products cp ON p.id = cp.product_id
LEFT JOIN 
    categories c ON cp.category_id = c.id
LEFT JOIN 
    favourites f ON p.id = f.product_id
LEFT JOIN 
    traces t ON p.id = t.product_id
LEFT JOIN
	orders o ON p.id = o.product_id
LEFT JOIN
	comments com ON p.id = com.product_id
GROUP BY id;