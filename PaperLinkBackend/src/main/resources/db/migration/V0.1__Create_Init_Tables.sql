CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    hashed_password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED') DEFAULT 'ACTIVE',
    INDEX idx_email (email),
    INDEX idx_username (username)
);

CREATE TABLE paper (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    doi VARCHAR(255) NOT NULL UNIQUE,
    paper_abstract TEXT NOT NULL,
    publication VARCHAR(255) NOT NULL,
    url VARCHAR(500)
);

CREATE TABLE graph (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE graph_node (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    graph_id BIGINT NOT NULL,
    paper_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    FOREIGN KEY (graph_id) REFERENCES graph(id) ON DELETE CASCADE,
    FOREIGN KEY (paper_id) REFERENCES paper(id) ON DELETE CASCADE,
    UNIQUE KEY uk_graph_paper (graph_id, paper_id), -- Cannot have duplicated paper inside a graph
    INDEX idx_graph_id (graph_id)
);

CREATE TABLE graph_edge (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    graph_id BIGINT NOT NULL,
    citing_paper_id BIGINT NOT NULL,
    cited_paper_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    FOREIGN KEY (graph_id) REFERENCES graph(id) ON DELETE CASCADE,
    FOREIGN KEY (citing_paper_id) REFERENCES paper(id) ON DELETE CASCADE,
    FOREIGN KEY (cited_paper_id) REFERENCES paper(id) ON DELETE CASCADE,
    INDEX idx_graph_id (graph_id) -- Acceleration for graph-based check
);