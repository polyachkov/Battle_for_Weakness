package ru.nsu.fit.battle_fw;

import java.io.Serializable;

public class InitGameResponse implements Serializable {
    private Board board;
    private CommonLibrary common_library;
    private UncommonLibrary uncommon_library;
    private RareLibrary rare_library;
    private EpicLibrary epic_library;
    private LegendaryLibrary legendary_library;
    private boolean isLockedCl;
    private boolean isLockedUcl;
    private boolean isLockedRl;
    private boolean isLockedEl;
    private boolean isLockedLl;

    public Board getBoard() {
        return board;
    }

    public CommonLibrary getCommon_library() {
        return common_library;
    }

    public UncommonLibrary getUncommon_library() {
        return uncommon_library;
    }

    public RareLibrary getRare_library() {
        return rare_library;
    }

    public EpicLibrary getEpic_library() {
        return epic_library;
    }
    public LegendaryLibrary getLegendary_library() {
        return legendary_library;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCommon_library(CommonLibrary common_library) {
        this.common_library = common_library;
    }

    public void setUncommon_library(UncommonLibrary uncommon_library) {
        this.uncommon_library = uncommon_library;
    }

    public void setRare_library(RareLibrary rare_library) {
        this.rare_library = rare_library;
    }

    public void setEpic_library(EpicLibrary epic_library) {
        this.epic_library = epic_library;
    }

    public void setLegendary_library(LegendaryLibrary legendary_library) {
        this.legendary_library = legendary_library;
    }

    public void setStatuses() {
        this.isLockedCl = common_library.isLocked();
        this.isLockedUcl = uncommon_library.isLocked();
        this.isLockedRl = rare_library.isLocked();
        this.isLockedEl = epic_library.isLocked();
        this.isLockedLl = legendary_library.isLocked();
    }

    public InitGameResponse(){

    }
}
