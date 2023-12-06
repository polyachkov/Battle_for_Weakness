package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "card", schema = "public")
public class Card {

    @Id
    @Column(name = "id_card")
    private Integer id_card;

    @Column(name = "name")
    private String name;

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

    @Column(name = "number_of_cards")
    private Integer number_of_cards;

    public Integer getId_card() {
        return id_card;
    }

    public void setId_card(Integer id_card) {
        this.id_card = id_card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getEvasion() {
        return evasion;
    }

    public void setEvasion(Integer evasion) {
        this.evasion = evasion;
    }

    public Integer getAttack_speed() {
        return attack_speed;
    }

    public void setAttack_speed(Integer attack_speed) {
        this.attack_speed = attack_speed;
    }

    public Integer getMovement_speed() {
        return movement_speed;
    }

    public void setMovement_speed(Integer movement_speed) {
        this.movement_speed = movement_speed;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public Integer getNumber_of_cards() {
        return number_of_cards;
    }

    public void setNumber_of_cards(Integer number_of_cards) {
        this.number_of_cards = number_of_cards;
    }
}