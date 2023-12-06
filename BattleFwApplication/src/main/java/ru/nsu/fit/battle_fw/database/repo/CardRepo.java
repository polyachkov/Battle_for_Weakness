package ru.nsu.fit.battle_fw.database.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.fit.battle_fw.database.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepo extends JpaRepository<Card, Integer> {
    @Query("select c from Card c where c.rarity = :rarity and c.fraction = :fraction")
    List<Card> getListOfCard(@Param("rarity") String rarity, @Param("fraction") String fraction);
}
