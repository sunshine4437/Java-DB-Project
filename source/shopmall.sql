drop database if exists shopmallmanageDB;
create database shopmallmanageDB;
use shopmallmanageDB;
CREATE TABLE producttbl (
  productNo INT NOT NULL PRIMARY KEY,
  productName varchar(20) not null,
  productType varchar(10),
  cost INT NULL,
  price INT NULL,
  amount INT NOT NULL,
  receivedDate DATE
  );

CREATE TABLE membertbl (
  ID VARCHAR(20) NOT NULL PRIMARY KEY,
  phoneNum VARCHAR(20) NULL,
  address VARCHAR(45) NOT NULL,
  name VARCHAR(10) NOT NULL
  );


CREATE TABLE ordertbl (
  orderNo INT NOT NULL PRIMARY KEY Auto_Increment,
  ID VARCHAR(20) NOT NULL,
  productNo INT NOT NULL,
  orderAmount INT NOT NULL,
  orderDate DATE NOT NULL,
  totalPrice int,
  CONSTRAINT fk_order_product
    FOREIGN KEY (productNo)
    REFERENCES producttbl (productNo)
    ON UPDATE CASCADE,
  CONSTRAINT fk_order_member1
    FOREIGN KEY (ID)
    REFERENCES membertbl(ID)
   ON UPDATE CASCADE
);

-- 주문 추가시 totalprice 자동 계산
DROP TRIGGER IF EXISTS `shopmallmanagedb`.`order_BEFORE_INSERT`;
DELIMITER $$
USE `shopmallmanagedb`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `order_BEFORE_INSERT` BEFORE INSERT ON ordertbl FOR EACH ROW BEGIN
set NEW.totalprice = New.orderAmount *
(   select price
    from producttbl
    where productNo = NEW.productNo
);
END$$
DELIMITER ;

-- 주문 수정시 totalprice 자동 계산
DROP TRIGGER IF EXISTS `shopmallmanagedb`.`order_BEFORE_UPDATE`;
DELIMITER $$
USE `shopmallmanagedb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `shopmallmanagedb`.`order_BEFORE_UPDATE` BEFORE UPDATE ON ordertbl FOR EACH ROW
BEGIN
set NEW.totalprice = New.orderAmount *
(   select price
    from producttbl
    where productNo = NEW.productNo
);
END$$
DELIMITER ;

-- 주문 추가 시 재고 감소
DROP TRIGGER IF EXISTS `shopmallmanagedb`.`add_producttbl_amount`;
DELIMITER $$
USE `shopmallmanagedb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `shopmallmanagedb`.`add_producttbl_amount` BEFORE INSERT ON `ordertbl` FOR EACH ROW
BEGIN
update producttbl
set amount = amount - New.orderAmount
where productNo = New.productNo;
END$$
DELIMITER ;

-- 주문 수정 시 재고 변동
DROP TRIGGER IF EXISTS `shopmallmanagedb`.`update_producttbl_amount`;
DELIMITER $$
USE `shopmallmanagedb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `shopmallmanagedb`.`update_producttbl_amount` BEFORE UPDATE ON `ordertbl` FOR EACH ROW
BEGIN
update producttbl
set amount = amount + Old.orderAmount - New.orderAmount
where productNo = New.productNo;
END$$
DELIMITER ;

-- 주문 삭제 시 재고 복구
DROP TRIGGER IF EXISTS `shopmallmanagedb`.`delete_producttbl_amount`;
DELIMITER $$
USE `shopmallmanagedb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `shopmallmanagedb`.`delete_producttbl_amount` BEFORE DELETE ON `ordertbl` FOR EACH ROW
BEGIN
update producttbl
set amount = amount + Old.orderAmount
where productNo = Old.productNo;
END$$
DELIMITER ;