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
WHERE cp.category_id = 1;