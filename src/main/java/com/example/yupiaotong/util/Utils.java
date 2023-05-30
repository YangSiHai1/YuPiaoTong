package com.example.yupiaotong.util;

import com.alibaba.fastjson2.JSON;
import com.example.yupiaotong.entity.*;
import com.example.yupiaotong.http.HttpBuild;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {


    /**
     * 事务号
     * @return
     */

    public static String thirteenNum () {
        int i = (int) ((Math.random() +1) * 1000000000);
        return "176" + i;
    }


    /**
     * 列车信息
     * @param depStation
     * @param arrStation
     * @param depDate
     * @return
     */

    public static String queryTickets(String depStation, String arrStation, String depDate) {

        //  1.设置所有列车信息
        TicketQuery ticketQuery = new TicketQuery();
        ticketQuery.setDepStation(depStation);
        ticketQuery.setArrStation(arrStation);
        ticketQuery.setDepDate(depDate);
        String s = Utils.thirteenNum();
        ticketQuery.setTraceId(s);
        ticketQuery.setTs(Long.parseLong(s));

        //  2.获取列车信息
        String res = HttpBuild.asynPostTickes(JSON.toJSONString(ticketQuery));
        return JSON.parseObject(res).getJSONObject("data").get("trains").toString();
    }


    /**
     * 车次信息
     * @param trainNo
     * @param depDate
     * @param depStation
     * @param arrStation
     * @return
     */

    public static TrainNumber queryTrainNumber (String trainNo, String depDate, String depStation, String arrStation) {

        //  该车次的车站列表
        String res = HttpBuild.asynGetQueryTrainNumber(trainNo, depDate);
        List<Station> stations = JSON.parseArray(JSON.parseObject(res).getJSONObject("data").get("data").toString(), Station.class);
        //  例：[杭州东, 德清, 广德南, 宣城, 芜湖, 巢湖东, 合肥南, 淮南南, 阜阳西, 亳州南, 商丘, 兰考南, 郑州东, 洛阳龙门, 华山北, 西安北, 咸阳西, 宝鸡南, 天水南, 秦安, 通渭, 兰州西]
        List<String> stations2 = new ArrayList<>();
        for (Station station : stations) {
            stations2.add(station.getStation_name());
        }

        //  出发站list1
        ArrayList<String> arr1 = new ArrayList<>();
        for (String station : stations2) {
            arr1.add(station);
            if (depStation.equals(station)) {
                break;
            }
        }

        //  抵达站list2
        ArrayList<String> arr2 = new ArrayList<>();
        for (int i = 0; i < stations2.size(); i++) {
            if (arrStation.equals(stations2.get(i))) {
                arr2.addAll(stations2.subList(i, stations2.size()));
                break;
            }
        }

        //  计算笛卡尔积；arr3所有路线的结果
        ArrayList<String> arr3 = new ArrayList<>();
        for (int i = 0; i < arr1.size(); i++) {
            for (int j = 0; j < arr2.size(); j++) {
                String pair = arr1.get(i) + "-" + arr2.get(j);
                arr3.add(pair);
            }
        }

        return new TrainNumber(trainNo, depDate, arr3);
    }


    /**
     * 余票查询
     * @param trainNumber
     * @return
     */

    public static String queryRemainderTickets (TrainNumber trainNumber, Map<String,String> stationsMap) {

        ArrayList<Train> trains = new ArrayList<>();

        //  1.获取指定车次
        for (String stations : trainNumber.getStations()) {
            String[] station = stations.split("-");
            Train train = queryTickets2(trainNumber.getTrainNo(), trainNumber.getDepDate(), stationsMap.get(station[0]), stationsMap.get(station[1]));
            //  TODO：跳过无票车次
            if (null != train) {
                trains.add(train);
            }
        }

        return JSON.toJSONString(trains);
    }




    /**
     * 列车信息
     * @param trainNo
     * @param depDate
     * @param depStation
     * @param arrStation
     * @return
     */

    public static Train queryTickets2(String trainNo, String depDate, String depStation, String arrStation) {

        //  1.设置所有列车信息
        TicketQuery ticketQuery = new TicketQuery();
        ticketQuery.setDepStation(depStation);
        ticketQuery.setArrStation(arrStation);
        ticketQuery.setDepDate(depDate);
        String s = Utils.thirteenNum();
        ticketQuery.setTraceId(s);
        ticketQuery.setTs(Long.parseLong(s));

        //  2.获取列车信息
        String res = HttpBuild.asynPostTickes(JSON.toJSONString(ticketQuery));
        List<Train> trains = JSON.parseArray(JSON.parseObject(res).getJSONObject("data").get("trains").toString(), Train.class);

        //  3.筛选指定车次
        for (Train train : trains) {
            if (trainNo.equals(train.getTrainNo())) {
                for (Ticket ticket: train.getTrainAvs()) {
                    if (! "0".equals(ticket.getNum())) {
                        return train;
                    }
                }
            }
        }

        return null;
    }


}
