package com.example.batchoperation.controller;

import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import com.example.batchoperation.entity.WeatherRequest;
import net.sf.json.JSONObject;
import com.example.batchoperation.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hou
 * @date 2020/8/14
 */
public class JsonReader {


    /**
     * String -->  Date
     * @param time
     * @return
     */
    public static Date getDateTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }



    /**
     * 读取 JSON 到 Weather 实体
     * @param jo
     * @return
     */
    public Weather addToListWeather(JSONObject jo){

        Weather weather = new Weather();

        if (jo.get("windSpeed") != null && jo.get("windSpeed") != ""){
            weather.setWindSpeed(Double.parseDouble(jo.get("windSpeed").toString()));
        }
        if (jo.get("rainfall") != null && jo.get("rainfall") != ""){
            weather.setRainfall(Double.parseDouble(jo.get("rainfall").toString()));
        }
        if (jo.get("atmosphericTemperature") != null && jo.get("atmosphericTemperature") != ""){
            weather.setAtmosphericTemperature(Double.parseDouble(jo.get("atmosphericTemperature").toString()));
        }
        if (jo.get("accumulation") != null && jo.get("accumulation") != ""){
            weather.setAccumulation(Double.parseDouble(jo.get("accumulation").toString()));
        }
        if (jo.get("pressure") != null && jo.get("pressure") != ""){
            weather.setPressure(Double.parseDouble(jo.get("pressure").toString()));
        }
        if (jo.get("windDirection") != null && jo.get("windDirection") != ""){
            weather.setWindDirection(Double.parseDouble(jo.get("windDirection").toString()));
        }
        if (jo.get("airHumidity") != null && jo.get("airHumidity") != ""){
            weather.setAirHumidity(Double.parseDouble(jo.get("airHumidity").toString()));
        }
        if (jo.get("noise") != null && jo.get("noise") != ""){
            weather.setNoise(Double.parseDouble(jo.get("noise").toString()));
        }
        if (jo.get("illumination") != null && jo.get("illumination") != ""){
            weather.setIllumination(Double.parseDouble(jo.get("illumination").toString()));
        }
        if (jo.get("pm10") != null && jo.get("pm10") != ""){
            weather.setPm10(Double.parseDouble(jo.get("pm10").toString()));
        }
        if (jo.get("pm25") != null && jo.get("pm25") != ""){
            weather.setPm25(Double.parseDouble(jo.get("pm25").toString()));
        }
        if (jo.get("createTime") != null && jo.get("createTime") != ""){
            weather.setCreateTime(getDateTime(jo.get("createTime").toString()));
        }

        return weather;
    }



    public WeatherRequest addToListWeatherRequest(JSONObject jo){

        WeatherRequest weatherRequest = new WeatherRequest();

        if (jo.get("windSpeed") != null && jo.get("windSpeed") != ""){
            weatherRequest.setWindSpeed(jo.get("windSpeed").toString());
        }
        if (jo.get("rainfall") != null && jo.get("rainfall") != ""){
            weatherRequest.setRainfall(jo.get("rainfall").toString());
        }
        if (jo.get("atmosphericTemperature") != null && jo.get("atmosphericTemperature") != ""){
            weatherRequest.setAtmosphericTemperature(jo.get("atmosphericTemperature").toString());
        }
        if (jo.get("accumulation") != null && jo.get("accumulation") != ""){
            weatherRequest.setAccumulation(jo.get("accumulation").toString());
        }
        if (jo.get("pressure") != null && jo.get("pressure") != ""){
            weatherRequest.setPressure(jo.get("pressure").toString());
        }
        if (jo.get("windDirection") != null && jo.get("windDirection") != ""){
            weatherRequest.setWindDirection(jo.get("windDirection").toString());
        }
        if (jo.get("airHumidity") != null && jo.get("airHumidity") != ""){
            weatherRequest.setAirHumidity(jo.get("airHumidity").toString());
        }
        if (jo.get("noise") != null && jo.get("noise") != ""){
            weatherRequest.setNoise(jo.get("noise").toString());
        }
        if (jo.get("illumination") != null && jo.get("illumination") != ""){
            weatherRequest.setIllumination(jo.get("illumination").toString());
        }
        if (jo.get("pm10") != null && jo.get("pm10") != ""){
            weatherRequest.setPm10(jo.get("pm10").toString());
        }
        if (jo.get("pm25") != null && jo.get("pm25") != ""){
            weatherRequest.setPm25(jo.get("pm25").toString());
        }
        if (jo.get("createTime") != null && jo.get("createTime") != ""){
            weatherRequest.setCreateTime(getDateTime(jo.get("createTime").toString()));
        }

        return weatherRequest;
    }
    /**
     * 生成 List<>Weather>
     * @param jsonString
     * @return
     */
    public List<Weather> getWeatherList(String jsonString){
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        List<Weather> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject jo = JSONObject.fromObject(jsonArray.getJSONObject(i));
            list.add(addToListWeather(jo));
        }
        return list;
    }


}
