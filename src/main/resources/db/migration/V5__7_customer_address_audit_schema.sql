CREATE TABLE customer_address_audit
(
    rev            INTEGER NOT NULL,
    revtype        SMALLINT,
    id             BIGINT  NOT NULL,
    street_address VARCHAR(255),
    city           VARCHAR(255),
    state_code     VARCHAR(255),
    country        VARCHAR(255),
    zip_code       VARCHAR(255),
    customer_id    BIGINT,
    CONSTRAINT pk_customer_address_audit PRIMARY KEY (rev, id)
);

ALTER TABLE customer_address_audit
    ADD CONSTRAINT FK_CUSTOMER_ADDRESS_AUDIT_ON_REV FOREIGN KEY (rev) REFERENCES revision_info (id);