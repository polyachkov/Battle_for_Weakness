package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hand_composition", schema = "public")
public class HandComp {
    @Id
    @Column(name = "id_hand_card")
    @SequenceGenerator(name = "handsCompIdSeq", sequenceName = "hand_comp_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handsCompIdSeq")
    private Integer id_hand_card;

    @Column(name = "id_hand")
    private Integer id_hand;

    @Column(name = "id_card")
    private Integer id_card;

    public Integer getId_hand_card() {
        return id_hand_card;
    }

    public void setId_hand_card(Integer id_hand_card) {
        this.id_hand_card = id_hand_card;
    }

    public Integer getId_hand() {
        return id_hand;
    }

    public void setId_hand(Integer id_hand) {
        this.id_hand = id_hand;
    }

    public Integer getId_card() {
        return id_card;
    }

    public void setId_card(Integer id_card) {
        this.id_card = id_card;
    }
}