package com.example.yupiaotong.entity;

import lombok.Data;

import java.util.List;

@Data
public class TrainNumber {

    private String trainNo;
    private String depDate;
    private List<String> stations;

    public TrainNumber(String trainNo, String depDate, List<String> stations) {
        this.trainNo = trainNo;
        this.depDate = depDate;
        this.stations = stations;
    }
}
