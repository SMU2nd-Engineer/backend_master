-- 1. USER_TBL (샘플 유저 추가)
INSERT INTO USER_TBL (ID, NAME, EMAIL, PASSWORD, ADDRESS, NICKNAME, SOCIAL_LOGIN, SDATE, EDATE)
SELECT * FROM (
    SELECT 'ryugeonu', '류건우', 'ryu@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 강남구(123)', '건우짱', 'kakao', '2023-01-01', '2023-01-01' UNION ALL
    SELECT 'coegyeongsu', '고경수', 'ko@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 서초구(456)', '경수맨', 'google', '2023-01-01', '2023-01-01' UNION ALL
    SELECT 'areum92', '김아름', 'areum@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 마포구(789)', '아름이', 'naver', '2023-01-01', '2023-01-01' UNION ALL
    SELECT 'jiu52', '지유', 'jiu@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 송파구(789)', '지유짱', 'regularLogin', '2023-01-01', '2023-01-01' UNION ALL
    SELECT 'sangho11', '상호', 'sangho@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 종로구(147)', '상호짱', 'regularLogin', '2023-01-01', '2023-01-01' UNION ALL
    SELECT 'yeji86', '예지', 'yeji@example.com', '{bcrypt}$2a$10$2bFJEDsMWwGjIGqDIQK2seNgFa0CxRawL1s6QuKkV3fIXt0hXMA8C', '서울시 동대문구(258)', '예지짱', 'naver', '2023-01-01', '2023-01-01'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_TBL);

-- 2. PRODUCT_TBL
INSERT INTO PRODUCT_TBL (CATEGORY_IDX, TITLE, SDATE, EDATE, USERID, PRICE, FLAG)
SELECT * FROM (
    SELECT 1001, 'Occaecati placeat.', '2024-06-29', NULL, 'ryugeonu', 37435, 0 UNION ALL
    SELECT 1002, 'Assumenda maiores.', '2024-06-24', NULL, 'coegyeongsu', 89247, 1 UNION ALL
    SELECT 1003, 'Officia recusandae excepturi.', '2024-06-01', NULL, 'areum92', 32262, 1 UNION ALL
    SELECT 1002, 'Enim quas perferendis.', '2024-09-12', NULL, 'ryugeonu', 48116, 0 UNION ALL
    SELECT 1006, 'Quas tempora dignissimos.', '2024-06-21', NULL, 'areum92', 14074, 1
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PRODUCT_TBL);

-- 3. PRODUCT_DETAIL_TBL
INSERT INTO PRODUCT_DETAIL_TBL (PRODUCT_IDX, CONTENT, IMAGE_URL)
SELECT * FROM (
    SELECT 1, 'Veniam reiciendis magni ea fugit iusto amet. Cum sit tenetur.', 'https://placeimg.com/987/384/any' UNION ALL
    SELECT 2, 'Culpa cupiditate voluptatum praesentium nisi.', 'https://placekitten.com/606/750' UNION ALL
    SELECT 3, 'Voluptas sequi eum explicabo. At magni illo. Vero qui fugiat unde.', 'https://placeimg.com/850/673/any' UNION ALL
    SELECT 4, 'Nam deserunt consequuntur laudantium alias animi. Iste dignissimos consequatur natus iure.', 'https://placekitten.com/591/927' UNION ALL
    SELECT 5, 'Aperiam iusto in quasi. Nemo neque sint recusandae optio error.', 'https://placeimg.com/340/793/any'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PRODUCT_DETAIL_TBL);

-- 4. USER_TRANSACTION_TBL
INSERT INTO USER_TRANSACTION_TBL (USER_IDX, PRODUCT_IDX, DIVISION, SDATE)
SELECT * FROM (
    SELECT 1, 1, 1, '2025-05-19' UNION ALL
    SELECT 2, 2, 0, '2025-05-19' UNION ALL
    SELECT 3, 3, 1, '2025-05-19' UNION ALL
    SELECT 4, 4, 0, '2025-05-19' UNION ALL
    SELECT 5, 5, 1, '2025-05-19'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_TRANSACTION_TBL);

-- 5. USER_REVIEW_TBL
INSERT INTO USER_REVIEW_TBL (USER_IDX, TRANSACTION_IDX, RATING, REVIEW, SDATE, EDATE) VALUES
(1, 1, 2.9, 'Harum quibusdam sapiente dignissimos ea.', '2025-05-19', NULL),
(2, 2, 2.5, 'Pariatur itaque tempore illum rem quia facere.', '2025-05-19', NULL),
(3, 3, 3.7, 'Id quibusdam quibusdam praesentium.', '2025-05-19', NULL),
(4, 4, 2.8, 'Animi amet delectus dolorem libero aperiam.', '2025-05-19', NULL),
(5, 5, 2.1, 'Eaque velit iste vitae iusto.', '2025-05-19', NULL);

-- 6. USER_REVIEW_EVALUATION_TBL
INSERT INTO USER_REVIEW_EVALUATION_TBL (REVIEW_IDX, CATEGORYSUB_IDX, IS_EVALUATION, SDATE, EDATE)
SELECT * FROM (
    SELECT 1, 5008, 1, '2025-05-20', NULL UNION ALL  -- 늦은 시간에 연락해요
    SELECT 2, 5005, 1, '2025-05-20', NULL UNION ALL  -- 약속 시간을 잘 지켜요
    SELECT 3, 5003, 1, '2025-05-20', NULL UNION ALL  -- 물건이 깨끗해요
    SELECT 4, 5001, 1, '2025-05-20', NULL UNION ALL  -- 답변이 빨라요
    SELECT 5, 5001, 1, '2025-05-20', NULL             -- 답변이 빨라요
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_REVIEW_EVALUATION_TBL);

-- 7. USER_FAVORITE_TBL
INSERT INTO USER_FAVORITE_TBL (USER_IDX, CATEGORYSUB_IDX, SDATE, IS_FAVORITE, EDATE)
SELECT * FROM (
    SELECT 1, 1001, '2025-05-20', 1, NULL UNION ALL  -- 이미지와 상품이 달라요
    SELECT 2, 1004, '2025-05-20', 0, NULL UNION ALL  -- 물건이 깨끗해요
    SELECT 3, 2003, '2025-05-20', 1, NULL UNION ALL  -- 늦은 시간에 연락해요
    SELECT 4, 2004, '2025-05-20', 1, NULL UNION ALL  -- 불친절해요
    SELECT 5, 3005, '2025-05-20', 1, NULL            -- 사기당했어요!
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_FAVORITE_TBL);


-- 8. USER_PICK_TBL
INSERT INTO USER_PICK_TBL (USER_IDX, PRODUCT_IDX, SDATE, FLAG)
SELECT * FROM (
    SELECT 1, 1, '2025-05-20', 1 UNION ALL
    SELECT 2, 2, '2025-05-20', 0 UNION ALL
    SELECT 3, 3, '2025-05-20', 1 UNION ALL
    SELECT 4, 4, '2025-05-20', 0 UNION ALL
    SELECT 5, 5, '2025-05-20', 1
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM USER_PICK_TBL);

-- 9. CONTENTS_TBL
INSERT INTO CONTENTS_TBL (USER_IDX, CATEGORY_IDX, TITLE, DIVISION, CONTENT, SDATE, EDATE)
SELECT * FROM (
    SELECT 1, 4, 'Itaque quam.', 0, 'Minus nihil eligendi molestiae quidem maiores.', '2025-05-20', NULL UNION ALL
    SELECT 2, 4, 'Iure voluptates deserunt.', 1, 'Optio debitis neque laboriosam quibusdam.', '2025-05-20', NULL UNION ALL
    SELECT 3, 4, 'Sequi veritatis.', 0, 'Quidem quis maxime. Pariatur cum delectus.', '2025-05-20', NULL UNION ALL
    SELECT 4, 4, 'Ad fugiat consequatur.', 1, 'Molestiae vitae soluta dolorum vero velit.', '2025-05-20', NULL UNION ALL
    SELECT 5, 4, 'Tempore necessitatibus nisi.', 0, 'Velit fugiat similique dolores temporibus.', '2025-05-20', NULL
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM CONTENTS_TBL);

-- 10. CONTENTS_COMMENT_TBL
INSERT INTO CONTENTS_COMMENT_TBL (USER_IDX, CONTENTS_IDX, TEXT, SDATE)
SELECT * FROM (
    SELECT 1, 1, 'Aliquam earum eos sequi.', '2025-05-20' UNION ALL
    SELECT 2, 2, 'Id voluptatum sequi unde.', '2025-05-20' UNION ALL
    SELECT 3, 3, 'Nostrum reiciendis exercitationem libero.', '2025-05-20' UNION ALL
    SELECT 4, 4, 'Sed dolor at aperiam deleniti neque placeat.', '2025-05-20' UNION ALL
    SELECT 5, 5, 'Doloribus debitis error mollitia itaque nobis dolor sit.', '2025-05-20'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM CONTENTS_COMMENT_TBL);

-- 11. TICKET_TBL
INSERT INTO TICKET_TBL (SUB_IDX, TITLE, COMPANY, LINK, SDATE, EDATE, PLACE, PRICE, GRADE, CAST, RUNNINGTIME, IMG, ETC)
SELECT * FROM (
    SELECT 3, '가상 글로벌 펌웨어', '(주) 김', 'https://janggimseo.kr/', '2025-05-20', '2025-06-04', '안양시 만안구', '108483', 'A', '김은정', '117분', 'https://www.lorempixel.com/492/839', 'Assumenda ut est vitae.' UNION ALL
    SELECT 3, '확장된 클라이언트 단 생산 능력', '(유) 이', 'http://www.jusighoesa.com/', '2025-05-20', '2025-06-16', '부여군', '57142', 'A', '이순자', '94분', 'https://placeimg.com/603/795/any', 'Hic enim aspernatur ab facere ullam.' UNION ALL
    SELECT 3, '1:1 수요 중심 LAN', '유한회사 백', 'http://www.songgang.kr/', '2025-05-20', '2025-06-09', '청주시 상당구', '23583', 'S', '문수진', '101분', 'https://placekitten.com/829/344', 'Unde impedit mollitia.' UNION ALL
    SELECT 3, '강력한 국가적 공구', '최김', 'https://www.yu.kr/', '2025-05-20', '2025-05-27', '군포시', '123860', 'A', '김영길', '130분', 'https://www.lorempixel.com/244/994', 'Officia dolore perferendis quisquam.' UNION ALL
    SELECT 3, '변경 가능한 하이브리드 유연성', '김이이', 'http://www.ju.kr/', '2025-05-20', '2025-05-27', '계룡시', '75119', 'VIP', '김상호', '98분', 'https://www.lorempixel.com/579/122', 'Quo facere totam aliquid ea ipsa.'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM TICKET_TBL);

-- 12. PAYMENT_HISTORY_TBL
INSERT INTO PAYMENT_HISTORY_TBL (TID, AMOUNT, PAY_METHOD, STATUS, CREATE_AT, BUYER_ID, SELLER_ID, PRODUCT_ID, TRADE_TYPE, DELIVERY_ADDRESS)
SELECT * FROM (
    SELECT 'a57d6b00-1304-48d3-a5c8-e47824fcb26b', 78085, 'KAKAOPAY', 'CANCELLED', '2025-05-20 02:26:15', 'ryugeonu', 'jiu52', '1', 'MEET', '전라남도 논산시 반포대169가 (옥순양윤동)' UNION ALL
    SELECT 'adbd95cc-26c0-4abd-9f0c-c7e00e026d7e', 128653, 'KAKAOPAY', 'CANCELLED', '2025-05-20 02:26:15', 'sangho11', 'yeji86', '2', 'DELIVERY', '전라북도 수원시 팔달구 선릉거리' UNION ALL
    SELECT 'dd6bed9d-ba45-4d3b-a844-606e3cfdfe2b', 79594, 'KAKAOPAY', 'CANCELLED', '2025-05-20 02:26:15', 'jiu52', 'jiu52', '3', 'MEET', '경상남도 보령시 가락408로' UNION ALL
    SELECT 'f5591955-ce0b-4584-ac08-7488db9c6b31', 145302, 'BANK', 'PAID', '2025-05-20 02:26:15', 'sangho11', 'areum92', '4', 'DELIVERY', '제주특별자치도 안양시 동안구 봉은사3로 (현정김마을)' UNION ALL
    SELECT '55203d3b-76c7-49bb-b54e-be54a93c7de9', 25570, 'KAKAOPAY', 'PAID', '2025-05-20 02:26:15', 'coegyeongsu', 'yeji86', '5', 'DELIVERY', '대전광역시 종로구 서초대1로 (영수이김동)'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM PAYMENT_HISTORY_TBL);
