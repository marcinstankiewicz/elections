package service.elections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.elections.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {}