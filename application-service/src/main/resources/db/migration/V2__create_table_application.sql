CREATE TABLE IF NOT EXISTS applications.applications
(
    id         SERIAL PRIMARY KEY,
    document_id   BIGINT not null,
    user_id   BIGINT not null,
    company_name varchar,
    status_application varchar,
    job_offer_name varchar
);



CREATE TABLE IF NOT EXISTS applications.documents
(
    id         SERIAL PRIMARY KEY,
    document_name varchar,
    content BYTEA,
    type_file varchar
);

ALTER TABLE applications.applications
ADD CONSTRAINT fk_application FOREIGN KEY(document_id) REFERENCES applications.documents(id);

