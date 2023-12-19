package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Status;

@RepositoryRestResource
public interface StatusRepo extends JpaRepository<Status, Integer> {
    @Query("select s from Status s where s.id_game = :id_game and s.id_player = :id_player")
    Status getStatus(@Param("id_game") Integer id_game, @Param("id_player") Integer id_player);
}
