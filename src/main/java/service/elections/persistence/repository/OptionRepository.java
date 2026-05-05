package service.elections.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.persistence.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {}