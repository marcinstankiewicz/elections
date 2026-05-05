package service.elections.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import service.elections.persistence.model.Voter;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VoterRepositoryTest {

    @Autowired
    private VoterRepository repo;

    @Test
    void shouldSaveVoter() {
        Voter voter = new Voter();
        voter.setName("Jan");
        voter.setBlocked(false);

        Voter saved = repo.save(voter);

        assertNotNull(saved.getId());
        assertEquals("Jan", saved.getName());
        assertFalse(saved.isBlocked());
    }
}