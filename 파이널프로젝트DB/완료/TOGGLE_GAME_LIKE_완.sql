--------------------------------------------------------
--  파일이 생성됨 - 월요일-6월-30-2025   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Procedure TOGGLE_GAME_LIKE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "SCOTT"."TOGGLE_GAME_LIKE" (
    p_user_name         IN VARCHAR2,
    p_game_id           IN NUMBER,
    p_title        IN VARCHAR2,
    p_background_image  IN VARCHAR2,
    p_price             IN NUMBER,
    p_sale_price        IN NUMBER,
    p_result            OUT VARCHAR2
) AS
    v_count NUMBER;
BEGIN

    -- 0. 필수값 NULL 체크
    IF p_user_name IS NULL OR p_game_id IS NULL THEN
        p_result := 'ERROR: USER_NAME 또는 GAME_ID는 NULL일 수 없습니다.';
        RETURN;
    END IF;

    -- 1. 기존 존재 여부 확인
    SELECT COUNT(*)
    INTO v_count
    FROM GAME_LIKE
    WHERE USER_NAME = p_user_name AND GAME_ID = p_game_id;

    -- 2. 찜 등록 또는 삭제
    IF v_count = 0 THEN
        INSERT INTO GAME_LIKE (
            USER_NAME, GAME_ID, TITLE, BACKGROUND_IMAGE, PRICE, SALE_PRICE
        ) VALUES (
            p_user_name, p_game_id, p_title, p_background_image, p_price, p_sale_price
        );
        p_result := 'SUCCESS: 찜 등록이 완료되었습니다.';
    ELSE
        DELETE FROM GAME_LIKE
        WHERE USER_NAME = p_user_name AND GAME_ID = p_game_id;
        p_result := 'SUCCESS: 찜이 취소되었습니다.';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        -- SQLERRM	시스템 내장 함수 – 현재 발생한 예외의 에러 메시지를 반환
        p_result := 'ERROR: ' || SQLERRM;
END;

/
