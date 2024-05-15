USE utopia;

CREATE VIEW products_view AS
SELECT 
	p.id,
    p.title,
    p.original_price,
    p.sales_price,
    p.description,
    p.date,
    m.name as "media"
FROM products p
JOIN medias m
	ON p.id = m.product_id AND m.is_main = 1
ORDER BY p.id;
