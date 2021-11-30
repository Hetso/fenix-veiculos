CREATE TABLE car_image
(
    car_id     BIGINT       NOT NULL,
    image_path TEXT 	NOT NULL,
    CONSTRAINT car_image_car_id_fk FOREIGN KEY (car_id)
        REFERENCES car (id)
);

ALTER TABLE car CHANGE image_path image_cover TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
ALTER TABLE car MODIFY COLUMN image_cover TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
