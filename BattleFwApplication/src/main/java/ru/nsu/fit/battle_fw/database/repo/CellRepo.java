package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Cell;

@RepositoryRestResource
public interface CellRepo extends JpaRepository<Cell, Integer> {
}
