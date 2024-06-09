package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.RoomStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "room_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RoomTimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date end;

    private String dayOfWeek;

    private Boolean reccurence;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

}
