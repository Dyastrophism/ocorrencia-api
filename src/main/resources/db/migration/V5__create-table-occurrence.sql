CREATE TYPE occurrence_status AS ENUM ('ACTIVE', 'FINISHED');

CREATE TABLE IF NOT EXISTS occurrences (
    id BIGSERIAL PRIMARY KEY,
    id_client BIGINT NOT NULL,
    id_address BIGINT NOT NULL,
    occurrence_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    occurrence_status occurrence_status NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES clients(id) ON DELETE CASCADE,
    CONSTRAINT fk_address FOREIGN KEY (id_address) REFERENCES address(id)
);

CREATE TABLE IF NOT EXISTS occurrence_photos (
    id BIGSERIAL PRIMARY KEY,
    id_occurrence BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    path_bucket VARCHAR(100) NOT NULL,
    hash VARCHAR(255) NOT NULL,
    CONSTRAINT fk_occurrence FOREIGN KEY (id_occurrence) REFERENCES occurrences(id) ON DELETE CASCADE
);