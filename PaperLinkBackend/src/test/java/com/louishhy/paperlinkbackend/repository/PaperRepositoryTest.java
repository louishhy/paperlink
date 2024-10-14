package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.Paper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Prevents Spring from using an embedded DB
public class PaperRepositoryTest {
    @Autowired
    private PaperRepository paperRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("paperlinktestdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.flyway.user", postgreSQLContainer::getUsername);
        registry.add("spring.flyway.password", postgreSQLContainer::getPassword);
    }


    @Test
    void testSaveAndFindAllPapers() {
        Paper paper1 = new Paper();
        paper1.setTitle("Test Paper 1");
        paper1.setAuthors("Author A");
        paper1.setPublicationType("Journal");
        paperRepository.save(paper1);

        Paper paper2 = new Paper();
        paper2.setTitle("Test Paper 2");
        paper2.setAuthors("Author B");
        paper2.setPublicationType("Conference");
        paperRepository.save(paper2);

        // Act: Retrieve all papers
        List<Paper> papers = paperRepository.findAll();

        // Assert: Check that papers were saved and retrieved correctly
        assertThat(papers).hasSize(2);
        assertThat(papers.get(0).getTitle()).isEqualTo("Test Paper 1");
        assertThat(papers.get(1).getTitle()).isEqualTo("Test Paper 2");
    }
}
