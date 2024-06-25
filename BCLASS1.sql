
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
            
            
    -- 조건에 부합하는 행의 개수 알아내기
    
    SELECT
           COUNT(BOARD_NO)
      FROM
           BOARD
    WHERE 
          STATUS = 'Y'
      AND
          BOARD_WRITER LIKE '%' || 'u' || '%'
      AND
          BOARD_TITLE LIKE '%' || 'Java' || '%'
      AND
          BOARD_CONTENT LIKE '%' || 'u' || '%'
      
      
      -- 사진 게시판 목록 조회
      
      SELECT
            CHANGE_NAME,
            BOARD_TITLE,
            USER_NAME,
            BOARD_CONTENT,
            CREATE_DATE
       FROM
            BOARD, MEMBER
      WHERE
            BOARD.BOARD_WRITER = MEMBER.USER_ID
        AND
            CHANGE_NAME IS NOT NULL
      ORDER
         BY
            BOARD_NO DESC;
            
            
delete from board where BOARD_TITLE = 'jjjjjjj';