package com.estech.EstechAppBackend.dto.stock;

import com.estech.EstechAppBackend.model.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.estech.EstechAppBackend.model.Stock}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDTO {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer quantity;
    @NotBlank
    private String roomName;
}