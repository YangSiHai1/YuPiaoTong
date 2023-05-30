package com.example.yupiaotong.entity;

import lombok.Data;

@Data
public class TicketQuery {

    private String arrStation;
    private String depStation;
    private String depDate;
    private String traceId;
    private long ts;
    private int pid = 1;
    private String type = "ADULT";
}
