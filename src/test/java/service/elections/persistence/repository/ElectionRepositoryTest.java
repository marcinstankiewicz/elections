package service.elections.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import service.elections.persistence.model.Election;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ElectionRepositoryTest {

    @Autowired
    private ElectionRepository repo;

    @Test
    void shouldSaveAndFindElection() {
        // given
        Election election = new Election();
        election.setName("Test election");

        // when
        Election saved = repo.save(election);
        Election found = repo.findById(saved.getId()).orElse(null);

        // then
        assertNotNull(saved.getId());
        assertNotNull(found);
        assertEquals("Test election", found.getName());
    }
}