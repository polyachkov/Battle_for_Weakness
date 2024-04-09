package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Invite;

import java.util.List;

@RepositoryRestResource
public interface InviteRepo extends JpaRepository<Invite, Integer> {
    @Query("select i from Invite i where i.inviter_id = :inviter_id and i.invited_id = :invited_id")
    Invite getInvite(@Param("inviter_id") Integer inviter_id, @Param("invited_id") Integer invited_id);
}
