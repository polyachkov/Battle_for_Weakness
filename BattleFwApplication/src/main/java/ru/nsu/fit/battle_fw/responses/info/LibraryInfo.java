package ru.nsu.fit.battle_fw.responses.info;

public class LibraryInfo {
    private String rarity;

    public LibraryInfo(String rarity) {
        this.rarity = rarity;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
