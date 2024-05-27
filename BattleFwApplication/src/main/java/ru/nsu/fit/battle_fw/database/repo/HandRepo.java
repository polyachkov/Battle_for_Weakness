package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Hand;

@RepositoryRestResource
public interface HandRepo extends JpaRepository<Hand, Integer> {
    @Query("select h from Hand h where h.id_game = :id_game and h.name_player = :name_player")
    Hand getHand(@Param("id_game") Integer id_game, @Param("name_player") String name_player);

    @Query("select h from Hand h where h.id_game = :id_game and h.name_player != :name_player")
    Hand getOppHand(@Param("id_game") Integer id_game, @Param("name_player") String name_player);
}
