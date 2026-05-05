package service.elections.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import service.elections.persistence.model.Election;
import service.elections.persistence.model.Option;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OptionRepositoryTest {

    @Autowired
    private OptionRepository repo;

    @Autowired
    private ElectionRepository electionRepo;

    @Test
    void shouldSaveOptionWithElection() {
        Election election = new Election();
        election.setName("Election");

        Election savedElection = electionRepo.save(election);

        Option option = new Option();
        option.setName("Option A");
        option.setElection(savedElection);

        Option saved = repo.save(option);

        assertNotNull(saved.getId());
        assertEquals("Option A", saved.getName());
        assertEquals(savedElection.getId(), saved.getElection().getId());
    }
}