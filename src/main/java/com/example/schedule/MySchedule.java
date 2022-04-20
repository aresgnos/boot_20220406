package com.example.schedule;

import java.io.IOException;
import java.util.Date;

import com.example.entity.ProductCountEntity;
import com.example.repository.ProductCountRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class MySchedule {

    // Scheduled는 보통 백업, 수집 용도로 많이 쓴다.

    // 저장소랑 연동할거면 autowired
    @Autowired
    ProductCountRepository pcRepository;

    // @Scheduled(cron = "*/10 * * * * *")
    public void printData() {
        Date date = new Date();
        System.out.println(date.toString());
    }

    // @Scheduled(cron = "*/10 * * * * *")
    public void printData1() throws IOException {
        final String URL = "http://ihongss.com/json/exam1.json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        Response response = client.newCall(request).execute();
        String msg = response.body().string();
        System.out.println(msg); // {"ret":"y", "data":"123"} 이게 두개 온다면 JASONARRAY

        // String 형태로 온 걸 json 형태로
        JSONObject jobj = new JSONObject(msg);
        System.out.println(jobj.getString("ret")); // 원하는 데이터 꺼내기
        System.out.println(jobj.getInt("data"));

        // 원하는 것을 객체로 만들고 save할 수 있다.
        // ProductCountEntity p = new ProductCountEntity();
        // pcRepository.save(p);
    }

    // @Scheduled(cron = "*/10 * * * * *")
    public void printData2() throws IOException {
        final String URL = "http://localhost:9090/ROOT/vue#/boardone?no=4.json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        Response response = client.newCall(request).execute();
        String msg = response.body().string();
        System.out.println(msg);

        // String 형태로 온 걸 json 형태로
        // JSONObject jobj = new JSONObject(msg);
        // System.out.println(jobj.getString("ret")); // 원하는 데이터 꺼내기
        // System.out.println(jobj.getInt("data"));

        // 원하는 것을 객체로 만들고 save할 수 있다.
        // ProductCountEntity p = new ProductCountEntity();
        // pcRepository.save(p);
    }
}
