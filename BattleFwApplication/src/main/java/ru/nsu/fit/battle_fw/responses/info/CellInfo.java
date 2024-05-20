package ru.nsu.fit.battle_fw.responses.info;

public class CellInfo {
    private Integer cell_num;
    private Integer id_card;
    private String name_owner;
    private Integer sickness;

    public CellInfo(Integer cell_num, Integer id_card, String name_owner, Integer sickness) {
        this.cell_num = cell_num;
        this.sickness = sickness;
        this.name_owner = name_owner;
        this.id_card = id_card;
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
}
