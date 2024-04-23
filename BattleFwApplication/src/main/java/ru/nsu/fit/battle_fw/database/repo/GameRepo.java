package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Game;

import java.util.List;

@RepositoryRestResource
public interface GameRepo extends JpaRepository<Game, Integer> {
    @Query("select g from Game g where g.name_player1 = :name_player1 and g.name_player2 = :name_player2")
    Game getGame(@Param("name_player1") String name_player1, @Param("name_player2") String name_player2);

    @Query("select g from Game g where g.name_player1 = :name_player1 or g.name_player2 = :name_player1")
    List<Game> getAllGames(@Param("name_player1") String name_player1);
}
