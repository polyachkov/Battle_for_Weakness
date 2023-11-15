package ru.nsu.fit.battle_fw;

import java.io.Serializable;

public class InitGameResponse implements Serializable {
    private Board board;

    private CommonLibrary common_library;

    public Board getBoard() {
        return board;
    }

    public CommonLibrary getCommon_library() {
        return common_library;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCommon_library(CommonLibrary common_library) {
        this.common_library = common_library;
    }

    public InitGameResponse(){

    }
}
