package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "library_composition", schema = "public")
public class LibraryComp {
    @Id
    @Column(name = "id_card_lib")
    @SequenceGenerator(name = "libCompIdSeq", sequenceName = "lib_comp_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libCompIdSeq")
    private Integer id_card_lib;

    @Column(name = "id_library")
    private Integer id_library;

    @Column(name = "id_card")
    private Integer id_card;

    public Integer getId_card_lib() {
        return id_card_lib;
    }

    public void setId_card_lib(Integer id_card_lib) {
        this.id_card_lib = id_card_lib;
    }

    public Integer getId_library() {
        return id_library;
    }

    public void setId_library(Integer id_library) {
        this.id_library = id_library;
    }

    public Integer getId_card() {
        return id_card;
    }

    public void setId_card(Integer id_card) {
        this.id_card = id_card;
    }
}