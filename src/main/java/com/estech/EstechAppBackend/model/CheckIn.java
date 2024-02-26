package com.estech.EstechAppBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "check_ins")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    private Boolean checkIn;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;


}
