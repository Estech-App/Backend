package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stocks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer quantity;

    //    CONEXION CON ROOM
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

}
