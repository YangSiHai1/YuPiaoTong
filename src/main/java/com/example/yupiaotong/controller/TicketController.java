package com.example.yupiaotong.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.example.yupiaotong.entity.TicketQuery;
import com.example.yupiaotong.entity.Train;
import com.example.yupiaotong.entity.TrainNumber;
import com.example.yupiaotong.util.FileUtils;
import com.example.yupiaotong.util.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class TicketController {

    //  TODO: 前端需要一个所有城市的列表，用于输入的站点提示
    //  TODO：两种错误，1.网络错误,2.暂无余票

    @Autowired
    private ResourceLoader resourceLoader;
    private static Map<String,String> StationsMap;
    private final Logger logger = Logger.getLogger("TicketController");


    @PostConstruct
    public void init() {
        FileUtils fileUtils = new FileUtils(resourceLoader);
        String stationsJson = fileUtils.readJsonFile();
        StationsMap = JSON.parseObject(stationsJson, new TypeReference<HashMap<String, String>>() {});
    }

    /**
     * 列车信息
     * @param ticketQuery 车票查询
     * @return 火车信息
     */

    @ResponseBody
    @GetMapping(value = "/queryTickets")
    public String queryTickets (TicketQuery ticketQuery) {

        try {
            String depStation = StationsMap.get(ticketQuery.getDepStation());
            String arrStation = StationsMap.get(ticketQuery.getArrStation());
            String depDate = ticketQuery.getDepDate();
            logger.info(ticketQuery.toString());

            //  1.车票查询
            return Utils.queryTickets(depStation, arrStation, depDate);

        } catch (Exception e) {
            logger.severe(e.toString());
            e.printStackTrace();
            return "";
        }

    }


    /**
     * 余票信息
     * @param train 这个车次
     * @return 这个车次的火车信息
     */
    @ResponseBody
    @GetMapping(value = "/queryRemainderTickets")
    public String queryRemainderTickets (Train train) {

        try {
            String depStation = train.getDepStationName();
            String arrStation = train.getArrStationName();
            String depDate = train.getDepDate();
            String trainNo = train.getTrainNo();
            logger.info(train.toString());

            //  @TODO：优化：车次信息加入缓存时间12h
            //  1.获取车次信息
            TrainNumber trainNumber = Utils.queryTrainNumber(trainNo, depDate, depStation, arrStation);
            logger.info(trainNumber.toString());

            //  2.余票查询
            return Utils.queryRemainderTickets(trainNumber, StationsMap);

        } catch (Exception e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }


    @ResponseBody
    @GetMapping(value = "/test")
    public String test () {
        return "";
    }


}
