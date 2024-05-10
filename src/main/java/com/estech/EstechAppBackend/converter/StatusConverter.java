package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.StatusDTO;
import com.estech.EstechAppBackend.model.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusConverter {

    public StatusDTO convertStatusEntityToStatusDTO(Status status) {
        StatusDTO statusDTO = new StatusDTO();

        statusDTO.setId(status.getId());
        statusDTO.setStatus(status.getStatus().toString());
        return statusDTO;
    }

}
