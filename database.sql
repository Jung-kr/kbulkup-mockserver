DROP DATABASE IF EXISTS assetdb;
CREATE DATABASE assetdb;
USE assetdb;

-- portfolios
CREATE TABLE `portfolios`
(
    `portfolio_id`    BIGINT                                                        NOT NULL AUTO_INCREMENT,
    `user_id`         BIGINT                                                        NOT NULL,
    `fintech_use_num` VARCHAR(100)                                                  NOT NULL,
    `bank_code`       ENUM ('국민은행','신한은행','기업은행','농협은행','우리은행','하나은행') NULL,
    `account_number`  VARCHAR(50)                                                   NULL,
    `created_at`      DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `PK_portfolios` PRIMARY KEY (`portfolio_id`),
    CONSTRAINT `UQ_portfolios_user` UNIQUE (`user_id`),
    CONSTRAINT `UQ_portfolios_fintech` UNIQUE (`fintech_use_num`)
);

-- snapshot_pools : 복합 PK(계좌+일시)
CREATE TABLE `snapshot_pools`
(
    `user_id`       BIGINT   NOT NULL,
    `balance`       BIGINT   NOT NULL,
    `snapshot_date` DATETIME NOT NULL,
    CONSTRAINT `PK_snapshot_pools` PRIMARY KEY (`user_id`, `snapshot_date`),
    CONSTRAINT `FK_snapshot_fintech`
        FOREIGN KEY (`user_id`) REFERENCES `portfolios` (`user_id`)
            ON UPDATE CASCADE ON DELETE CASCADE
);

-- composition_pools : 1계좌 1행이면 단일 PK 유지
CREATE TABLE `composition_pools`
(
    `user_id`           BIGINT NOT NULL,
    `asset_composition` JSON   NOT NULL,
    CONSTRAINT `PK_composition_pools` PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_composition_fintech`
        FOREIGN KEY (`user_id`) REFERENCES `portfolios` (`user_id`)
            ON UPDATE CASCADE ON DELETE CASCADE
);

-- transaction_pools : 거래 ID PK
CREATE TABLE `transaction_pools`
(
    `transaction_id`       BIGINT                                                                                         NOT NULL AUTO_INCREMENT,
    `user_id`              BIGINT                                                                                         NOT NULL,
    `transaction_type`     ENUM ('입금','출금')                                                                             NOT NULL,
    `amount`               BIGINT                                                                                         NOT NULL,
    `transaction_category` ENUM ('식비','교통비','주거/공과금','생필품', '의료/건강','패션/미용','문화생활/여가','기타', '월급','부수입') NOT NULL,
    `tran_date`            DATETIME                                                                                       NOT NULL,
    CONSTRAINT `PK_transaction_pools` PRIMARY KEY (`transaction_id`),
    CONSTRAINT `FK_transaction_fintech` FOREIGN KEY (`user_id`) REFERENCES `portfolios` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO portfolios (user_id, fintech_use_num)
VALUES
    (1, '1'),
    (2, '2'),
    (3, '3'),
    (4, '4'),
    (5, '5'),
    (6, '6'),
    (7, '7'),
    (8, '8'),
    (9, '9'),
    (10, '10'),
    (11, '11'),
    (12, '12'),
    (13, '13'),
    (14, '14'),
    (15, '15'),
    (16, '16'),
    (17, '17'),
    (18, '18'),
    (19, '19'),
    (20, '20'),
    (21, '21'),
    (22, '22'),
    (23, '23'),
    (24, '24'),
    (25, '25'),
    (26, '26'),
    (27, '27'),
    (28, '28'),
    (29, '29'),
    (30, '30'),
    (31, '31'),
    (32, '32'),
    (33, '33'),
    (34, '34'),
    (35, '35'),
    (36, '36'),
    (37, '37'),
    (38, '38'),
    (39, '39'),
    (40, '40'),
    (41, '41'),
    (42, '42'),
    (43, '43'),
    (44, '44'),
    (45, '45'),
    (46, '46'),
    (47, '47'),
    (48, '48'),
    (49, '49'),
    (50, '50'),
    (51, '51'),
    (52, '52'),
    (53, '53'),
    (54, '54'),
    (55, '55'),
    (56, '56'),
    (57, '57'),
    (58, '58'),
    (59, '59'),
    (60, '60'),
    (61, '61'),
    (62, '62'),
    (63, '63'),
    (64, '64'),
    (65, '65'),
    (66, '66'),
    (67, '67'),
    (68, '68'),
    (69, '69'),
    (70, '70'),
    (71, '71'),
    (72, '72'),
    (73, '73'),
    (74, '74'),
    (75, '75'),
    (76, '76'),
    (77, '77'),
    (78, '78'),
    (79, '79'),
    (80, '80'),
    (81, '81'),
    (82, '82'),
    (83, '83'),
    (84, '84'),
    (85, '85'),
    (86, '86'),
    (87, '87'),
    (88, '88'),
    (89, '89'),
    (90, '90'),
    (91, '91'),
    (92, '92'),
    (93, '93'),
    (94, '94'),
    (95, '95'),
    (96, '96'),
    (97, '97'),
    (98, '98'),
    (99, '99'),
    (100, '100');
