package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Invite;
import ru.nsu.fit.battle_fw.database.model.User;

import java.util.List;

@RepositoryRestResource
public interface UserRoleRepo extends JpaRepository<Invite, Integer> {
    @Query("select u.role_id from UserRole u where u.user_id = :user_id")
    Integer getRoleByID(@Param("user_id") Integer user_id);
}
