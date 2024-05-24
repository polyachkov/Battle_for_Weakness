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
    private Integer id_cell;

    @Column(name = "id_game")
    private Integer id_game;

    @Column(name = "cell_num")
    private Integer cell_num;

    @Column(name = "id_card")
    private Integer id_card;

    @Column(name = "name_owner")
    private String name_owner;

    @Column(name = "sickness")
    private Integer sickness;

    @Column(name = "attack")
    private Integer attack;

    @Column(name = "health")
    private Integer health;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "evasion")
    private Integer evasion;

    @Column(name = "attack_speed")
    private Integer attack_speed;

    @Column(name = "movement_speed")
    private Integer movement_speed;

    @Column(name = "rarity")
    private String rarity;

    @Column(name = "fraction")
    private String fraction;

    public Integer getId_cell() {
        return id_cell;
    }

    public void setId_cell(Integer id_cell) {
        this.id_cell = id_cell;
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

    public String getName_owner() {
        return name_owner;
    }

    public void setName_owner(String id_owner) {
        this.name_owner = id_owner;
    }

    public Integer getSickness() {
        return sickness;
    }

    public void setSickness(Integer sickness) {
        this.sickness = sickness;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Integer getMovement_speed() {
        return movement_speed;
    }

    public void setMovement_speed(Integer movement_speed) {
        this.movement_speed = movement_speed;
    }

    public Integer getAttack_speed() {
        return attack_speed;
    }

    public void setAttack_speed(Integer attack_speed) {
        this.attack_speed = attack_speed;
    }

    public Integer getEvasion() {
        return evasion;
    }

    public void setEvasion(Integer evasion) {
        this.evasion = evasion;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
}