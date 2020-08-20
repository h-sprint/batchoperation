package com.example.batchoperation.entity;

import com.example.batchoperation.controller.JsonReader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author 学无止境~冲
 */
public class WeatherRequest {

    //风速
    private String windSpeed;
    //雨量
    private String rainfall;
    //大气温度
    private String atmosphericTemperature;
    //雨量积累
    private String accumulation;
    //大气压力
    private String pressure;
    //风向
    private String windDirection;
    //大气湿度
    private String airHumidity;
    //噪声
    private String noise;
    //照度
    private String illumination;
    //pm10
    private String pm10;
    //pm25
    private String pm25;
    //创建时间
    private Date createTime;


    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }

    public String getAtmosphericTemperature() {
        return atmosphericTemperature;
    }

    public void setAtmosphericTemperature(String atmosphericTemperature) {
        this.atmosphericTemperature = atmosphericTemperature;
    }

    public String getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(String accumulation) {
        this.accumulation = accumulation;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getIllumination() {
        return illumination;
    }

    public void setIllumination(String illumination) {
        this.illumination = illumination;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WeatherRequest(){

    }

    public WeatherRequest  search(String windSpeed,String rainfall,String atmosphericTemperature,
                          String accumulation,String pressure,String windDirection,String airHumidity,
                          String noise,String illumination,String pm10,String pm25,String createTime){
        WeatherRequest weatherRequest = new WeatherRequest();

        if (windSpeed.length() != 0){
            weatherRequest.setWindSpeed(windSpeed);
        }else {
            weatherRequest.setWindSpeed(null);
        }

        if (rainfall.length() != 0){
            weatherRequest.setRainfall(rainfall);
        }else{
            weatherRequest.setRainfall(null);
        }

        if (atmosphericTemperature.length() != 0){
            weatherRequest.setAtmosphericTemperature(atmosphericTemperature);
        }else {
            weatherRequest.setAtmosphericTemperature(null);
        }

        if (accumulation.length() != 0){
            weatherRequest.setAccumulation(accumulation);
        }else {
            weatherRequest.setAccumulation(null);
        }

        if (pressure.length() != 0){
            weatherRequest.setPressure(pressure);
        }else {
            weatherRequest.setPressure(null);
        }

        if (windDirection.length() != 0){
            weatherRequest.setWindDirection(windDirection);
        }else {
            weatherRequest.setWindDirection(null);
        }

        if (airHumidity.length() != 0){
            weatherRequest.setAirHumidity(airHumidity);
        }else {
            weatherRequest.setAirHumidity(null);
        }

        if (noise.length() != 0){
            weatherRequest.setNoise(noise);
        }else {
            weatherRequest.setNoise(null);
        }

        if (illumination.length() != 0){
            weatherRequest.setIllumination(illumination);
        }else {
            weatherRequest.setIllumination(null);
        }

        if (pm10.length() != 0){
            weatherRequest.setPm10(pm10);
        }else {
            weatherRequest.setPm10(null);
        }

        if (pm25.length() != 0){
            weatherRequest.setPm25(pm25);
        }else {
            weatherRequest.setPm25(null);
        }

        if (createTime.length() != 0){
            weatherRequest.setCreateTime(JsonReader.getDateTime(createTime));
        }else {
            weatherRequest.setCreateTime(null);
        }

        return weatherRequest;
    }
}
