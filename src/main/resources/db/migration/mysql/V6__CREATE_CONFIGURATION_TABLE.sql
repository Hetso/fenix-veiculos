CREATE TABLE configuration
(
    `key`        VARCHAR(64) NOT NULL,
    `value`      TEXT        NOT NULL,
    CONSTRAINT configuration_pk PRIMARY KEY (`key`)
);
