CREATE TABLE audience (
    audience_id VARCHAR(20) NOT NULL,
    audience_name VARCHAR(20) NOT NULL,
    age INTEGER CHECK (age >= 0), 
    address VARCHAR(30),
    membership_rank VARCHAR(10) DEFAULT 'silver' 
        CHECK (membership_rank IN ('silver', 'gold', 'vip')), 
    PRIMARY KEY (audience_id)
);

CREATE TABLE ticket (
    ticket_id VARCHAR(20) NOT NULL,
    movie_name VARCHAR(20) NOT NULL,
    screening_date DATE NOT NULL,
    price INTEGER DEFAULT 0,
    audience_id VARCHAR(20) NOT NULL,
    PRIMARY KEY (ticket_id),
    FOREIGN KEY (audience_id) REFERENCES audience(audience_id)
);

CREATE TABLE payment (
    payment_id VARCHAR(20) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    payment_date DATE NOT NULL,
    ticket_id VARCHAR(20) NOT NULL,
    PRIMARY KEY (payment_id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id)
);

-- 데이터 삽입
INSERT INTO audience (audience_id, audience_name, age, address, membership_rank)
VALUES 
('나른한', '박소영', 22, '대전광역시 서구', 'silver'),
('알사탕', '임유민', 22, NULL, 'gold'),
('시리프', '이지영', 21, '대전광역시 유성구', 'vip'),
('비타민', '김지현', 23, '대전광역시 유성구', 'silver'),
('부족', '홍현지', 22, NULL, 'gold');

INSERT INTO ticket (ticket_id, movie_name, screening_date, price, audience_id)
VALUES 
('leet1', '해리포터', '2022-10-13', 12000, '나른한'),
('leet2', '지영이의 우당탕탕', '2022-10-18', 10000, '알사탕'),
('leet3', '지현이의 그림자', '2022-10-23', 13000, '시리프'),
('leet4', '지영이과 지현이의 기묘한 모험', '2022-12-18', 11000, '비타민'),
('leet5', '요술램프 김지현', '2024-01-03', 12000, '부족');

INSERT INTO payment (payment_id, payment_method, payment_date, ticket_id)
VALUES 
('1546548', '신용카드', '2022-10-13', 'leet1'),
('6445153', '현금', '2022-10-18', 'leet2'),
('9465124', '계좌이체', '2022-10-23', 'leet3'),
('9512423', '신용카드', '2022-12-18', 'leet4'),
('8513452', '체크카드', '2024-01-03', 'leet5');
