SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE roles;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO roles(id,name, create_date, last_update) VALUES
        (1,'ROLE_ADMIN', NOW(), NOW()),
        (2,'ROLE_USER', NOW(), NOW());
