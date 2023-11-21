package nsu.ru.BattleForWeakness.repo;

import nsu.ru.BattleForWeakness.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PersonRepo extends JpaRepository<Person, Integer>{
}
