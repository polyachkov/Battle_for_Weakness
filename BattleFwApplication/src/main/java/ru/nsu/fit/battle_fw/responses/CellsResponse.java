package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.responses.info.CellInfo;

import java.util.List;

public class CellsResponse {
    List<CellInfo> cells;

    public CellsResponse(List<CellInfo> cells) {
        this.cells = cells;
    }

    public List<CellInfo> getCells() {
        return cells;
    }

    public void setCells(List<CellInfo> cells) {
        this.cells = cells;
    }
}
