package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "time_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;

    private LocalTime hour;

    private String weekday;

}
