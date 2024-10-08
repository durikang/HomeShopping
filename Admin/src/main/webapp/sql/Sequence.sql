-- 1. USERS 테이블의 USER_NO 시퀀스 삭제
DROP SEQUENCE SEQ_USER_NO;

-- 6. BOARD 테이블의 BOARD_NO 시퀀스 삭제
DROP SEQUENCE SEQ_BOARD_NO;

-- 7. REPLY 테이블의 REPLY_NO 시퀀스 삭제
DROP SEQUENCE SEQ_BOARD_REPLY_NO;

-- 9. PRODUCT 테이블의 PRODUCT_NO 시퀀스 삭제
DROP SEQUENCE SEQ_PRODUCT_NO;

-- 10. PRODUCT_IMAGE 테이블의 IMAGE_NO 시퀀스 삭제
DROP SEQUENCE SEQ_SEQ_BOARD_FILEUPLOADS_NO;

-- 11. PRODUCT_REVIEW 테이블의 REVIEW_NO 시퀀스 삭제
DROP SEQUENCE SEQ_REVIEW_NO;

-- 12. PRODUCT_REVIEW_REPLY 테이블의 REPLY_NO 시퀀스 삭제
DROP SEQUENCE SEQ_PRODUCT_REVIEW_REPLY_NO;

-- 13. CART 테이블의 CART_NO 시퀀스 삭제
DROP SEQUENCE SEQ_CART_NO;

-- 14. CART_ITEM 테이블의 CART_ITEM_NO 시퀀스 삭제
DROP SEQUENCE SEQ_CART_ITEM_NO;

-- 15. ORDERS 테이블의 ORDER_NO 시퀀스 삭제
DROP SEQUENCE SEQ_ORDER_NO;

-- 16. ORDER_ITEM 테이블의 ORDER_ITEM_NO 시퀀스 삭제
DROP SEQUENCE SEQ_ORDER_ITEM_NO;

-- 17. DELIVERY 테이블의 DELIVERY_NO 시퀀스 삭제
DROP SEQUENCE SEQ_DELIVERY_NO;

-- 18. EVENT 테이블의 EVENT_NO 시퀀스 삭제
DROP SEQUENCE SEQ_EVENT_NO;

-- EVENT_IMAGE 테이블의 EVENT_IMAGE_NO 시퀀스
DROP SEQUENCE SEQ_EVENT_IMAGE_NO;

-- 19. COUPON 테이블의 COUPON_NO 시퀀스 삭제
DROP SEQUENCE SEQ_COUPON_NO;

-- 20. EMAIL_LOG 테이블의 EMAIL_LOG_NO 시퀀스 삭제
DROP SEQUENCE SEQ_EMAIL_LOG_NO;

-- 21. USER_COUPON 테이블의 USER_COUPON_NO 시퀀스 삭제
DROP SEQUENCE SEQ_USER_COUPON_NO;

-- 22. USER_LOG 테이블의 LOG_NO 시퀀스 삭제
DROP SEQUENCE SEQ_USER_LOG_NO;

-- 23. BOARD_VISIT 테이블의 VISIT_NO 시퀀스 삭제
DROP SEQUENCE SEQ_BOARD_VISIT_ID; -- SEQ_BOARD_VISIT_NO로 수정 되었음 혹시나 해서 삭제 쿼리 넣어둠
DROP SEQUENCE SEQ_BOARD_VISIT_NO; 

-- 24. USER_WALLET 테이블의 WALLET_NO 시퀀스 삭제
DROP SEQUENCE SEQ_WALLET_NO;

-- 25. COMPANY_REVENUE 테이블의 REVENUE_NO 시퀀스 삭제
DROP SEQUENCE SEQ_REVENUE_NO;

-- 26. BOARD_VISIT 테이블의 SEQ_BOARD_VISIT_NO 시퀀스 삭제
DROP SEQUENCE SEQ_BOARD_VISIT_NO;

-- 사진 업로드를 위한 SEQ_BOARD_IMAGE_NO 시퀀스 삭제
DROP SEQUENCE SEQ_BOARD_IMAGE_NO;


-- 1. USERS 테이블의 USER_NO 시퀀스
CREATE SEQUENCE SEQ_USER_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 2. CUSTOMER 테이블의 USER_NO는 USERS 테이블의 USER_NO와 동일한 시퀀스를 사용합니다.

-- 3. ADMIN_ROLE 테이블에는 NUMBER 타입의 PRIMARY KEY가 없으므로 시퀀스 생성 불필요

-- 4. ADMIN 테이블의 USER_NO는 USERS 테이블의 USER_NO와 동일한 시퀀스를 사용합니다.

-- 5. BOARD_CATEGORY 테이블에는 NUMBER 타입의 PRIMARY KEY가 없으므로 시퀀스 생성 불필요

-- 6. BOARD 테이블의 BOARD_NO 시퀀스
CREATE SEQUENCE SEQ_BOARD_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 7. REPLY 테이블의 REPLY_NO 시퀀스
CREATE SEQUENCE SEQ_BOARD_REPLY_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 8. PRODUCT_CATEGORY 테이블에는 NUMBER 타입의 PRIMARY KEY가 없으므로 시퀀스 생성 불필요

-- 9. PRODUCT 테이블의 PRODUCT_NO 시퀀스
CREATE SEQUENCE SEQ_PRODUCT_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 10.  BOARD_FILEUPLOADS 테이블의 FILE_NO 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD_FILEUPLOADS_NO
START WITH 1
INCREMENT BY 1
NOCACHE;


-- 11. PRODUCT_REVIEW 테이블의 REVIEW_NO 시퀀스
CREATE SEQUENCE SEQ_REVIEW_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 12. PRODUCT_REVIEW_REPLY 테이블의 REPLY_NO 시퀀스
CREATE SEQUENCE SEQ_PRODUCT_REVIEW_REPLY_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 13. CART 테이블의 CART_NO 시퀀스
CREATE SEQUENCE SEQ_CART_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 14. CART_ITEM 테이블의 CART_ITEM_NO 시퀀스
CREATE SEQUENCE SEQ_CART_ITEM_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 15. ORDERS 테이블의 ORDER_NO 시퀀스
CREATE SEQUENCE SEQ_ORDER_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 16. ORDER_ITEM 테이블의 ORDER_ITEM_NO 시퀀스
CREATE SEQUENCE SEQ_ORDER_ITEM_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 17. DELIVERY 테이블의 DELIVERY_NO 시퀀스
CREATE SEQUENCE SEQ_DELIVERY_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 18. EVENT 테이블의 EVENT_NO 시퀀스
CREATE SEQUENCE SEQ_EVENT_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- EVENT_IMAGE 테이블의 EVENT_IMAGE_NO 시퀀스
CREATE SEQUENCE SEQ_EVENT_IMAGE_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 19. COUPON 테이블의 COUPON_NO 시퀀스
CREATE SEQUENCE SEQ_COUPON_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 20. EMAIL_LOG 테이블의 EMAIL_LOG_NO 시퀀스
CREATE SEQUENCE SEQ_EMAIL_LOG_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 21. USER_COUPON 테이블의 USER_COUPON_NO 시퀀스
CREATE SEQUENCE SEQ_USER_COUPON_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 22. USER_LOG 테이블의 LOG_NO 시퀀스
CREATE SEQUENCE SEQ_USER_LOG_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 24. USER_WALLET 테이블의 WALLET_NO 시퀀스
CREATE SEQUENCE SEQ_WALLET_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 25. COMPANY_REVENUE 테이블의 REVENUE_NO 시퀀스
CREATE SEQUENCE SEQ_REVENUE_NO
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 26. BOARD_VISIT 테이블의 SEQ_BOARD_VISIT_NO 시퀀스

CREATE SEQUENCE SEQ_BOARD_VISIT_NO
START WITH 1  -- 시작 값
INCREMENT BY 1  -- 증가 값
MINVALUE 1  -- 최소 값
MAXVALUE 9999999999999999999999999999  -- 최대 값
NOCYCLE  -- 최대 값에 도달해도 다시 시작하지 않음
NOCACHE;  -- 캐싱을 사용하지 않음


-- 사진 업로드를 위한 시퀀스 생성 (IMAGE_NO)
CREATE SEQUENCE SEQ_BOARD_IMAGE_NO
START WITH 1
INCREMENT BY 1
NOCACHE;
