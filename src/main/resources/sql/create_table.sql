-- huobi.hkline definition

CREATE TABLE `hkline` (
                          `hid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                          `id` bigint(20) DEFAULT NULL,
                          `symbol` varchar(500) DEFAULT NULL,
                          `amount` decimal(40,10) DEFAULT NULL,
                          `count` decimal(20,10) DEFAULT NULL,
                          `open` decimal(20,10) DEFAULT NULL,
                          `high` decimal(20,10) DEFAULT NULL,
                          `low` decimal(20,10) DEFAULT NULL,
                          `close` decimal(20,10) DEFAULT NULL,
                          `vol` decimal(20,10) DEFAULT NULL,
                          PRIMARY KEY (`hid`),
                          UNIQUE KEY `idx_symbol` (`symbol`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1144 DEFAULT CHARSET=utf8 COMMENT='订单列表';


-- huobi.horder definition

CREATE TABLE `horder` (
                          `hid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                          `id` bigint(20) DEFAULT NULL,
                          `symbol` varchar(500) DEFAULT NULL,
                          `accountId` bigint(20) DEFAULT NULL,
                          `amount` decimal(40,10) DEFAULT NULL,
                          `price` decimal(40,10) DEFAULT NULL,
                          `type` varchar(500) DEFAULT NULL,
                          `filledAmount` decimal(40,10) DEFAULT NULL,
                          `filledCashAmount` decimal(40,10) DEFAULT NULL,
                          `filledFees` decimal(40,10) DEFAULT NULL,
                          `source` varchar(500) DEFAULT NULL,
                          `state` varchar(500) DEFAULT NULL,
                          `createdAt` bigint(20) DEFAULT NULL,
                          `canceledAt` bigint(20) DEFAULT NULL,
                          `finishedAt` bigint(20) DEFAULT NULL,
                          `stopPrice` decimal(40,10) DEFAULT NULL,
                          PRIMARY KEY (`hid`),
                          UNIQUE KEY `horder_id_IDX` (`id`) USING BTREE,
                          KEY `idx_symbol` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=347 DEFAULT CHARSET=utf8 COMMENT='订单列表';