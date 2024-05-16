USE utopia;

DROP FUNCTION IF EXISTS get_medias_as_array;

DELIMITER $$
CREATE FUNCTION get_medias_as_array(product_id BIGINT UNSIGNED)
RETURNS JSON
READS SQL DATA
BEGIN
	DECLARE media_names JSON;
    
	SELECT JSON_ARRAYAGG((JSON_OBJECT("id", id, "name", name, "is_main", is_main))) INTO media_names
	FROM medias m
	WHERE m.product_id = product_id
    GROUP BY m.product_id;
    
    RETURN media_names;
END $$
DELIMITER ;
