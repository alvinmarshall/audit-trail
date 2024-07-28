CREATE SEQUENCE IF NOT EXISTS seq_revision_id START WITH 1 INCREMENT BY 50;

CREATE TABLE revision_info
(
    id            INTEGER NOT NULL,
    revision_date TIMESTAMP WITHOUT TIME ZONE,
    user_name     VARCHAR(255),
    CONSTRAINT pk_revision_info PRIMARY KEY (id)
);