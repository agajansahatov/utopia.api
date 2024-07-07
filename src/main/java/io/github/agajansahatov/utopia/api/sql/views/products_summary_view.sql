USE utopia;

DROP VIEW IF EXISTS products_summary_view;

CREATE VIEW products_summary_view AS
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
LEFT JOIN medias m ON p.id = m.product_id AND m.id = (
		SELECT MIN(m1.id)
		FROM medias m1
		WHERE m1.product_id = p.id AND m1.is_main = 1
		GROUP BY m1.product_id
	)
ORDER BY p.id;
