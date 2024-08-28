-- 테이블 삭제
DROP TABLE USER_COUPON CASCADE CONSTRAINTS; -- 여기다 주석달아줘
DROP TABLE EMAIL_LOG CASCADE CONSTRAINTS;
DROP TABLE COUPON CASCADE CONSTRAINTS;
DROP TABLE EVENT CASCADE CONSTRAINTS;
DROP TABLE DELIVERY CASCADE CONSTRAINTS;
DROP TABLE ORDER_ITEM CASCADE CONSTRAINTS;
DROP TABLE ORDERS CASCADE CONSTRAINTS;
DROP TABLE CART_ITEM CASCADE CONSTRAINTS;
DROP TABLE CART CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_REVIEW CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_IMAGE CASCADE CONSTRAINTS;
DROP TABLE PRODUCT CASCADE CONSTRAINTS;
DROP TABLE PRODUCT_CATEGORY CASCADE CONSTRAINTS;
DROP TABLE REPLY CASCADE CONSTRAINTS;
DROP TABLE BOARD CASCADE CONSTRAINTS;
DROP TABLE BOARD_CATEGORY CASCADE CONSTRAINTS;
DROP TABLE ADMIN CASCADE CONSTRAINTS;
DROP TABLE ADMIN_ROLE CASCADE CONSTRAINTS;
DROP TABLE CUSTOMER CASCADE CONSTRAINTS;
DROP TABLE USER CASCADE CONSTRAINTS;



-- 1. 사용자 정보를 관리하는 테이블
CREATE TABLE USER (
    USER_NO NUMBER PRIMARY KEY,  -- 사용자 번호 (Primary Key)
    USER_ID VARCHAR2(50) UNIQUE NOT NULL,  -- 사용자 아이디 (Unique, 필수 입력)
    PASSWORD VARCHAR2(100) NOT NULL,  -- 사용자 비밀번호 (필수 입력)
    NAME VARCHAR2(100) NOT NULL,  -- 사용자 이름 (필수 입력)
    EMAIL VARCHAR2(100),  -- 사용자 이메일 주소
    USER_TYPE VARCHAR2(20) NOT NULL CHECK (USER_TYPE IN ('CUSTOMER', 'ADMIN')),  -- 사용자 유형
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  -- 사용자 삭제 여부 (Default: N)
    CREATED_AT DATE DEFAULT SYSDATE,  -- 사용자 등록일 (기본값: 현재 날짜)
    UPDATED_AT DATE  -- 사용자 정보 수정일
);

-- 2. 회원 정보를 관리하는 테이블 (USER 테이블 참조)
CREATE TABLE CUSTOMER (
    USER_NO NUMBER PRIMARY KEY,  -- 사용자 번호 (Primary Key, USER 테이블 참조)
    AGE NUMBER,  -- 회원 나이
    JOB VARCHAR2(100),  -- 회원 직업
    LOCATION VARCHAR2(100),  -- 회원 사는 곳
    MILEAGE NUMBER DEFAULT 0,  -- 회원 마일리지 (기본값: 0)
    LAST_LOGIN_DATE DATE,  -- 마지막 로그인 날짜
    TOTAL_PURCHASE_AMOUNT NUMBER DEFAULT 0,  -- 총 구매 금액 (기본값: 0)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)  -- USER 테이블 참조
);

-- 3. 관리자 역할을 관리하는 테이블
CREATE TABLE ADMIN_ROLE (
    ROLE_CODE VARCHAR2(300) PRIMARY KEY,  -- 역할 코드 (Primary Key)
    ROLE_NAME VARCHAR2(100) NOT NULL  -- 역할 이름 (필수 입력)
);

-- 4. 관리자 계정을 관리하는 테이블 (USER 및 ADMIN_ROLE 테이블 참조)
CREATE TABLE ADMIN (
    USER_NO NUMBER PRIMARY KEY,  -- 사용자 번호 (Primary Key, USER 테이블 참조)
    ROLE_CODE VARCHAR2(300),  -- 역할 코드 (ADMIN_ROLE 테이블의 ROLE_CODE를 참조)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO),  -- USER 테이블 참조
    FOREIGN KEY (ROLE_CODE) REFERENCES ADMIN_ROLE(ROLE_CODE)  -- 역할 코드 (ADMIN_ROLE 테이블 참조)
);

-- 5. 게시판 카테고리를 관리하는 테이블
CREATE TABLE BOARD_CATEGORY (
    CATEGORY_NO VARCHAR2(300) PRIMARY KEY,  -- 카테고리 번호 (Primary Key)
    NAME VARCHAR2(100) NOT NULL,  -- 카테고리 이름 (필수 입력)
    DESCRIPTION VARCHAR2(255)  -- 카테고리 설명
);

-- 6. 게시판 게시글을 관리하는 테이블 (USER 및 BOARD_CATEGORY 테이블 참조)
CREATE TABLE BOARD (
    BOARD_NO NUMBER PRIMARY KEY,  -- 게시글 번호 (Primary Key)
    USER_NO NUMBER,  -- 게시글 작성자 (USER 테이블 참조)
    CATEGORY_NO VARCHAR2(300),  -- 게시글 카테고리 (BOARD_CATEGORY 테이블 참조)
    TITLE VARCHAR2(200) NOT NULL,  -- 게시글 제목 (필수 입력)
    CONTENT VARCHAR2(4000),  -- 게시글 내용
    CREATED_AT DATE DEFAULT SYSDATE,  -- 게시글 작성일 (기본값: 현재 날짜)
    UPDATED_AT DATE,  -- 게시글 수정일
    IS_DELETED CHAR(1) DEFAULT 'N',  -- 게시글 삭제 여부 (Default: N)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO),  -- 작성자 (USER 테이블 참조)
    FOREIGN KEY (CATEGORY_NO) REFERENCES BOARD_CATEGORY(CATEGORY_NO)  -- 카테고리 (BOARD_CATEGORY 테이블 참조)
);

-- 7. 게시글의 댓글을 관리하는 테이블 (USER 및 BOARD 테이블 참조)
CREATE TABLE REPLY (
    REPLY_NO NUMBER PRIMARY KEY,  -- 댓글 번호 (Primary Key)
    BOARD_NO NUMBER,  -- 댓글이 달린 게시글 (BOARD 테이블 참조)
    USER_NO NUMBER,  -- 댓글 작성자 (USER 테이블 참조)
    CONTENT VARCHAR2(4000),  -- 댓글 내용
    CREATED_AT DATE DEFAULT SYSDATE,  -- 댓글 작성일 (기본값: 현재 날짜)
    UPDATED_AT DATE,  -- 댓글 수정일
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  -- 댓글 삭제 여부 (Default: N)
    FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  -- 게시글 (BOARD 테이블 참조)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)  -- 작성자 (USER 테이블 참조)
);

-- 8. 상품 카테고리를 관리하는 테이블
CREATE TABLE PRODUCT_CATEGORY (
    CATEGORY_NO VARCHAR2(300) PRIMARY KEY,  -- 상품 카테고리 번호 (Primary Key)
    NAME VARCHAR2(100) NOT NULL,  -- 상품 카테고리 이름 (필수 입력)
    DESCRIPTION VARCHAR2(255)  -- 상품 카테고리 설명
);

-- 9. 상품 정보를 관리하는 테이블 (PRODUCT_CATEGORY 테이블 참조)
CREATE TABLE PRODUCT (
    PRODUCT_NO NUMBER PRIMARY KEY,  -- 상품 번호 (Primary Key)
    CATEGORY_NO VARCHAR2(300),  -- 상품 카테고리 (PRODUCT_CATEGORY 테이블 참조)
    NAME VARCHAR2(100) NOT NULL,  -- 상품 이름 (필수 입력)
    DESCRIPTION VARCHAR2(4000),  -- 상품 설명
    PRICE NUMBER NOT NULL,  -- 상품 가격 (필수 입력)
    STOCK_QUANTITY NUMBER,  -- 재고 수량
    CREATED_AT DATE DEFAULT SYSDATE,  -- 상품 등록일 (기본값: 현재 날짜)
    UPDATED_AT DATE,  -- 상품 수정일
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  -- 상품 삭제 여부 (Default: N)
    TOTAL_SALES NUMBER DEFAULT 0,  -- 총 판매량 (기본값: 0)
    FOREIGN KEY (CATEGORY_NO) REFERENCES PRODUCT_CATEGORY(CATEGORY_NO)  -- 상품 카테고리 (PRODUCT_CATEGORY 테이블 참조)
);

-- 10. 상품 이미지를 관리하는 테이블 (PRODUCT 테이블 참조)
CREATE TABLE PRODUCT_IMAGE (
    IMAGE_NO NUMBER PRIMARY KEY,  -- 이미지 번호 (Primary Key)
    PRODUCT_NO NUMBER,  -- 이미지가 연결된 상품 (PRODUCT 테이블 참조)
    IMAGE_URL VARCHAR2(255) NOT NULL,  -- 이미지 URL (필수 입력)
    DESCRIPTION VARCHAR2(255),  -- 이미지 설명
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  -- 상품 (PRODUCT 테이블 참조)
);

-- 11. 상품 리뷰를 관리하는 테이블 (USER 및 PRODUCT 테이블 참조)
CREATE TABLE PRODUCT_REVIEW (
    REVIEW_NO NUMBER PRIMARY KEY,  -- 리뷰 번호 (Primary Key)
    PRODUCT_NO NUMBER,  -- 리뷰 대상 상품 (PRODUCT 테이블 참조)
    USER_NO NUMBER,  -- 리뷰 작성자 (USER 테이블 참조)
    RATING NUMBER CHECK (RATING BETWEEN 1 AND 5),  -- 리뷰 평점 (1~5)
    COMM VARCHAR2(4000),  -- 리뷰 내용
    CREATED_AT DATE DEFAULT SYSDATE,  -- 리뷰 작성일 (기본값: 현재 날짜)
    UPDATED_AT DATE,  -- 리뷰 수정일
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  -- 리뷰 삭제 여부 (Default: N)
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO),  -- 상품 (PRODUCT 테이블 참조)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)  -- 작성자 (USER 테이블 참조)
);

-- 12. 사용자 장바구니를 관리하는 테이블 (USER 테이블 참조)
CREATE TABLE CART (
    CART_NO NUMBER PRIMARY KEY,  -- 장바구니 번호 (Primary Key)
    USER_NO NUMBER,  -- 장바구니 소유자 (USER 테이블 참조)
    CREATED_AT DATE DEFAULT SYSDATE,  -- 장바구니 생성일 (기본값: 현재 날짜)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)  -- 소유자 (USER 테이블 참조)
);

-- 13. 장바구니에 담긴 상품 항목을 관리하는 테이블 (CART 및 PRODUCT 테이블 참조)
CREATE TABLE CART_ITEM (
    CART_ITEM_NO NUMBER PRIMARY KEY,  -- 장바구니 항목 번호 (Primary Key)
    CART_NO NUMBER,  -- 장바구니 (CART 테이블 참조)
    PRODUCT_NO NUMBER,  -- 장바구니에 담긴 상품 (PRODUCT 테이블 참조)
    QUANTITY NUMBER NOT NULL,  -- 수량 (필수 입력)
    ADDED_AT DATE DEFAULT SYSDATE,  -- 항목 추가일 (기본값: 현재 날짜)
    UPDATED_AT DATE,  -- 항목 수정일
    FOREIGN KEY (CART_NO) REFERENCES CART(CART_NO),  -- 장바구니 (CART 테이블 참조)
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  -- 상품 (PRODUCT 테이블 참조)
);

-- 14. 주문 정보를 관리하는 테이블 (USER 테이블 참조)
CREATE TABLE ORDERS (
    ORDER_NO NUMBER PRIMARY KEY,  -- 주문 번호 (Primary Key)
    USER_NO NUMBER,  -- 주문한 고객 (USER 테이블 참조)
    ORDER_DATE DATE DEFAULT SYSDATE,  -- 주문 일자 (기본값: 현재 날짜)
    STATUS VARCHAR2(50) DEFAULT 'PENDING' CHECK (STATUS IN ('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED')),  -- 주문 상태
    TOTAL_AMOUNT NUMBER NOT NULL,  -- 총 주문 금액 (필수 입력)
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)  -- 고객 (USER 테이블 참조)
);

-- 15. 주문 항목을 관리하는 테이블 (ORDERS 및 PRODUCT 테이블 참조)
CREATE TABLE ORDER_ITEM (
    ORDER_ITEM_NO NUMBER PRIMARY KEY,  -- 주문 항목 번호 (Primary Key)
    ORDER_NO NUMBER,  -- 주문 (ORDERS 테이블 참조)
    PRODUCT_NO NUMBER,  -- 주문한 상품 (PRODUCT 테이블 참조)
    QUANTITY NUMBER NOT NULL,  -- 수량 (필수 입력)
    PRICE NUMBER NOT NULL,  -- 가격 (필수 입력)
    FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO),  -- 주문 (ORDERS 테이블 참조)
    FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  -- 상품 (PRODUCT 테이블 참조)
);

-- 16. 주문 배송 정보를 관리하는 테이블 (ORDERS 테이블 참조)
CREATE TABLE DELIVERY (
    DELIVERY_NO NUMBER PRIMARY KEY,  -- 배송 번호 (Primary Key)
    ORDER_NO NUMBER,  -- 주문 (ORDERS 테이블 참조)
    DELIVERY_DATE DATE,  -- 배송일
    DELIVERY_STATUS VARCHAR2(50) DEFAULT 'PENDING' CHECK (DELIVERY_STATUS IN ('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED')),  -- 배송 상태
    FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO)  -- 주문 (ORDERS 테이블 참조)
);

-- 17. 이벤트 정보를 관리하는 테이블
CREATE TABLE EVENT (
    EVENT_NO NUMBER PRIMARY KEY, -- 이벤트 NO: 각 이벤트를 고유하게 식별하는 기본 키입니다.
    NAME VARCHAR2(255) NOT NULL, -- 이벤트 이름: 이벤트의 제목이나 이름을 저장합니다. 예: "봄맞이 할인".
    DESCRIPTION VARCHAR2(1000), -- 이벤트 설명: 이벤트에 대한 상세 설명을 저장합니다.
    START_DATE DATE, -- 이벤트 시작일: 이벤트가 시작되는 날짜를 저장합니다.
    END_DATE DATE, -- 이벤트 종료일: 이벤트가 종료되는 날짜를 저장합니다.
    CREATED_AT DATE DEFAULT SYSDATE, -- 이벤트 생성일: 이벤트가 처음 생성된 날짜를 기록합니다. 기본값은 현재 날짜입니다.
    UPDATED_AT DATE -- 이벤트 수정일: 이벤트 정보가 마지막으로 수정된 날짜를 기록합니다.
);

-- 18. 쿠폰 정보를 관리하는 테이블 (EVENT 테이블 참조)
CREATE TABLE COUPON (
    COUPON_NO NUMBER PRIMARY KEY, -- 쿠폰 NO: 각 쿠폰을 고유하게 식별하는 기본 키입니다.
    CODE VARCHAR2(50) UNIQUE NOT NULL, -- 쿠폰 코드: 사용자가 쿠폰을 사용할 때 입력하는 고유 코드입니다. 중복되지 않도록 설정합니다.
    DISCOUNT_AMOUNT NUMBER, -- 할인 금액: 쿠폰이 적용되었을 때 할인되는 금액을 저장합니다.
    DISCOUNT_PERCENT NUMBER, -- 할인율: 쿠폰이 적용되었을 때 할인되는 비율을 저장합니다. 예: 10% 할인.
    EXPIRY_DATE DATE, -- 만료일: 쿠폰의 유효기간을 설정합니다. 이 날짜 이후로는 쿠폰을 사용할 수 없습니다.
    IS_USED CHAR(1) DEFAULT 'N' CHECK (IS_USED IN ('Y', 'N')), -- 사용 여부: 쿠폰이 사용되었는지 여부를 기록합니다. 'Y'는 사용됨, 'N'은 사용되지 않음을 의미합니다.
    EVENT_NO NUMBER, -- 이벤트 NO: 이 쿠폰이 연관된 이벤트의 NO입니다. EVENT 테이블과의 외래 키입니다.
    CREATED_AT DATE DEFAULT SYSDATE, -- 쿠폰 생성일: 쿠폰이 생성된 날짜를 기록합니다. 기본값은 현재 날짜입니다.
    UPDATED_AT DATE, -- 쿠폰 수정일: 쿠폰 정보가 마지막으로 수정된 날짜를 기록합니다.
    FOREIGN KEY (EVENT_NO) REFERENCES EVENT(EVENT_NO) -- 외래 키: EVENT 테이블의 EVENT_NO를 참조합니다.
);

-- 19. 이메일 발송 기록을 관리하는 테이블 (COUPON 테이블 참조)
CREATE TABLE EMAIL_LOG (
    EMAIL_LOG_NO NUMBER PRIMARY KEY, -- 이메일 로그 NO: 각 이메일 발송 기록을 고유하게 식별하는 기본 키입니다.
    EMAIL_ADDRESS VARCHAR2(255) NOT NULL, -- 이메일 주소: 쿠폰이 발송된 이메일 주소를 저장합니다.
    COUPON_NO NUMBER, -- 쿠폰 NO: 발송된 쿠폰의 NO입니다. COUPON 테이블과의 외래 키입니다.
    SENT_AT DATE DEFAULT SYSDATE, -- 이메일 발송일: 이메일이 발송된 날짜를 기록합니다. 기본값은 현재 날짜입니다.
    STATUS VARCHAR2(50) DEFAULT 'SENT' CHECK (STATUS IN ('SENT', 'FAILED')), -- 발송 상태: 이메일 발송 상태를 기록합니다. 'SENT'는 성공적으로 발송되었음을, 'FAILED'는 실패했음을 의미합니다.
    ERROR_MESSAGE VARCHAR2(1000), -- 에러 메시지: 발송 실패 시 발생한 오류 메시지를 기록합니다.
    FOREIGN KEY (COUPON_NO) REFERENCES COUPON(COUPON_NO) -- 외래 키: COUPON 테이블의 COUPON_NO를 참조합니다.
);

-- 20. 사용자 쿠폰을 관리하는 테이블 (USER 및 COUPON 테이블 참조)
CREATE TABLE USER_COUPON (
    USER_COUPON_NO NUMBER PRIMARY KEY, -- 사용자 쿠폰 NO: 각 사용자 쿠폰을 고유하게 식별하는 기본 키입니다.
    USER_NO NUMBER, -- 사용자 NO: 쿠폰을 받은 사용자의 NO입니다. USER 테이블과의 외래 키입니다.
    COUPON_NO NUMBER, -- 쿠폰 NO: 사용자가 받은 쿠폰의 NO입니다. COUPON 테이블과의 외래 키입니다.
    RECEIVED_AT DATE DEFAULT SYSDATE, -- 쿠폰 수신일: 사용자가 쿠폰을 수신한 날짜를 기록합니다. 기본값은 현재 날짜입니다.
    IS_REDEEMED CHAR(1) DEFAULT 'N' CHECK (IS_REDEEMED IN ('Y', 'N')), -- 사용 여부: 사용자가 쿠폰을 사용했는지 여부를 기록합니다. 'Y'는 사용됨, 'N'은 사용되지 않음을 의미합니다.
    REDEEMED_AT DATE, -- 쿠폰 사용일: 사용자가 쿠폰을 사용한 날짜를 기록합니다.
    FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO), -- 외래 키: USER 테이블의 USER_NO를 참조합니다.
    FOREIGN KEY (COUPON_NO) REFERENCES COUPON(COUPON_NO) -- 외래 키: COUPON 테이블의 COUPON_NO를 참조합니다.
);

