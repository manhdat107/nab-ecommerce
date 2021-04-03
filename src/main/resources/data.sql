SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE roles;
TRUNCATE TABLE branch;
TRUNCATE TABLE product;
TRUNCATE TABLE user;
TRUNCATE TABLE user_roles;
SET FOREIGN_KEY_CHECKS = 1;

-- role default
INSERT INTO roles(id,name, create_date, last_update) VALUES
        (1,'ROLE_ADMIN', NOW(), NOW()),
        (2,'ROLE_USER', NOW(), NOW());

-- branch default
INSERT INTO `branch` (`create_date`, `last_update`,`description`, `name`) VALUES
        (NOW(), NOW(),'Gucci branch', 'GUCCI'),
        (NOW(), NOW(),'Nike branch', 'NIKE'),
        (NOW(), NOW(),'Adidas branch', 'ADIDAS');

-- product default
INSERT INTO `product` (`create_date`, `last_update`,`color`, `name`, `price`, `branch_id`) VALUES (NOW(), NOW(), 'RED', 'gucci product', '6000', '1');
INSERT INTO `product` (`create_date`, `last_update`,`color`, `name`, `price`, `branch_id`) VALUES (NOW(), NOW(), 'BLACK', 'Nike Product', '9000', '2');
INSERT INTO `product` (`create_date`, `last_update`,`color`, `name`, `price`, `branch_id`) VALUES (NOW(), NOW(), 'YELLOW', 'Adidas Product', '12300', '3');


-- product quantity default
INSERT INTO `sql6402572`.`quantity` (`create_date`, `last_update`,`quantity`, `product_id`) VALUES (NOW(), NOW(),'99', '1');
INSERT INTO `sql6402572`.`quantity` (`create_date`, `last_update`,`quantity`, `product_id`) VALUES (NOW(), NOW(),'50', '2');
INSERT INTO `sql6402572`.`quantity` (`create_date`, `last_update`,`quantity`, `product_id`) VALUES (NOW(), NOW(),'20', '3');


-- user default
INSERT INTO `user` (`id`,`create_date`, `last_update`, `address`, `full_name`, `password`, `phone_number`, `username`, `email`)
VALUES (1, NOW(), NOW(), 'sai gon', 'vu manh dat', '$2a$10$Fl2s8H6viDzCmV3HTREd6.krQ0AHnBIw9KUVs6aLjyygAYAOt.FXq', 1111, 'admin','admin@admin.com');

INSERT INTO `user` (`id`,`create_date`, `last_update`, `address`, `full_name`, `password`, `phone_number`, `username`, `email`)
VALUES (2, NOW(), NOW(), 'sai gon', 'vu manh dat-user', '$2a$10$JSnx8cQFJYPSnJqNf.FM8.ECOBNFccYd9LGsHjyzgUhbnCdDQYqdO', 123, 'user','user@user.com');


-- user role default
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES (1,1), (2,2);


