package com.example.batchoperation.mapper;

import java.util.List;

import com.example.batchoperation.entity.Count;
import com.example.batchoperation.entity.WeatherRequest;
import com.example.batchoperation.entity.WeatherResponse;
import org.springframework.stereotype.Component;

import com.example.batchoperation.entity.Weather;

/**
 * @author 学无止境~冲
 */
@Component("WeatherMapper")
public interface WeatherMapper {

    /**
     * 单行导入信息
     * @param weather
     * @return
     */
    int insertweather(Weather weather);

    /**
     * 批量导入信息
     * @param weatherList
     * @return
     */
    int insertbatch(List<Weather> weatherList);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deletebatch(int[] ids);

    List<WeatherResponse> search(WeatherRequest weatherRequest);

    List<Count> count();

    Count maxCounter();
}
