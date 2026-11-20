CREATE DATABASE IF NOT EXISTS parking_lot CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE parking_lot;

CREATE TABLE IF NOT EXISTS parking_spots (
  id       BIGINT       NOT NULL AUTO_INCREMENT,
  code     VARCHAR(255) NOT NULL,
  type     VARCHAR(255) NOT NULL,
  occupied BIT(1)       NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS parking_tickets (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  spot_id    BIGINT       NOT NULL,
  plate      VARCHAR(255) NOT NULL,
  entry_time DATETIME(6)  NOT NULL,
  exit_time  DATETIME(6)  NULL,
  amount     BIGINT       NOT NULL,
  status     VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_tickets_spot FOREIGN KEY (spot_id) REFERENCES parking_spots (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO parking_spots (id,code,type,occupied) VALUES
 (1,'C-01','CAR',1),(2,'C-02','CAR',1),(3,'C-03','CAR',0),(4,'C-04','CAR',0),(5,'C-05','CAR',0),(6,'C-06','CAR',0),
 (7,'M-01','MOTORCYCLE',1),(8,'M-02','MOTORCYCLE',0),(9,'M-03','MOTORCYCLE',0),(10,'M-04','MOTORCYCLE',0);

INSERT IGNORE INTO parking_tickets (id,spot_id,plate,entry_time,exit_time,amount,status) VALUES
 (1,1,'ABC123','2026-11-10 08:15:00',NULL,0,'ACTIVE'),
 (2,2,'XYZ789','2026-11-10 09:40:00',NULL,0,'ACTIVE'),
 (3,7,'MNO45A','2026-11-10 10:05:00',NULL,0,'ACTIVE'),
 (4,3,'DEF456','2026-11-10 07:00:00','2026-11-10 09:30:00',9000,'CLOSED'),
 (5,8,'QRS12B','2026-11-10 08:00:00','2026-11-10 08:50:00',1500,'CLOSED'),
 (6,4,'GHI321','2026-11-09 17:00:00','2026-11-09 20:10:00',12000,'CLOSED');
