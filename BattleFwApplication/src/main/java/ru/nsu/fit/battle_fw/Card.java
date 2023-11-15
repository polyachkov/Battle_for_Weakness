package ru.nsu.fit.battle_fw;

import java.io.Serializable;

public class Card implements Serializable {
    private String name; // Название карты
    private int attack; // Сила атаки карты
    private int health; // Здоровье карты
    private int cost; // Стоимость карты

    private int evasion; // Уклонение карты

    private int attack_speed; // Скорость атаки карты

    private int movement_speed; // Скорость передвижения карты

    public Card(String name, int attack, int health, int cost,
                int evasion, int attack_speed, int movement_speed) {
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.cost = cost;
        this.evasion = evasion;
        this.attack_speed = attack_speed;
        this.movement_speed = movement_speed;
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

    public int getAttackSpeed() {
        return attack_speed;
    }

    public int getMovementSpeed() {
        return movement_speed;
    }
}