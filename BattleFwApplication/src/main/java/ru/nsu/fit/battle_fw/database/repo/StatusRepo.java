package ru.nsu.fit.battle_fw.database.repo;

import ru.nsu.fit.battle_fw.database.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StatusRepo extends JpaRepository<Card, Integer> {
}
