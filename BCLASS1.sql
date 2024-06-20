
    SELECT
          TO_CHAR(CREATE_DATE, 'YYYY-MM-DD')
      FROM
          BOARD;
          
    -- INLINE-VIEW --     
      SELECT
            BOARD_NO,
            BOARD_TITLE,
            BOARD_WRITER,
            COUNT,
            CREATE_DATE,
            ORIGIN_NAME
        FROM
            (
             SELECT
                    BOARD_NO,
                    BOARD_TITLE,
                    BOARD_WRITER,
                    COUNT,
                    CREATE_DATE,
                    ORIGIN_NAME,
                    ROWNUM RNUM
                FROM
                    (
                     SELECT
                          BOARD_NO,
                          BOARD_TITLE,
                          BOARD_WRITER,
                          COUNT,
                          CREATE_DATE,
                          ORIGIN_NAME
                      FROM
                          BOARD
                     WHERE
                          STATUS = 'Y'
                    ORDER
                       BY
                         BOARD_NO DESC))
      WHERE
            RNUM BETWEEN 1 AND 10;