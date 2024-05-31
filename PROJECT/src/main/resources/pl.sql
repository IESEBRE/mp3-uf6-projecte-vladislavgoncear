CREATE OR REPLACE PROCEDURE crear_taula_sneakers AUTHID CURRENT_USER AS
    v_table_count NUMBER;
BEGIN
    -- Verificar si la tabla ya existe
    SELECT COUNT(*)
    INTO v_table_count
    FROM user_tables
    WHERE table_name = 'SNEAKERS';

    -- Si la tabla no existe, crearla
    IF v_table_count = 0 THEN
        -- Crear la tabla utilizando SQL dinámico
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
END crear_taula_sneakers;


