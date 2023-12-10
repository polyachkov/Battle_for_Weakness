package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cell", schema = "public")
public class Cell {
    @Id
    @Column(name = "id_cell")
    @SequenceGenerator(name = "cellsIdSeq", sequenceName = "cell_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cellsIdSeq")
    private Integer id_field;

    @Column(name = "id_game")
    private Integer id_game;

    @Column(name = "cell_num")
    private Integer cell_num;

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

    public Integer getCell_num() {
        return cell_num;
    }

    public void setCell_num(Integer field_num) {
        this.cell_num = field_num;
    }

    public Integer getId_card() {
        return id_card;
    }

    public void setId_card(Integer id_card) {
        this.id_card = id_card;
    }
}