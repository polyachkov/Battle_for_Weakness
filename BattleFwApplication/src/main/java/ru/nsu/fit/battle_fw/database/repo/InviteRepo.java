package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Invite;

import java.util.List;

@RepositoryRestResource
public interface InviteRepo extends JpaRepository<Invite, Integer> {
    @Query("select i from Invite i where i.inviter_name = :inviter_name and i.invited_name = :invited_name")
    Invite getInvite(@Param("inviter_name") String inviter_name, @Param("invited_name") String invited_name);

    @Query("select i from Invite i where i.invited_name = :invited_name")
    List<Invite> getAllInvitesByInvitedName(@Param("invited_name") String invited_name);
}
