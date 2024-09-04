-- 테이블 삭제
DROP TABLE USER_COUPON CASCADE CONSTRAINTS; 
DROP TABLE EMAIL_LOG CASCADE CONSTRAINTS;
DROP TABLE COUPON CASCADE CONSTRAINTS;
DROP TABLE EVENT CASCADE CONSTRAINTS;
DROP TABLE DELIVERY CASCADE CONSTRAINTS;
DROP TABLE ORDER_ITEM CASCADE CONSTRAINTS;
DROP TABLE ORDERS CASCADE CONSTRAINTS;
DROP TABLE CART_ITEM CASCADE CONSTRAINTS;
DROP TABLE CART CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_REVIEW_REPLY CASCADE CONSTRAINTS; 
DROP TABLE PRODUCT_REVIEW CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_IMAGE CASCADE CONSTRAINTS;
DROP TABLE PRODUCT CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_CATEGORY CASCADE CONSTRAINTS;
DROP TABLE BOARD_REPLY CASCADE CONSTRAINTS;
DROP TABLE REPLY CASCADE CONSTRAINTS;                       -- 기존 REPLY 테이블이 존재 하는 경우 삭제 구문
DROP TABLE BOARD CASCADE CONSTRAINTS;
DROP TABLE BOARD_CATEGORY CASCADE CONSTRAINTS;
DROP TABLE ADMIN CASCADE CONSTRAINTS;
DROP TABLE ADMIN_ROLE CASCADE CONSTRAINTS;
DROP TABLE CUSTOMER CASCADE CONSTRAINTS;
DROP TABLE USERS CASCADE CONSTRAINTS;
DROP TABLE USER_LOG CASCADE CONSTRAINTS; -- 추가된 로그 테이블
DROP TABLE USER_WALLET CASCADE CONSTRAINTS; -- 유저 지갑 관리 테이블 삭제 구문
DROP TABLE COMPANY_REVENUE CASCADE CONSTRAINTS; -- 회사 수익 관리 테이블 삭제 구문
DROP TABLE BOARD_VISIT CASCADE CONSTRAINTS; 

-- 1. 사용자 정보를 관리하는 테이블
CREATE TABLE USERS (
    USER_NO NUMBER PRIMARY KEY,  
    USER_ID VARCHAR2(50) UNIQUE NOT NULL,  
    PASSWORD VARCHAR2(100) NOT NULL,  
    NAME VARCHAR2(100) NOT NULL,  
    EMAIL VARCHAR2(100),  
    USER_TYPE VARCHAR2(20) NOT NULL CHECK (USER_TYPE IN ('CUSTOMER', 'ADMIN')),  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE  
);

-- 2. 회원 정보를 관리하는 테이블 (USERS 테이블 참조)
CREATE TABLE CUSTOMER (
    USER_NO NUMBER PRIMARY KEY,  
    AGE NUMBER,  
    JOB VARCHAR2(100),  
    LOCATION VARCHAR2(100),  
    MILEAGE NUMBER DEFAULT 0,  
    LAST_LOGIN_DATE DATE,  
    TOTAL_PURCHASE_AMOUNT NUMBER DEFAULT 0,  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
);

-- 3. 관리자 역할을 관리하는 테이블
CREATE TABLE ADMIN_ROLE (
    ROLE_CODE VARCHAR2(300) PRIMARY KEY,  
    ROLE_NAME VARCHAR2(100) NOT NULL  
);

-- 4. 관리자 계정을 관리하는 테이블 (USERS 및 ADMIN_ROLE 테이블 참조)
CREATE TABLE ADMIN (
    USER_NO NUMBER PRIMARY KEY,  
    ROLE_CODE VARCHAR2(300),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),  
    FOREIGN KEY (ROLE_CODE) REFERENCES ADMIN_ROLE(ROLE_CODE)  
);

-- 5. 게시판 카테고리를 관리하는 테이블
CREATE TABLE BOARD_CATEGORY (
    CATEGORY_NO VARCHAR2(300) PRIMARY KEY,  
    NAME VARCHAR2(100) NOT NULL,  
    DESCRIPTION VARCHAR2(255)  
);

-- 6. 게시판 게시글을 관리하는 테이블 (USERS 및 BOARD_CATEGORY 테이블 참조)
CREATE TABLE BOARD (
    BOARD_NO NUMBER PRIMARY KEY,  
    USER_NO NUMBER,  
    CATEGORY_NO VARCHAR2(300),  
    TITLE VARCHAR2(200) NOT NULL,  
    CONTENT VARCHAR2(4000),  
    VIEWS NUMBER DEFAULT 0,  -- 조회수 추가
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N',  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),  
    FOREIGN KEY (CATEGORY_NO) REFERENCES BOARD_CATEGORY(CATEGORY_NO)  
);

-- 7. 게시글의 댓글을 관리하는 테이블 (USERS 및 BOARD 테이블 참조)
CREATE TABLE BOARD_REPLY (
    REPLY_NO NUMBER PRIMARY KEY,  
    BOARD_NO NUMBER,  
    USER_NO NUMBER NULL,  -- 추후에 null 삭제 예정
    CONTENT VARCHAR2(4000),  
    LEFT_VAL NUMBER,  -- 중첩 집합에서의 왼쪽 값
    RIGHT_VAL NUMBER,  -- 중첩 집합에서의 오른쪽 값
    NODE_LEVEL NUMBER,  -- 계층 수준 (루트: 1, 자식: 2 등)
    PARENT_REPLY_NO NUMBER,  -- 부모 댓글 번호 (NULL이면 최상위 댓글)
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),
    FOREIGN KEY (PARENT_REPLY_NO) REFERENCES BOARD_REPLY(REPLY_NO)  -- 자기 자신을 참조하는 외래 키
);



-- 8. 상품 카테고리를 관리하는 테이블
CREATE TABLE PRODUCT_CATEGORY (
    CATEGORY_NO VARCHAR2(300) PRIMARY KEY,  
    NAME VARCHAR2(100) NOT NULL,  
    DESCRIPTION VARCHAR2(255)  
);

-- 9. 상품 정보를 관리하는 테이블 (PRODUCT_CATEGORY 테이블 참조)
CREATE TABLE PRODUCT (
    PRODUCT_NO NUMBER PRIMARY KEY,  -- 상품 번호
    CATEGORY_NO VARCHAR2(300),  -- 카테고리 번호
    NAME VARCHAR2(100) NOT NULL, -- 상품명
    DESCRIPTION VARCHAR2(4000),  -- 상품 정보
    PRICE NUMBER NOT NULL,  -- 가격
    STOCK_QUANTITY NUMBER,  -- 재고 수량
    VIEWS NUMBER DEFAULT 0,  -- 조회수
    CREATED_AT DATE DEFAULT SYSDATE,  -- 상품 등록일
    UPDATED_AT DATE,  -- 상품 수정일
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')), -- 상품 삭제 유무  
    TOTAL_SALES NUMBER DEFAULT 0,  -- 상품 판매량
    USER_NO NUMBER,  -- 상품을 등록한 어드민의 USER_NO
    FOREIGN KEY (CATEGORY_NO) REFERENCES PRODUCT_CATEGORY(CATEGORY_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  -- 어드민 정보를 참조
);


-- 10. 상품 이미지를 관리하는 테이블 (PRODUCT 테이블 참조)
CREATE TABLE PRODUCT_IMAGE (
    IMAGE_NO NUMBER PRIMARY KEY,  
    PRODUCT_NO NUMBER,  
    IMAGE_URL VARCHAR2(255) NOT NULL,  
    DESCRIPTION VARCHAR2(255),  
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
);

-- 11. 상품 리뷰를 관리하는 테이블 (USERS 및 PRODUCT 테이블 참조)
CREATE TABLE PRODUCT_REVIEW (
    REVIEW_NO NUMBER PRIMARY KEY,  
    PRODUCT_NO NUMBER,  
    USER_NO NUMBER,  
    RATING NUMBER CHECK (RATING BETWEEN 1 AND 5),  
    COMM VARCHAR2(4000),  
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
);

-- 12. 상품 리뷰의 댓글을 관리하는 테이블 (USERS 및 PRODUCT_REVIEW 테이블 참조)
CREATE TABLE PRODUCT_REVIEW_REPLY (
    REPLY_NO NUMBER PRIMARY KEY,  
    REVIEW_NO NUMBER,  
    USER_NO NUMBER,  
    CONTENT VARCHAR2(4000),  
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    FOREIGN KEY (REVIEW_NO) REFERENCES PRODUCT_REVIEW(REVIEW_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
);

-- 13. 사용자 장바구니를 관리하는 테이블 (USERS 테이블 참조)
CREATE TABLE CART (
    CART_NO NUMBER PRIMARY KEY,  
    USER_NO NUMBER,  
    CREATED_AT DATE DEFAULT SYSDATE,  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
);

-- 14. 장바구니에 담긴 상품 항목을 관리하는 테이블 (CART 및 PRODUCT 테이블 참조)
CREATE TABLE CART_ITEM (
    CART_ITEM_NO NUMBER PRIMARY KEY,  
    CART_NO NUMBER,  
    PRODUCT_NO NUMBER,  
    QUANTITY NUMBER NOT NULL,  
    ADDED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    FOREIGN KEY (CART_NO) REFERENCES CART(CART_NO),  
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
);

-- 15. 주문 정보를 관리하는 테이블 (USERS 테이블 참조)
CREATE TABLE ORDERS (
    ORDER_NO NUMBER PRIMARY KEY,  
    USER_NO NUMBER,  
    ORDER_DATE DATE DEFAULT SYSDATE,  
    STATUS VARCHAR2(50) DEFAULT 'PENDING' CHECK (STATUS IN ('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED')),  
    TOTAL_AMOUNT NUMBER NOT NULL,  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
);

-- 16. 주문 항목을 관리하는 테이블 (ORDERS 및 PRODUCT 테SHIPPED이블 참조)
CREATE TABLE ORDER_ITEM (
    ORDER_ITEM_NO NUMBER PRIMARY KEY,  
    ORDER_NO NUMBER,  
    PRODUCT_NO NUMBER,  
    QUANTITY NUMBER NOT NULL,  
    PRICE NUMBER NOT NULL,  
    FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO),  
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
);

-- 17. 주문 배송 정보를 관리하는 테이블 (ORDERS 테이블 참조)
CREATE TABLE DELIVERY (
    DELIVERY_NO NUMBER PRIMARY KEY,  
    ORDER_NO NUMBER,  
    DELIVERY_DATE DATE,  
    DELIVERY_STATUS VARCHAR2(50) DEFAULT 'PENDING' CHECK (DELIVERY_STATUS IN ('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED')),  
    FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO)  
);

-- 18. 이벤트 정보를 관리하는 테이블
CREATE TABLE EVENT (
    EVENT_NO NUMBER PRIMARY KEY,  
    NAME VARCHAR2(255) NOT NULL,  
    DESCRIPTION VARCHAR2(1000),  
    START_DATE DATE,  
    END_DATE DATE,  
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE  
);

-- 19. 쿠폰 정보를 관리하는 테이블 (EVENT 테이블 참조)
CREATE TABLE COUPON (
    COUPON_NO NUMBER PRIMARY KEY,  
    CODE VARCHAR2(50) UNIQUE NOT NULL,  
    DISCOUNT_AMOUNT NUMBER,  
    DISCOUNT_PERCENT NUMBER,  
    EXPIRY_DATE DATE,  
    IS_USED CHAR(1) DEFAULT 'N' CHECK (IS_USED IN ('Y', 'N')),  
    EVENT_NO NUMBER,  
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    FOREIGN KEY (EVENT_NO) REFERENCES EVENT(EVENT_NO)  
);

-- 20. 이메일 발송 기록을 관리하는 테이블 (COUPON 테이블 참조)
CREATE TABLE EMAIL_LOG (
    EMAIL_LOG_NO NUMBER PRIMARY KEY,  
    EMAIL_ADDRESS VARCHAR2(255) NOT NULL,  
    COUPON_NO NUMBER,  
    SENT_AT DATE DEFAULT SYSDATE,  
    STATUS VARCHAR2(50) DEFAULT 'SENT' CHECK (STATUS IN ('SENT', 'FAILED')),  
    ERROR_MESSAGE VARCHAR2(1000),  
    FOREIGN KEY (COUPON_NO) REFERENCES COUPON(COUPON_NO)  
);

-- 21. 사용자 쿠폰을 관리하는 테이블 (USERS 및 COUPON 테이블 참조)
CREATE TABLE USER_COUPON (
    USER_COUPON_NO NUMBER PRIMARY KEY,  
    USER_NO NUMBER,  
    COUPON_NO NUMBER,  
    RECEIVED_AT DATE DEFAULT SYSDATE,  
    IS_REDEEMED CHAR(1) DEFAULT 'N' CHECK (IS_REDEEMED IN ('Y', 'N')),  
    REDEEMED_AT DATE,  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),  
    FOREIGN KEY (COUPON_NO) REFERENCES COUPON(COUPON_NO)  
);

-- 22. 사용자 활동 로그를 관리하는 테이블 (새로 추가된 테이블)
CREATE TABLE USER_LOG (
    LOG_NO NUMBER PRIMARY KEY,  -- 로그 번호 (Primary Key)
    USER_NO NUMBER,  -- 사용자 번호 (USERS 테이블 참조)
    ACTION VARCHAR2(255) NOT NULL,  -- 사용자 행동 (예: '로그인', '게시글 작성', '상품 조회' 등)
    DESCRIPTION VARCHAR2(4000),  -- 행동 설명
    CREATED_AT DATE DEFAULT SYSDATE,  -- 행동 발생 시각 (기본값: 현재 날짜)
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  -- USERS 테이블 참조
);
-- 23. 게시판 방문 기록
CREATE TABLE BOARD_VISIT (
    VISIT_ID NUMBER PRIMARY KEY,
    USER_NO NUMBER,
    BOARD_NO NUMBER,
    VISIT_DATE DATE DEFAULT SYSDATE,
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),
    FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO)
);


--유저 지갑 관리 테이블 (USER_WALLET)
--설명:
--
--USER_WALLET 테이블은 각 유저의 지갑 정보를 관리합니다.
--유저가 쇼핑몰에서 구매할 때 사용되는 잔액이 기록됩니다.
--잔액이 변동될 때마다 LAST_UPDATED 날짜가 갱신됩니다
CREATE TABLE USER_WALLET (
    WALLET_NO NUMBER PRIMARY KEY,  -- 지갑 고유 번호
    USER_NO NUMBER NOT NULL,  -- 사용자 번호 (USERS 테이블 참조)
    BALANCE NUMBER DEFAULT 0,  -- 지갑 잔액 (기본값 0) BALANCE 컬럼을 통해 유저가 물건을 구매할 때 차감되거나, 환불될 때 다시 추가되는 금액을 관리합니다.
    LAST_UPDATED DATE DEFAULT SYSDATE,  -- 지갑 잔액이 마지막으로 업데이트된 날짜 LAST_UPDATED 컬럼을 통해 마지막으로 지갑이 갱신된 시점을 기록하여 유저의 최근 활동을 추적할 수 있습니다.
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  -- USERS 테이블을 참조하는 외래 키
);

-- 회사 수익 관리 테이블 (COMPANY_REVENUE)
--설명:
--
--COMPANY_REVENUE 테이블은 각 주문으로 인해 발생한 회사의 수익을 기록합니다.
--각 수익 항목은 주문 번호(ORDER_NO)와 연결되며, 언제 수익이 발생했는지 기록합니다.

CREATE TABLE COMPANY_REVENUE (
    REVENUE_NO NUMBER PRIMARY KEY,  -- 수익 고유 번호
    ORDER_NO NUMBER,  -- 주문 번호 (ORDERS 테이블 참조)
    AMOUNT NUMBER NOT NULL,  -- 수익 금액
    REVENUE_DATE DATE DEFAULT SYSDATE,  -- 수익 발생 날짜
    FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO)  -- ORDERS 테이블을 참조하는 외래 키
);
--수익 기록: AMOUNT 컬럼을 통해 각 주문에서 발생한 수익을 기록합니다. 이는 총 매출 분석에 중요한 데이터를 제공합니다.
--수익 발생 시점 추적: REVENUE_DATE 컬럼을 통해 수익이 발생한 날짜를 기록, 이를 통해 특정 기간 동안의 매출 분석이 가능합니다.



