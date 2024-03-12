package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Invites;

import java.util.List;

@RepositoryRestResource
public interface InvitesRepo extends JpaRepository<Invites, Integer> {
    @Query("select i from Invites i where i.id_player2 = :id_player2")
    List<Invites> getInvites(@Param("id_player2") Integer id_player2);
}
