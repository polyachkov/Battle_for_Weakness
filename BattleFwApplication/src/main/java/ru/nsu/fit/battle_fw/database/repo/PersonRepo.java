package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.fit.battle_fw.database.model.Cell;
import ru.nsu.fit.battle_fw.database.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PersonRepo extends JpaRepository<Person, Integer>{
    @Query("select c from Person c where c.name = :name")
    Person findByName(@Param("name") String name);
}
