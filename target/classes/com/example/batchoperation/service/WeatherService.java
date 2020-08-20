package com.example.batchoperation.service;

import com.example.batchoperation.entity.Count;
import com.example.batchoperation.entity.Weather;
import com.example.batchoperation.entity.WeatherRequest;
import com.example.batchoperation.entity.WeatherResponse;
import com.example.batchoperation.mapper.WeatherMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 学无止境~冲
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WeatherService {

    @Autowired
    private WeatherMapper weatherMapper;

    public boolean addweather(Weather weather) throws Exception {
        return this.weatherMapper.insertweather(weather) > 0;
    }

    public boolean addbatch(List<Weather> weatherList) throws Exception {
        return this.weatherMapper.insertbatch(weatherList) > 0;
    }

    public boolean delete(int[] ids){
        return this.weatherMapper.deletebatch(ids) > 0;
    }

    public List<WeatherResponse> search(WeatherRequest weatherRequest){
        return this.weatherMapper.search(weatherRequest);
    }

    private final ConcurrentMap<Long, Weather> weatherMap = new ConcurrentHashMap<Long, Weather>();

    public List<Weather> listWeather(){
        return new ArrayList<Weather>(this.weatherMap.values());
    }

    public List<Count> count(){
        return this.weatherMapper.count();
    }

    public Count maxCounter(){
        return this.weatherMapper.maxCounter();
    }

    public PageInfo findPage(int page,int pageSize,WeatherRequest weatherRequest){
        PageHelper.startPage(page,pageSize);
        List<WeatherResponse> list = this.search(weatherRequest);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}