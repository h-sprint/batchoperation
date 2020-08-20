package com.example.batchoperation.controller;

import com.example.batchoperation.entity.Weather;
import com.example.batchoperation.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hou
 * @date 2020/8/14
 */

@RestController
public class InsertController {

    @Autowired
    private WeatherService weatherService;

    /**
     * 批量导入
     * @param listAll
     * @param batchSize
     * @return
     */
    public String batchInsert(List<Weather> listAll, int batchSize){
        int listAllSize = listAll.size();
        List<Weather> weatherList = new ArrayList<>();
        for(int j = 0 ; j < listAllSize; j += batchSize){
            if( j + batchSize <= listAllSize ){
                weatherList.addAll(listAll.subList(j,j + batchSize));
                try {
                    weatherService.addbatch(weatherList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                weatherList.clear();
            }
            else {
                weatherList.addAll(listAll.subList(j,listAllSize));
                try {
                    weatherService.addbatch(weatherList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                weatherList.clear();
            }
        }
        return "batch successful";
    }


    @RequestMapping(value = "/jsonInsert",produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    public @ResponseBody String readJson(@RequestParam(name = "jsonString") String jsonString)throws Exception{

        // 如果以 Postman 形式传入，则需要 去掉 @RequestParam(name = "jsonString")

        try {
            JsonReader jsonReader = new JsonReader();
            List<Weather> listAll = jsonReader.getWeatherList(jsonString);

            //设置批量导入大小
            int batchSize = 50;
            return batchInsert(listAll,batchSize);

        }catch (Exception e){
            e.printStackTrace();
            return "wrong";
        }
    }



}
