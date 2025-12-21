DROP DATABASE IF EXISTS assetdb;
CREATE DATABASE assetdb;
USE assetdb;

CREATE TABLE `ob_users`
(
    `user_seq_no` VARCHAR(20) NOT NULL,
    `user_name`   VARCHAR(50) NOT NULL,
    `created_at`  DATETIME    NOT NULL,
    CONSTRAINT `PK_ob_users` PRIMARY KEY (`user_seq_no`)
);

CREATE TABLE `registered_accounts`
(
    `fintech_use_num`     VARCHAR(24) NOT NULL,
    `user_seq_no`         VARCHAR(20) NOT NULL,
    `bank_code`           VARCHAR(3)  NOT NULL COMMENT '097, 088 등',
    `bank_name`           VARCHAR(50) NOT NULL,
    `account_num`         VARCHAR(20) NOT NULL,
    `account_holder_name` VARCHAR(50) NOT NULL,
    `balance_amt`         BIGINT      NOT NULL DEFAULT 0,
    `available_amt`       BIGINT      NOT NULL DEFAULT 0,
    `created_at`          DATETIME    NOT NULL,
    CONSTRAINT `PK_registered_accounts` PRIMARY KEY (`fintech_use_num`),
    CONSTRAINT `FK_account_user` FOREIGN KEY (`user_seq_no`)
        REFERENCES `ob_users` (`user_seq_no`) ON DELETE CASCADE
);

CREATE TABLE `account_transactions`
(
    `tran_no`           BIGINT       NOT NULL AUTO_INCREMENT,
    `fintech_use_num`   VARCHAR(24)  NOT NULL,
    `tran_date`         DATETIME     NOT NULL,
    `inout_type`        ENUM('입금', '출금') NOT NULL,
    `tran_amt`          BIGINT       NOT NULL,
    `after_balance_amt` BIGINT       NOT NULL,
    `printed_content`   VARCHAR(100) NULL COMMENT '거래상대방',
    `created_at`        DATETIME     NOT NULL,
    CONSTRAINT `PK_transactions` PRIMARY KEY (`tran_no`),
    CONSTRAINT `FK_tran_account` FOREIGN KEY (`fintech_use_num`)
        REFERENCES `registered_accounts` (`fintech_use_num`) ON DELETE CASCADE
);

-- ========================================
-- 1. ob_users (사용자 5명)
-- ========================================
INSERT INTO ob_users (user_seq_no, user_name, created_at) VALUES
                                                              ('1100000001', '김철수', NOW()),
                                                              ('1100000002', '이영희', NOW()),
                                                              ('1100000003', '박민수', NOW()),
                                                              ('1100000004', '최지원', NOW()),
                                                              ('1100000005', 'DEV테스트', NOW());

-- ========================================
-- 2. registered_accounts (계좌 5개)
-- ========================================
INSERT INTO registered_accounts (
    fintech_use_num, user_seq_no, bank_code, bank_name,
    account_num, account_holder_name, balance_amt, available_amt, created_at
) VALUES
-- 김철수 - 국민은행
('a1b2c3d4e5f6g7h8i9j0k1l2', '1100000001', '097', '국민은행',
 '1234567890', '김철수', 5230000, 5230000, NOW()),

-- 이영희 - 신한은행
('b2c3d4e5f6g7h8i9j0k1l2m3', '1100000002', '088', '신한은행',
 '9876543210', '이영희', 12450000, 12450000, NOW()),

-- 박민수 - 하나은행
('c3d4e5f6g7h8i9j0k1l2m3n4', '1100000003', '081', '하나은행',
 '5555666677', '박민수', 890000, 890000, NOW()),

-- 최지원 - 우리은행
('d4e5f6g7h8i9j0k1l2m3n4o5', '1100000004', '020', '우리은행',
 '1111222233', '최지원', 18700000, 18700000, NOW()),

-- DEV테스트 - 기업은행
('dev_fintech_001', '1100000005', '003', '기업은행',
 '9999888877', 'DEV테스트', 10000000, 10000000, NOW());

-- ========================================
-- 3. account_transactions (각 계좌당 5건씩 = 총 25건)
-- ========================================

-- 김철수 계좌 거래내역 (5건)
INSERT INTO account_transactions (
    fintech_use_num, tran_date, inout_type, tran_amt,
    after_balance_amt, printed_content, created_at
) VALUES
      ('a1b2c3d4e5f6g7h8i9j0k1l2', '2025-12-20 14:30:00', '출금', 50000, 5230000, '스타벅스 강남점', NOW()),
      ('a1b2c3d4e5f6g7h8i9j0k1l2', '2025-12-20 10:15:00', '입금', 500000, 5280000, '월급', NOW()),
      ('a1b2c3d4e5f6g7h8i9j0k1l2', '2025-12-19 18:45:00', '출금', 85000, 4780000, '이마트', NOW()),
      ('a1b2c3d4e5f6g7h8i9j0k1l2', '2025-12-19 12:20:00', '출금', 12000, 4865000, '지하철', NOW()),
      ('a1b2c3d4e5f6g7h8i9j0k1l2', '2025-12-18 09:30:00', '입금', 100000, 4877000, '친구 송금', NOW());

-- 이영희 계좌 거래내역 (5건)
INSERT INTO account_transactions (
    fintech_use_num, tran_date, inout_type, tran_amt,
    after_balance_amt, printed_content, created_at
) VALUES
      ('b2c3d4e5f6g7h8i9j0k1l2m3', '2024-12-20 16:00:00', '출금', 150000, 12450000, '온라인쇼핑', NOW()),
      ('b2c3d4e5f6g7h8i9j0k1l2m3', '2024-12-20 11:00:00', '입금', 2000000, 12600000, '월급', NOW()),
      ('b2c3d4e5f6g7h8i9j0k1l2m3', '2024-12-19 20:30:00', '출금', 45000, 10600000, '저녁식사', NOW()),
      ('b2c3d4e5f6g7h8i9j0k1l2m3', '2024-12-19 15:10:00', '출금', 8000, 10645000, '편의점', NOW()),
      ('b2c3d4e5f6g7h8i9j0k1l2m3', '2024-12-18 13:20:00', '입금', 300000, 10653000, '상여금', NOW());

-- 박민수 계좌 거래내역 (5건)
INSERT INTO account_transactions (
    fintech_use_num, tran_date, inout_type, tran_amt,
    after_balance_amt, printed_content, created_at
) VALUES
      ('c3d4e5f6g7h8i9j0k1l2m3n4', '2024-12-20 17:45:00', '출금', 35000, 890000, '치킨', NOW()),
      ('c3d4e5f6g7h8i9j0k1l2m3n4', '2024-12-20 08:00:00', '입금', 50000, 925000, '용돈', NOW()),
      ('c3d4e5f6g7h8i9j0k1l2m3n4', '2024-12-19 21:00:00', '출금', 120000, 875000, 'CGV 영화', NOW()),
      ('c3d4e5f6g7h8i9j0k1l2m3n4', '2024-12-19 14:30:00', '출금', 28000, 995000, '점심', NOW()),
      ('c3d4e5f6g7h8i9j0k1l2m3n4', '2024-12-18 10:00:00', '입금', 800000, 1023000, '알바비', NOW());

-- 최지원 계좌 거래내역 (5건)
INSERT INTO account_transactions (
    fintech_use_num, tran_date, inout_type, tran_amt,
    after_balance_amt, printed_content, created_at
) VALUES
      ('d4e5f6g7h8i9j0k1l2m3n4o5', '2024-12-20 19:20:00', '출금', 200000, 18700000, '월세', NOW()),
      ('d4e5f6g7h8i9j0k1l2m3n4o5', '2024-12-20 15:00:00', '입금', 3000000, 18900000, '월급', NOW()),
      ('d4e5f6g7h8i9j0k1l2m3n4o5', '2024-12-19 17:30:00', '출금', 65000, 15900000, 'GS칼텍스', NOW()),
      ('d4e5f6g7h8i9j0k1l2m3n4o5', '2024-12-19 11:45:00', '출금', 95000, 15965000, '올리브영', NOW()),
      ('d4e5f6g7h8i9j0k1l2m3n4o5', '2024-12-18 14:00:00', '입금', 500000, 16060000, '보너스', NOW());

-- DEV테스트 계좌 거래내역 (5건)
INSERT INTO account_transactions (
    fintech_use_num, tran_date, inout_type, tran_amt,
    after_balance_amt, printed_content, created_at
) VALUES
      ('dev_fintech_001', '2024-12-20 12:00:00', '출금', 100000, 10000000, '테스트 출금', NOW()),
      ('dev_fintech_001', '2024-12-20 09:00:00', '입금', 1000000, 10100000, '테스트 입금', NOW()),
      ('dev_fintech_001', '2024-12-19 16:00:00', '출금', 50000, 9100000, '카페 테스트', NOW()),
      ('dev_fintech_001', '2024-12-19 10:00:00', '입금', 200000, 9150000, '송금 테스트', NOW()),
      ('dev_fintech_001', '2024-12-18 15:00:00', '출금', 80000, 8950000, '마트 테스트', NOW());