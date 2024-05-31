CREATE OR REPLACE PROCEDURE crear_taula_sneakers AUTHID CURRENT_USER AS
    
BEGIN
DECLARE
v_table_count NUMBER;
BEGIN
    -- Verificar si la taula ja existeix
    SELECT COUNT(*)
    INTO v_table_count
    FROM user_tables
    WHERE table_name = 'SNEAKERS';

    -- Si la taula no existeix, crear-la
    IF v_table_count = 0 THEN
        -- Crear la taula utilitzant SQL dinàmic
        EXECUTE IMMEDIATE '
            CREATE TABLE SNEAKERS (
            prod_id VARCHAR2(50) NOT NULL, 
            marca VARCHAR2(50), 
            model VARCHAR2(50), 
            talla NUMBER, 
            preu_actual NUMBER, 
            preu_sortida NUMBER,
            data_sortida VARCHAR2(50),
            disponible NUMBER,
            PRIMARY KEY (prod_id)
        )';
    END IF;
    END;
END crear_taula_sneakers;
/

