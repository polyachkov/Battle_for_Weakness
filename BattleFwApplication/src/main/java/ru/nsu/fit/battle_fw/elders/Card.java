package ru.nsu.fit.battle_fw.elders;

import java.io.Serializable;

public class Card implements Serializable {
    private String name; // Название карты
    private int attack; // Сила атаки карты
    private int health; // Здоровье карты
    private int cost; // Стоимость карты
    private int evasion; // Уклонение карты
    private Speed attack_speed; // Скорость атаки карты
    private int movement_speed; // Скорость передвижения карты
    private boolean ability; //Некоторая способность карты



    public Card(String name, int attack, int health, int cost,
                int evasion, Speed attack_speed, int movement_speed, boolean ability) {
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.cost = cost;
        this.evasion = evasion;
        this.attack_speed = attack_speed;
        this.movement_speed = movement_speed;
        this.ability = ability;
    }

    // Геттеры и сеттеры для свойств карты
    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return cost;
    }

    public int getEvasion() {
        return evasion;
    }

    public Speed getAttackSpeed() {
        return attack_speed;
    }

    public int getMovementSpeed() {
        return movement_speed;
    }

    public boolean getAbility() {
        return ability;
    }
}