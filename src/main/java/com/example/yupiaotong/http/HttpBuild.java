package com.example.yupiaotong.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class HttpBuild {

    private static String Query_Tickes_Url = "https://www.ly.com/trainsearchbffapi/trainSearch";
    private static String Connection_Tickes = "keep-alive";
    private static String Content_Type_Tickes = "application/json";
    private static String Content_Length_Tickes = "16265";
    private static String Access_Control_Allow_Origin_Tickes = "https://www.ly.com";


    private static String Query_Train_Number_Url = "https://kyfw.12306.cn/otn/queryTrainInfo/query";
    private static String Connection_Train_Number = "keep-alive";
    private static String Content_Encoding_Train_Number = "gzip";
    private static String Content_Type_Train_Number = "application/json;charset=UTF-8";
    private static String Ct_Train_Number = "C1_215_155_6";
    private static String Date_Train_Number = "Mon, 22 May 2099 15:03:32 GMT";
    private static String Transfer_Encoding_Train_Number = "chunked";
    private static String X_Cdn_Src_Port_Train_Number = "59619";
    private static String X_Via_Train_Number = "1.1 PS-YIC-01KQs61:8 (Cdn Cache Server V2.0)";
    private static String X_Ws_Request_Id_Train_Number = "646b8444_PS-YIC-01F8Q156_27905-33751";




    public static String syncPostTest (String json) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Connection",Connection_Tickes);
        httpHeaders.add("Content-Type",Content_Type_Tickes);
        httpHeaders.add("Content-Length",Content_Length_Tickes);
        httpHeaders.add("Access-Control-Allow-Origin",Access_Control_Allow_Origin_Tickes);

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(json,httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Query_Tickes_Url, stringHttpEntity, String.class);

        return responseEntity.getBody();
    }


    public static String asynPostTest (String json) {
        WebClient webClient = WebClient.create();

        Mono<String> stringMono = webClient.post()
                .uri(Query_Tickes_Url)
                .headers(httpHeaders -> {
                    httpHeaders.add("Connection",Connection_Tickes);
                    httpHeaders.add("Content-Type",Content_Type_Tickes);
                    httpHeaders.add("Content-Length",Content_Length_Tickes);
                    httpHeaders.add("Access-Control-Allow-Origin",Access_Control_Allow_Origin_Tickes);
                })
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(String.class);

        return stringMono.block();
    }


    public static String syncPostTickes (String json) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Connection",Connection_Tickes);
        httpHeaders.add("Content-Type",Content_Type_Tickes);
        httpHeaders.add("Content-Length",Content_Length_Tickes);
        httpHeaders.add("Access-Control-Allow-Origin",Access_Control_Allow_Origin_Tickes);

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(json,httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(Query_Tickes_Url, stringHttpEntity, String.class);

        return responseEntity.getBody();
    }


    public static String asynPostTickes (String json) {
        WebClient webClient = WebClient.create();

        Mono<String> stringMono = webClient.post()
                .uri(Query_Tickes_Url)
                .headers(httpHeaders -> {
                    httpHeaders.add("Connection",Connection_Tickes);
                    httpHeaders.add("Content-Type",Content_Type_Tickes);
                    httpHeaders.add("Content-Length",Content_Length_Tickes);
                    httpHeaders.add("Access-Control-Allow-Origin",Access_Control_Allow_Origin_Tickes);
                })
                .body(BodyInserters.fromValue(json))
                .retrieve()
                .bodyToMono(String.class);

        return stringMono.block();
    }


    public static String asynGetQueryTrainNumber (String trainNo, String date) {
        WebClient webClient = WebClient.create();

        Mono<String> stringMono = webClient.get()
                .uri(Query_Train_Number_Url+"?leftTicketDTO.train_no="+trainNo+"&leftTicketDTO.train_date="+date+"&rand_code=")
                .headers(httpHeaders -> {
                    httpHeaders.add("Connection",Connection_Train_Number);
                    httpHeaders.add("Content_Encoding",Content_Encoding_Train_Number);
                    httpHeaders.add("Content_Type",Content_Type_Train_Number);
                    httpHeaders.add("Ct",Ct_Train_Number);
                    httpHeaders.add("Date",date);
                    httpHeaders.add("Transfer_Encoding",Transfer_Encoding_Train_Number);
                    httpHeaders.add("X_Cdn_Src_Port",X_Cdn_Src_Port_Train_Number);
                    httpHeaders.add("X_Via_Train",X_Via_Train_Number);
                    httpHeaders.add("X_Ws_Request_Id",X_Ws_Request_Id_Train_Number);
                })
                .retrieve()
                .bodyToMono(String.class);

        return stringMono.block();
    }
}
