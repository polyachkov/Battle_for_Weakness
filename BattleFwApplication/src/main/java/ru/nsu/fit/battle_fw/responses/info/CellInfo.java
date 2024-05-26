package ru.nsu.fit.battle_fw.responses.info;

public class CellInfo {
    private Integer cell_num;
    private Integer id_card;
    private String name_owner;
    private Integer sickness;
    private String card_name;
    private Integer attack;
    private Integer health;
    private Integer cost;
    private Integer evasion;
    private Integer attack_speed;
    private Integer movement_speed;

    public CellInfo(Integer cell_num, Integer id_card,
                    String name_owner, Integer sickness,
                    String card_name, Integer attack,
                    Integer health, Integer cost,
                    Integer evasion, Integer attack_speed,
                    Integer movement_speed) {
        this.cell_num = cell_num;
        this.id_card = id_card;
        this.name_owner = name_owner;
        this.sickness = sickness;
        this.card_name = card_name;
        this.attack = attack;
        this.health = health;
        this.cost = cost;
        this.evasion = evasion;
        this.attack_speed = attack_speed;
        this.movement_speed = movement_speed;
    }

    public Integer getCell_num() {
        return cell_num;
    }

    public void setCell_num(Integer cell_num) {
        this.cell_num = cell_num;
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

    public void setName_owner(String name_owner) {
        this.name_owner = name_owner;
    }

    public Integer getSickness() {
        return sickness;
    }

    public void setSickness(Integer sickness) {
        this.sickness = sickness;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
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
}
