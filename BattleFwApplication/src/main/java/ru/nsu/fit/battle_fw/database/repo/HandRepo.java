package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Card;
import ru.nsu.fit.battle_fw.database.model.Hand;

import java.util.List;

@RepositoryRestResource
public interface HandRepo extends JpaRepository<Hand, Integer> {
    @Query("select h from Hand h where h.id_game = :id_game and h.id_player = :id_player")
    Hand getHand(@Param("id_game") Integer id_game, @Param("id_player") Integer id_player);
}
