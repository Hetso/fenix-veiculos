CREATE TABLE car
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    brand       VARCHAR(64)           NOT NULL,
    model       VARCHAR(120)          NOT NULL,
    `year`      INT(4)                NOT NULL,
    price       DECIMAL               NOT NULL,
    color       VARCHAR(64)           NOT NULL,
    description LONGTEXT,
    image_path  VARCHAR(240),
    CONSTRAINT car_pk PRIMARY KEY (id),
    CONSTRAINT car_uk1 UNIQUE KEY (brand, model, `year`, color)
);