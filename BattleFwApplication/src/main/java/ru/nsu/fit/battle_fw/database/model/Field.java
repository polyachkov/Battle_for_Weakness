package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "field", schema = "public")
public class Field {
    @Id
    @Column(name = "id_field")
    @SequenceGenerator(name = "fieldsIdSeq", sequenceName = "field_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fieldsIdSeq")
    private Integer id_field;

    @Column(name = "id_game")
    private Integer id_game;

    @Column(name = "field_num")
    private Integer field_num;

    @Column(name = "id_card")
    private Integer id_card;

    public Integer getId_field() {
        return id_field;
    }

    public void setId_field(Integer id_field) {
        this.id_field = id_field;
    }

    public Integer getId_game() {
        return id_game;
    }

    public void setId_game(Integer id_game) {
        this.id_game = id_game;
    }

    public Integer getField_num() {
        return field_num;
    }

    public void setField_num(Integer field_num) {
        this.field_num = field_num;
    }

    public Integer getId_card() {
        return id_card;
    }

    public void setId_card(Integer id_card) {
        this.id_card = id_card;
    }
}