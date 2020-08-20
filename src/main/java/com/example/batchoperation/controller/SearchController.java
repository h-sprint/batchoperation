package com.example.batchoperation.controller;

//import com.alibaba.fastjson.JSONObject;
import com.example.batchoperation.entity.Count;
import com.example.batchoperation.entity.Weather;
import com.example.batchoperation.entity.WeatherRequest;
import com.example.batchoperation.entity.WeatherResponse;
import com.example.batchoperation.service.WeatherService;
import com.example.batchoperation.util.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger; //导入方法依赖的package包/类
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author hou
 * @date 2020/8/17
 */
@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public PageInfo search(@RequestParam(name = "windSpeed")String windSpeed,
                             @RequestParam(name = "rainfall")String rainfall,
                             @RequestParam(name = "atmosphericTemperature")String atmosphericTemperature,
                             @RequestParam(name = "accumulation")String accumulation,
                             @RequestParam(name = "pressure")String pressure,
                             @RequestParam(name = "windDirection")String windDirection,
                             @RequestParam(name = "airHumidity")String airHumidity,
                             @RequestParam(name = "noise")String noise,
                             @RequestParam(name = "illumination")String illumination,
                             @RequestParam(name = "pm10")String pm10,
                             @RequestParam(name = "pm25")String pm25,
                             @RequestParam(name = "createTime")String createTime,
                             @RequestParam(name = "pageNum")int pageNum,
                             @RequestParam(name = "pageSize")int pageSize

    )throws Exception{

        // 天气查询实体
        WeatherRequest weatherRequest = new WeatherRequest().search(windSpeed,rainfall,atmosphericTemperature, accumulation,
                pressure,windDirection,airHumidity,noise,illumination,pm10,pm25,createTime);
        Logger logger = null;
        List<WeatherResponse> weatherResponses = weatherService.search(weatherRequest);


        //查询结果写到Excel
        ObjectMapper mapper = new ObjectMapper();
        List<Weather> list = mapper.convertValue(weatherResponses, new TypeReference<List<Weather>>() {});
        // 写入数据到工作簿对象内
        Workbook workbook = ExcelWriter.exportData(list);
        // 以文件的形式输出工作簿对象
        FileOutputStream fileOut = null;
        try {
            String exportFilePath = "D:\\download\\writeResult.xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
        } catch (Exception e) {
            logger.warning("输出Excel时发生错误，错误原因：" + e.getMessage());
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                logger.warning("关闭输出流时发生错误，错误原因：" + e.getMessage());
            }
        }

        return weatherService.findPage(pageNum,pageSize,weatherRequest);
    }

    @RequestMapping(value = "/download",  method = RequestMethod.POST)
    public @ResponseBody void download(HttpServletResponse response)throws Exception{ //接受上传的文件

        try{
            String filePath = "D:\\download\\writeResult.xlsx";
            FileUtil.downloadResource(filePath,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/count",  method = RequestMethod.POST)
    public @ResponseBody List<Count> count()throws Exception{ //接受上传的文件

        try{
            return weatherService.count();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/maxCounter",  method = RequestMethod.POST)
    public @ResponseBody Count maxCounter()throws Exception{ //接受上传的文件

        try{
            return weatherService.maxCounter();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
