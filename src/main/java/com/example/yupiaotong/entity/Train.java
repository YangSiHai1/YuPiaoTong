package com.example.yupiaotong.entity;

import lombok.Data;

import java.util.List;

@Data
public class Train {

    private String arrStationCode;
    private String arrStationName;
    private String arrDate;
    private String arrTime;
    private String depStationCode;
    private String depStationName;
    private String depDate;
    private String depTime;
    private String arrivalDays;
    private String runTime;
    private String trainCode;
    private String trainNo;
    private String type;
    private List<Ticket> trainAvs;
}
