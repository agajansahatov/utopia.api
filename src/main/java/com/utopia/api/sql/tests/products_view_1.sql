SELECT
	p.id,
    p.title,
    c.name
FROM products p
JOIN product_categories pc
	ON p.id = pc.product_id
JOIN categories c
	ON c.id = pc.category_id
ORDER BY p.id;