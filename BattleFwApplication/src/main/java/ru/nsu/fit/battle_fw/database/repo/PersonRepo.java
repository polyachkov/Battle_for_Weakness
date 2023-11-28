package ru.nsu.fit.battle_fw.database.repo;

import ru.nsu.fit.battle_fw.database.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PersonRepo extends JpaRepository<Person, Integer>{
}
