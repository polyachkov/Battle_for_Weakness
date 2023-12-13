package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Hand;
import ru.nsu.fit.battle_fw.database.model.HandComp;

import java.util.*;

@RepositoryRestResource
public interface HandCompRepo extends JpaRepository<HandComp, Integer> {
    @Query("select h from HandComp h where h.id_hand = :id_hand and h.id_card = :id_card")
    List<HandComp> getHandCard(@Param("id_hand") Integer id_hand, @Param("id_card") Integer id_card);
}
