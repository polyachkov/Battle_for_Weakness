package ru.nsu.fit.battle_fw.responses.info;

public class StatusInfo {
    private Integer babos;
    private Integer collectors;
    private Integer health;

    public StatusInfo(Integer babos, Integer collectors, Integer health) {
        this.babos = babos;
        this.collectors = collectors;
        this.health = health;
    }

    public Integer getBabos() {
        return babos;
    }

    public void setBabos(Integer babos) {
        this.babos = babos;
    }

    public Integer getCollectors() {
        return collectors;
    }

    public void setCollectors(Integer collectors) {
        this.collectors = collectors;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
}
