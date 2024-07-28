CREATE TABLE customers_audit
(
    rev               INTEGER NOT NULL,
    revtype           SMALLINT,
    id                BIGINT  NOT NULL,
    first_name        VARCHAR(100),
    first_name_mod    BOOLEAN,
    last_name         VARCHAR(100),
    last_name_mod     BOOLEAN,
    email_address     VARCHAR(255),
    email_address_mod BOOLEAN,
    addresses_mod     BOOLEAN,
    created_by        VARCHAR(255),
    created_by_mod    BOOLEAN,
    updated_by        VARCHAR(255),
    updated_by_mod    BOOLEAN,
    created_on        TIMESTAMP WITHOUT TIME ZONE,
    created_on_mod    BOOLEAN,
    updated_on        TIMESTAMP WITHOUT TIME ZONE,
    updated_on_mod    BOOLEAN,
    CONSTRAINT pk_customers_audit PRIMARY KEY (rev, id)
);

ALTER TABLE customers_audit
    ADD CONSTRAINT FK_CUSTOMERS_AUDIT_ON_REV FOREIGN KEY (rev) REFERENCES revision_info (id);