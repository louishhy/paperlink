package com.louishhy.paperlinkbackend.repository;

import com.louishhy.paperlinkbackend.model.Paper;
import com.louishhy.paperlinkbackend.model.Reference;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReferenceRepositoryTest {
    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17");

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
    void testSaveAndFindAllReferences() {
        // Arrange: Create two papers and save them to the database
        Paper citingPaper = new Paper();
        citingPaper.setTitle("Citing Paper");
        citingPaper.setAuthors("Author C");
        citingPaper.setPublicationType("Journal");
        paperRepository.save(citingPaper);

        Paper referencedPaper = new Paper();
        referencedPaper.setTitle("Referenced Paper");
        referencedPaper.setAuthors("Author D");
        referencedPaper.setPublicationType("Conference");
        paperRepository.save(referencedPaper);

        // Create a reference from citingPaper to referencedPaper
        Reference reference = new Reference();
        reference.setCitingPaper(citingPaper);
        reference.setReferencedPaper(referencedPaper);
        referenceRepository.save(reference);

        // Act: Retrieve all references
        List<Reference> references = referenceRepository.findAll();

        // Assert: Check that the reference was saved and retrieved correctly
        assertThat(references).hasSize(1);
        assertThat(references.get(0).getCitingPaper().getTitle()).isEqualTo("Citing Paper");
        assertThat(references.get(0).getReferencedPaper().getTitle()).isEqualTo("Referenced Paper");
    }
}
