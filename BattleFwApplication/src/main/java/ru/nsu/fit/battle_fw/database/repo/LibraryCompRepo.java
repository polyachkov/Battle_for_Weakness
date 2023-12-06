package ru.nsu.fit.battle_fw.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.nsu.fit.battle_fw.database.model.Card;
import ru.nsu.fit.battle_fw.database.model.LibraryComp;

import java.util.List;

@RepositoryRestResource
public interface LibraryCompRepo extends JpaRepository<LibraryComp, Integer> {
    @Query("select c1 from " +
                "(select min(c.id_card_lib) as id from LibraryComp c " +
                "where c.id_library = :id_library) i, " +
                "LibraryComp c1 " +
            "where c1.id_card_lib = i.id")
    LibraryComp getMinCard(@Param("id_library") Integer id_library);
}
