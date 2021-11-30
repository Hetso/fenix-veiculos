CREATE TABLE car_brand
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name       	VARCHAR(64)           NOT NULL,
    CONSTRAINT car_brand_pk PRIMARY KEY (id),
    CONSTRAINT car_brand_uk1 UNIQUE KEY (name)
);

ALTER TABLE car MODIFY COLUMN brand BIGINT NOT NULL;
ALTER TABLE car ADD CONSTRAINT brand_car_fk FOREIGN KEY (brand) REFERENCES car_brand(id);
ALTER TABLE car CHANGE brand brand_id bigint(20) NOT NULL;
