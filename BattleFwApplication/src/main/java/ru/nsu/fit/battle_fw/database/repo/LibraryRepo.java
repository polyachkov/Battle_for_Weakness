package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Library;
import ru.nsu.fit.battle_fw.database.model.LibraryComp;

import java.util.List;

@RepositoryRestResource
public interface LibraryRepo extends JpaRepository<Library, Integer> {
    @Query("select c.id_library from Library c where c.rarity = :rarity and c.game_id = :game_id and c.player_id = :player_id")
    Integer getLibId(@Param("player_id") Integer player_id, @Param("game_id") Integer game_id, @Param("rarity") String rarity);
}
