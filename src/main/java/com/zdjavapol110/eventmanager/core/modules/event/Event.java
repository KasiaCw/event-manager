package com.zdjavapol110.eventmanager.core.modules.event;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name ="event")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name ="title", nullable = false )
    private String title;

    @Column(name = "date")
    private String date;

    @Column(name = "description", nullable = false)
    private String description;


}
