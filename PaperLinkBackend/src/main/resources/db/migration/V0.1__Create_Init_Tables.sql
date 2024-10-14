CREATE TABLE paper (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(1024) NOT NULL,
    -- authors are stored as plain texts to adapt to the long author list in some fields.
    authors TEXT NOT NULL,
    -- conference, journal, arxiv etc.
    publication_type VARCHAR(50) NOT NULL,
    publication_name VARCHAR(255),
    publication_date DATE,
    abstract TEXT,
    doi VARCHAR(255)
);

CREATE TABLE reference (
    id BIGSERIAL PRIMARY KEY,
    citing_paper_id BIGINT,
    referenced_paper_id BIGINT,
    -- constraints
    CONSTRAINT fk_reference_citing_paper FOREIGN KEY (citing_paper_id) REFERENCES paper(id) ON DELETE CASCADE,
    CONSTRAINT fk_reference_referenced_paper_id FOREIGN KEY (referenced_paper_id) REFERENCES paper(id) ON DELETE CASCADE
);