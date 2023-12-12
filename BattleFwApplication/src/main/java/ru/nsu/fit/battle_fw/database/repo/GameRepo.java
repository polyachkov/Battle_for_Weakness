package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Game;
import ru.nsu.fit.battle_fw.database.model.HandComp;

import java.util.List;

@RepositoryRestResource
public interface GameRepo extends JpaRepository<Game, Integer> {
    @Query("select g from Game g where g.id_player1 = :id_player1 and g.id_player2 = :id_player2")
    Game getGame(@Param("id_player1") Integer id_player1, @Param("id_player2") Integer id_player2);
}
