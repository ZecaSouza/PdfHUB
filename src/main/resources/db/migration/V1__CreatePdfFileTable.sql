CREATE TABLE pdf_file (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    file_data LONGBLOB NOT NULL,
    description VARCHAR(500),
    is_deleted BOOLEAN DEFAULT FALSE
);