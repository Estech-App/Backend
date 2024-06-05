package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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

    private LocalDateTime start;

    private LocalDateTime end;

    private String weekday;

}
