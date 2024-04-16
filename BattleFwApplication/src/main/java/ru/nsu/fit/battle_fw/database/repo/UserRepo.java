package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.battle_fw.database.model.Cell;
import ru.nsu.fit.battle_fw.database.model.Status;
import ru.nsu.fit.battle_fw.database.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("select u from User u")
    List<User> getAllUsers();
}
