CREATE TABLE customer_address
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    street_address VARCHAR(255),
    city           VARCHAR(255),
    state_code     VARCHAR(255),
    country        VARCHAR(255),
    zip_code       VARCHAR(255),
    CONSTRAINT pk_customer_address PRIMARY KEY (id)
);