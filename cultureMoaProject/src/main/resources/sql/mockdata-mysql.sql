-- 1. USER_TBL (샘플 유저 추가)
INSERT INTO USER_TBL (ID, NAME, EMAIL, PASSWORD, ADDRESS, NICKNAME, SOCIAL_LOGIN, SDATE, CDATE)
SELECT * FROM (
    SELECT 'ryugeonu', '류건우', 'ryu@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 강남구(123)', '건우짱', 'kakao', '2023-01-01', '2023-01-11' UNION ALL
    SELECT 'coegyeongsu', '고경수', 'ko@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 서초구(456)', '경수맨', 'google', '2023-01-02', '2023-01-21' UNION ALL
    SELECT 'areum92', '김아름', 'areum@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 마포구(789)', '아름이', 'naver', '2023-01-03', '2023-01-15' UNION ALL
    SELECT 'jiu52', '지유', 'jiu@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 송파구(789)', '지유짱', 'regularLogin', '2023-01-04', '2023-01-16' UNION ALL
    SELECT 'sangho11', '상호', 'sangho@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 종로구(147)', '상호짱', 'regularLogin', '2023-01-05', '2023-01-18' UNION ALL
    SELECT 'yeji86', '예지', 'yeji@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 동대문구(258)', '예지짱', 'naver', '2023-01-06', '2023-01-23'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_TBL);

-- 2. PRODUCT_TBL
INSERT INTO PRODUCT_TBL (CATEGORY_IDX, CATEGORYGENRE_IDX, TITLE, SDATE, EDATE, USER_IDX, PRICE, FLAG, CONTENT)
SELECT * FROM (
    SELECT 1001, 2001, 'Occaecati placeat.', '2024-06-29', NULL, 1, 37435, 0 , 'ABCDEFG' UNION ALL
    SELECT 1002, 2002, 'Assumenda maiores.', '2024-06-24', NULL, 2, 89247, 1 , 'ABCDEFG' UNION ALL
    SELECT 1003, 2003, 'Officia recusandae excepturi.', '2024-06-01', NULL, 3, 32262, 1 , 'ABCDEFG' UNION ALL
    SELECT 1002, 2002, 'Enim quas perferendis.', '2024-09-12', NULL, 4, 48116, 0 , 'ABCDEFG' UNION ALL
    SELECT 1006, 2004, 'Quas tempora dignissimos.', '2024-06-21', NULL, 5, 14074, 1, 'ABCDEFG'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PRODUCT_TBL);

-- 3. PRODUCT_IMAGE_TBL
INSERT INTO PRODUCT_IMAGE_TBL (PRODUCT_IDX, IMAGE_URL, FLAG)
SELECT * FROM (
    SELECT 1 AS PRODUCT_IDX,
    'https://placeimg.com/987/384/any' AS IMAGE_URL,
    1 AS FLAG UNION ALL
    SELECT 2, 'https://placekitten.com/606/750' , 0 UNION ALL
    SELECT 3, 'https://placeimg.com/850/673/any', 1  UNION ALL
    SELECT 4, 'https://placekitten.com/591/927', 1  UNION ALL
    SELECT 5, 'https://placeimg.com/340/793/any', 0
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PRODUCT_IMAGE_TBL);

-- 4. USER_TRANSACTION_TBL
INSERT INTO USER_TRANSACTION_TBL (USER_IDX, PRODUCT_IDX, SDATE)
SELECT * FROM (
    SELECT 1 as USER_IDX , 1 as PRODUCT_IDX , '2025-05-19' as SDATE UNION ALL
    SELECT 2, 2, '2025-05-19' UNION ALL
    SELECT 3, 3, '2025-05-19' UNION ALL
    SELECT 4, 4, '2025-05-19' UNION ALL
    SELECT 5, 5, '2025-05-19'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_TRANSACTION_TBL);

-- 5. USER_REVIEW_TBL
INSERT INTO USER_REVIEW_TBL (WRITER_USER_IDX, RECEIVER_USER_IDX, TRANSACTION_IDX, RATING, REVIEW, SDATE)
SELECT * FROM (
    SELECT 1 as WRITER_USER_IDX, 2 as RECEIVER_USER_IDX, 1 as TRANSACTION_IDX, 2.9 as RATING, 'Harum quibusdam sapiente dignissimos ea.' as REVIEW, '2025-05-19' as SDATE UNION ALL
    SELECT 2, 3, 2, 2.5, 'Pariatur itaque tempore illum rem quia facere.', '2025-05-19' UNION ALL
    SELECT 3, 4, 3, 3.7, 'Id quibusdam quibusdam praesentium.', '2025-05-19' UNION ALL
    SELECT 4, 5, 4, 2.8, 'Animi amet delectus dolorem libero aperiam.', '2025-05-19' UNION ALL
    SELECT 5, 1, 5, 2.1, 'Eaque velit iste vitae iusto.', '2025-05-19'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_REVIEW_TBL);

-- 6. USER_REVIEW_EVALUATION_TBL
INSERT INTO USER_REVIEW_EVALUATION_TBL (USER_IDX, EVAL_5001, EVAL_5003, EVAL_5005, EVAL_5008)
SELECT * FROM (
    SELECT 1 as USER_IDX, 0 as EVAL_5001, 0 as EVAL_5003, 0 as EVAL_5005, 1 as EVAL_5008 UNION ALL
    SELECT 2, 0, 0, 1, 0 UNION ALL
    SELECT 3, 0, 1, 0, 0 UNION ALL
    SELECT 4, 1, 0, 0, 0 UNION ALL
    SELECT 5, 1, 0, 0, 0
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_REVIEW_EVALUATION_TBL);

-- 7. USER_FAVORITE_TBL
INSERT INTO USER_FAVORITE_TBL (USER_IDX, FAV_1001)
VALUES (1, 1) AS new
ON DUPLICATE KEY UPDATE FAV_1001 = new.FAV_1001;

INSERT INTO USER_FAVORITE_TBL (USER_IDX, FAV_2003)
VALUES (3, 1) AS new
ON DUPLICATE KEY UPDATE FAV_2003 = new.FAV_2003;

INSERT INTO USER_FAVORITE_TBL (USER_IDX, FAV_2004)
VALUES (4, 1) AS new
ON DUPLICATE KEY UPDATE FAV_2004 = new.FAV_2004;

INSERT INTO USER_FAVORITE_TBL (USER_IDX, FAV_3005)
VALUES (5, 1) AS new
ON DUPLICATE KEY UPDATE FAV_3005 = new.FAV_3005;


-- 8. USER_PICK_TBL
INSERT INTO USER_PICK_TBL (USER_IDX, PRODUCT_IDX, SDATE)
SELECT * FROM (
    SELECT 1 as USER_IDX, 1 as PRODUCT_IDX, '2025-05-20' as SDATE UNION ALL
    SELECT 2, 2, '2025-05-20' UNION ALL
    SELECT 3, 3, '2025-05-20' UNION ALL
    SELECT 4, 4, '2025-05-20' UNION ALL
    SELECT 5, 5, '2025-05-20'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_PICK_TBL);

-- 9. CONTENTS_TBL
INSERT INTO CONTENTS_TBL (USER_IDX, CATEGORY_IDX, TITLE, CONTENT, SDATE, EDATE)
SELECT * FROM (
    SELECT 1, 4003, 'Itaque quam.', 'Minus nihil eligendi molestiae quidem maiores.', '2025-05-20', NULL UNION ALL
    SELECT 2, 4002, 'Iure voluptates deserunt.', 'Optio debitis neque laboriosam quibusdam.', '2025-05-20', NULL UNION ALL
    SELECT 3, 4003, 'Sequi veritatis.', 'Quidem quis maxime. Pariatur cum delectus.', '2025-05-20', NULL UNION ALL
    SELECT 4, 4002, 'Ad fugiat consequatur.', 'Molestiae vitae soluta dolorum vero velit.', '2025-05-20', NULL UNION ALL
    SELECT 5, 4003, 'Tempore necessitatibus nisi.', 'Velit fugiat similique dolores temporibus.', '2025-05-20', NULL
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM CONTENTS_TBL);

-- 10. CONTENTS_COMMENT_TBL
INSERT INTO CONTENTS_COMMENT_TBL (USER_IDX, CONTENTS_IDX, TEXT, SDATE)
SELECT * FROM (
    SELECT 1 AS USER_IDX, 1 AS CONTENTS_IDX, 'Aliquam earum eos sequi.' AS TEXT, '2025-05-20' AS SDATE UNION ALL
    SELECT 2, 2, 'Id voluptatum sequi unde.', '2025-05-20' UNION ALL
    SELECT 3, 3, 'Nostrum reiciendis exercitationem libero.', '2025-05-20' UNION ALL
    SELECT 4, 4, 'Sed dolor at aperiam deleniti neque placeat.', '2025-05-20' UNION ALL
    SELECT 5, 5, 'Doloribus debitis error mollitia itaque nobis dolor sit.', '2025-05-20'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM CONTENTS_COMMENT_TBL);

-- 11. CONTENTS_DETAIL_IMAGE_TBL mock data
INSERT INTO CONTENTS_DETAIL_IMAGE_TBL (CONTENTS_IDX, IMAGE_URL)
SELECT * FROM (
    SELECT 1, 'https://placeimg.com/640/480/nature' UNION ALL
    SELECT 2, 'https://placekitten.com/800/600' UNION ALL
    SELECT 3, 'https://placeimg.com/720/480/tech' UNION ALL
    SELECT 4, 'https://placebear.com/640/360' UNION ALL
    SELECT 5, 'https://picsum.photos/640/480'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM CONTENTS_DETAIL_IMAGE_TBL);

-- 12. TICKET_TBL
INSERT INTO TICKET_TBL (SUB_IDX, TITLE, COMPANY, LINK, SDATE, EDATE, PLACE, PRICE, GRADE, CAST, RUNNINGTIME, IMG, ETC)
SELECT * FROM (
    SELECT 3001, '가상 글로벌 펌웨어', '(주) 김', 'https://janggimseo.kr/', '2025-05-20', '2025-06-04', '안양시 만안구', '108483', 'A', '김은정', '117분', 'https://www.lorempixel.com/492/839', 'Assumenda ut est vitae.' UNION ALL
    SELECT 3002, '확장된 클라이언트 단 생산 능력', '(유) 이', 'http://www.jusighoesa.com/', '2025-05-20', '2025-06-16', '부여군', '57142', 'A', '이순자', '94분', 'https://placeimg.com/603/795/any', 'Hic enim aspernatur ab facere ullam.' UNION ALL
    SELECT 3001, '1:1 수요 중심 LAN', '유한회사 백', 'http://www.songgang.kr/', '2025-05-20', '2025-06-09', '청주시 상당구', '23583', 'S', '문수진', '101분', 'https://placekitten.com/829/344', 'Unde impedit mollitia.' UNION ALL
    SELECT 3003, '강력한 국가적 공구', '최김', 'https://www.yu.kr/', '2025-05-20', '2025-05-27', '군포시', '123860', 'A', '김영길', '130분', 'https://www.lorempixel.com/244/994', 'Officia dolore perferendis quisquam.' UNION ALL
    SELECT 3004, '변경 가능한 하이브리드 유연성', '김이이', 'http://www.ju.kr/', '2025-05-20', '2025-05-27', '계룡시', '75119', 'VIP', '김상호', '98분', 'https://www.lorempixel.com/579/122', 'Quo facere totam aliquid ea ipsa.'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM TICKET_TBL);

-- 13. PAYMENT_HISTORY_TBL
INSERT INTO PAYMENT_HISTORY_TBL (TID, AMOUNT, PAY_METHOD, BUYER_IDX, SELLER_IDX, PRODUCT_IDX, DELIVERY_ADDRESS)
SELECT * FROM (
    SELECT
        'a57d6b00-1304-48d3-a5c8-e47824fcb26b' AS TID,
        78085 AS AMOUNT,
        6001 AS PAY_METHOD,
        1 AS BUYER_IDX,
        3 AS SELLER_IDX,
        1 AS PRODUCT_IDX,
        '전라남도 논산시 반포대169가 (옥순양윤동)' AS DELIVERY_ADDRESS UNION ALL
    SELECT 'adbd95cc-26c0-4abd-9f0c-c7e00e026d7e', 128653, 6001, 4, 6, 2, '전라북도 수원시 팔달구 선릉거리' UNION ALL
    SELECT 'dd6bed9d-ba45-4d3b-a844-606e3cfdfe2b', 79594, 6001, 4, 3, 3, '경상남도 보령시 가락408로' UNION ALL
    SELECT 'f5591955-ce0b-4584-ac08-7488db9c6b31', 145302, 6001, 4, 5, 4, '제주특별자치도 안양시 동안구 봉은사3로 (현정김마을)' UNION ALL
    SELECT '55203d3b-76c7-49bb-b54e-be54a93c7de9', 25570, 6001, 2, 6, 5, '대전광역시 종로구 서초대1로 (영수이김동)'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PAYMENT_HISTORY_TBL);

-- 14. PAYMENT_STATUS_TBL
INSERT INTO PAYMENT_STATUS_TBL (TID, CREATED_AT, APPROVED_AT, CANCELED_AT, FAILED_AT)
SELECT * FROM (
    SELECT
        'a57d6b00-1304-48d3-a5c8-e47824fcb26b' AS TID,
        '2025-05-20 02:26:15' AS CREATED_AT,
        NULL AS APPROVED_AT,
        '2025-05-20 02:26:15' AS CANCELED_AT,
        NULL AS FAILED_AT UNION ALL
    SELECT 'adbd95cc-26c0-4abd-9f0c-c7e00e026d7e', '2025-05-20 02:26:15', NULL, '2025-05-20 02:26:15', NULL UNION ALL
    SELECT 'dd6bed9d-ba45-4d3b-a844-606e3cfdfe2b', '2025-05-20 02:26:15', NULL, '2025-05-20 02:26:15', NULL UNION ALL
    SELECT 'f5591955-ce0b-4584-ac08-7488db9c6b31', '2025-05-20 02:26:10', '2025-05-20 02:26:15', NULL, NULL UNION ALL
    SELECT '55203d3b-76c7-49bb-b54e-be54a93c7de9', '2025-05-20 02:26:10', '2025-05-20 02:26:15', NULL, NULL

) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PAYMENT_STATUS_TBL);