package com.estech.EstechAppBackend.dto.stock;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
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
@Builder
public class StockDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer quantity;
    @NotNull
    private RoomDTO room;
}