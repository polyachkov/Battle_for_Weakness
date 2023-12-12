package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Cell;

import java.util.List;

@RepositoryRestResource
public interface CellRepo extends JpaRepository<Cell, Integer> {
    @Query("select c from Cell c where c.id_game = :id_game and c.cell_num = :cell_num")
    Cell getCell(@Param("id_game") Integer id_game, @Param("cell_num") Integer cell_num);

    @Query("select c from Cell c where c.id_game = :id_game")
    List<Cell> getCells(@Param("id_game") Integer id_game);

}
