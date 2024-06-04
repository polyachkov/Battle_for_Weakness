package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Library;

import java.util.List;

@RepositoryRestResource
public interface LibraryRepo extends JpaRepository<Library, Integer> {
    @Query("select c from Library c where c.rarity = :rarity and c.game_id = :game_id and c.player_name = :player_name")
    Library getLib(@Param("player_name") String player_name, @Param("game_id") Integer game_id, @Param("rarity") String rarity);

    @Query("select c from Library c where c.game_id = :game_id and c.player_name = :player_name")
    List<Library> getLibs(@Param("player_name") String player_name, @Param("game_id") Integer game_id);

    @Query("select c from Library c where c.game_id = :game_id")
    List<Library> getLibsByGame(@Param("game_id") Integer game_id);

}
