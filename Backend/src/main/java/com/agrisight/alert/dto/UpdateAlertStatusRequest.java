package com.agrisight.alert.dto;

import com.agrisight.alert.entity.AlertStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateAlertStatusRequest {

    @NotNull(message = "Alert status is required")
    private AlertStatus status;

    public UpdateAlertStatusRequest() {
    }

    public UpdateAlertStatusRequest(AlertStatus status) {
        this.status = status;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }
}
