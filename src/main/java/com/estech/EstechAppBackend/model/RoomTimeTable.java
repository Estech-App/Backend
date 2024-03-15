package com.estech.EstechAppBackend.model;

import com.estech.EstechAppBackend.model.enums.RoomStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "room_status")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoomTimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoomStatusEnum status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roomTimeTables")
    private List<Room> rooms;

}