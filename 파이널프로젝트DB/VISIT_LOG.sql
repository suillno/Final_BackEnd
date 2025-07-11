CREATE TABLE VISIT_LOG (
    ID          NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY 
                PRIMARY KEY,
    USER_ID     NVARCHAR2(10) NOT NULL,
    VISIT_DATE  DATE NOT NULL
);

-- 주석 (COMMENT)
COMMENT ON TABLE VISIT_LOG IS '하루 평균방문일 통계를 위한 테이블';
COMMENT ON COLUMN VISIT_LOG.ID IS '고유번호';
COMMENT ON COLUMN VISIT_LOG.USER_ID IS '내용 – 멤버 FK';
COMMENT ON COLUMN VISIT_LOG.VISIT_DATE IS '방문일 기록';
