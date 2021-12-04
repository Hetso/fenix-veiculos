ALTER TABLE car_image DROP FOREIGN KEY car_image_car_id_fk;
ALTER TABLE car_image ADD CONSTRAINT car_image_car_id_fk FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE ON UPDATE CASCADE;
