CREATE TABLE IF NOT EXISTS `money_transfer_app_db`.`user`(
	`user_id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`username` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NOT NULL,
	`age` INT,
	`role` VARCHAR(20) NOT NULL,
	`creation_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT `check_role` CHECK(`role` IN('admin','customer'))
);

CREATE TABLE IF NOT EXISTS `account`(
	`account_id` INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`balance` DOUBLE NOT NULL DEFAULT 0,
	`user_id` INT,
	CONSTRAINT `user_acc_fk` FOREIGN KEY(`user_id`) REFERENCES user(`user_id`)
);

