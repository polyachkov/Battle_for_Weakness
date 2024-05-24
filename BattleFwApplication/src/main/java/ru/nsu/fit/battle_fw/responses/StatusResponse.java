package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.responses.info.StatusInfo;

public class StatusResponse {
    StatusInfo status;

    public StatusResponse(StatusInfo status) {
        this.status = status;
    }

    public StatusInfo getStatus() {
        return status;
    }

    public void setStatus(StatusInfo status) {
        this.status = status;
    }
}
