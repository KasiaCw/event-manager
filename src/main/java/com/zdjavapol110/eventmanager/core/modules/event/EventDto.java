package com.zdjavapol110.eventmanager.core.modules.event;


import lombok.Data;

@Data
public class EventDto {

    private Long id;
   // private String uuid;
    private String title;
    private String date;
    private String description;

}
