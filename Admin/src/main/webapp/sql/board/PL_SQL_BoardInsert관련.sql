-- 아래 insert 구문은 board게시글을 테스트하기 위해 필요한 준비물입니다.

-- 1. USERS 테이블에 회원과 관리자를 추가
INSERT INTO USERS (USER_NO, USER_ID, PASSWORD, NAME, EMAIL, USER_TYPE, IS_DELETED, CREATED_AT, UPDATED_AT)
VALUES (1, 'test1', '1234', 'John Doe', 'test1@example.com', 'CUSTOMER', 'N', SYSDATE, SYSDATE);

INSERT INTO USERS (USER_NO, USER_ID, PASSWORD, NAME, EMAIL, USER_TYPE, IS_DELETED, CREATED_AT, UPDATED_AT)
VALUES (4, 'admin', '1234', 'Admin User', 'admin@example.com', 'ADMIN', 'N', SYSDATE, SYSDATE);

-- 2. CUSTOMER 테이블에 회원 정보를 추가
INSERT INTO CUSTOMER (USER_NO, AGE, JOB, LOCATION, MILEAGE, LAST_LOGIN_DATE, TOTAL_PURCHASE_AMOUNT)
VALUES (1, 30, 'Software Developer', 'New York', 1000, SYSDATE, 5000);

-- 3. ADMIN_ROLE 테이블에 관리자 역할을 추가
INSERT INTO ADMIN_ROLE (ROLE_CODE, ROLE_NAME)
VALUES ('ADMIN001', '최고 관리자');

-- 4. ADMIN 테이블에 관리자 정보를 추가
INSERT INTO ADMIN (USER_NO, ROLE_CODE)
VALUES (4, 'ADMIN001');


commit;

-- 해당 프로시저는 유저번호 4번인 유저가 게시글 100개를 작성하는 프로시저입니다.
-- 아래 프로시저를 사용하기 위한 조건으로 유저 번호와 카테고리 코드가 존재해야합니다.


DECLARE
    v_user_no NUMBER := 4;
    v_category_code VARCHAR2(10);
    v_title VARCHAR2(100);
    v_content CLOB;
    
    -- 랜덤 한글 문자열 생성 함수
    FUNCTION random_korean_string(p_length IN NUMBER) RETURN VARCHAR2 IS
        v_result VARCHAR2(1000);
        v_choseong NUMBER;
        v_jungseong NUMBER;
        v_jongseong NUMBER;
        v_korean_char NUMBER;
    BEGIN
        FOR i IN 1..p_length LOOP
            -- 초성, 중성, 종성 랜덤 선택
            v_choseong := TRUNC(DBMS_RANDOM.VALUE(0, 19));
            v_jungseong := TRUNC(DBMS_RANDOM.VALUE(0, 21));
            v_jongseong := TRUNC(DBMS_RANDOM.VALUE(0, 28));
            
            -- 유니코드 한글 계산
            v_korean_char := 44032 + (v_choseong * 588) + (v_jungseong * 28) + v_jongseong;
            
            -- 결과 문자열에 추가
            v_result := v_result || CHR(v_korean_char);
        END LOOP;
        RETURN v_result;
    END;
BEGIN
    FOR i IN 1..100 LOOP
        -- 카테고리 코드 랜덤 선택
        v_category_code := CASE MOD(i, 2) 
                             WHEN 0 THEN 'B.C1000' 
                             ELSE 'B.C1010' 
                           END;
        
        -- 랜덤 제목 생성 (4~5자)
        v_title := random_korean_string(TRUNC(DBMS_RANDOM.VALUE(4, 6)));
        
        -- 디버깅을 위한 로그 출력
        DBMS_OUTPUT.PUT_LINE('Generated Title: ' || v_title);
        
        -- 랜덤 내용 생성 (10줄 이상)
        v_content := '';
        FOR j IN 1..10 LOOP
            v_content := v_content || random_korean_string(30) || CHR(10); -- 한 줄에 30자
        END LOOP;
        
        -- 데이터 삽입
        INSERT INTO board (BOARD_NO, USER_NO, CATEGORY_NO, TITLE, CONTENT, VIEWS, CREATED_AT, UPDATED_AT, IS_DELETED)
        VALUES (seq_board_no.nextval, v_user_no, v_category_code, v_title, v_content, DEFAULT ,sysdate, NULL, 'N');
    END LOOP;
    
    -- 트랜잭션 커밋
    COMMIT;
END;
/


-- 현재 위에 있는 코드 내용중 한글이 깨지는 문제가 발생. 임시로 아래 영문으로 작성하는걸로 대체

DECLARE
    v_user_no NUMBER := 4;
    v_category_code VARCHAR2(10);
    v_title VARCHAR2(100);
    v_content CLOB;
    
    -- 랜덤 영어 문자열 생성 함수
    FUNCTION random_english_string(p_length IN NUMBER) RETURN VARCHAR2 IS
        v_result VARCHAR2(1000) := '';
    BEGIN
        FOR i IN 1..p_length LOOP
            -- 랜덤 영어 문자 생성 (a~z)
            v_result := v_result || CHR(TRUNC(DBMS_RANDOM.VALUE(97, 123)));
        END LOOP;
        RETURN v_result;
    END;
BEGIN
    FOR i IN 1..100 LOOP
        -- 카테고리 코드 랜덤 선택
        v_category_code := CASE MOD(i, 2) 
                             WHEN 0 THEN 'B.C1000' 
                             ELSE 'B.C1010' 
                           END;
        
        -- 랜덤 제목 생성 (4~5자)
        v_title := random_english_string(TRUNC(DBMS_RANDOM.VALUE(4, 6)));
        
        -- 랜덤 내용 생성 (10줄 이상)
        v_content := '';
        FOR j IN 1..10 LOOP
            v_content := v_content || random_english_string(30) || CHR(10); -- 한 줄에 30자
        END LOOP;
        
        -- TITLE 또는 CONTENT가 NULL일 경우 기본값을 할당
        IF v_title IS NULL OR LENGTH(v_title) = 0 THEN
            v_title := 'Default Title';  -- 기본 제목 설정
        END IF;
        
        IF v_content IS NULL OR LENGTH(v_content) = 0 THEN
            v_content := 'Default Content';  -- 기본 내용 설정
        END IF;
        
        -- 데이터 삽입
        INSERT INTO board (BOARD_NO, USER_NO, CATEGORY_NO, TITLE, CONTENT, VIEWS, CREATED_AT, UPDATED_AT, IS_DELETED)
        VALUES (seq_board_no.nextval, v_user_no, v_category_code, v_title, v_content, DEFAULT ,sysdate, NULL, 'N');
    END LOOP;
    
    -- 트랜잭션 커밋
    COMMIT;
END;
/



