USE utopia;

DROP VIEW IF EXISTS products_view;

CREATE VIEW products_view AS
SELECT 
	p.id,
    p.title,
    p.original_price,
    p.sales_price,
    p.number_in_stock,
    CONCAT(LEFT(p.description, 500), ' ...') AS description,
    p.date,
    m.name as "main_media"
FROM products p
JOIN medias m
	ON p.id = m.product_id AND m.is_main = 1
ORDER BY p.id;
