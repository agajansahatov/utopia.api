USE utopia;

DROP FUNCTION IF EXISTS get_medias_as_array;

DELIMITER $$
CREATE FUNCTION get_medias_as_array(product_id BIGINT UNSIGNED)
RETURNS JSON
READS SQL DATA
BEGIN
	DECLARE media_list JSON;
    
	SELECT 
		JSON_ARRAYAGG(
			(JSON_OBJECT(
				"id", id, 
                "name", name, 
                "is_main", is_main)
			)
		) INTO media_list
	FROM medias m
	WHERE m.product_id = product_id;
    
    RETURN media_list;
END $$
DELIMITER ;
