package com.example.batchoperation.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.batchoperation.entity.FileMsg;
import com.example.batchoperation.entity.Weather;
import com.example.batchoperation.service.FileMsgService;
import com.example.batchoperation.service.WeatherService;

/**
 * @author hou
 */
@RestController
public class UploadController {

    @Autowired
    private FileMsgService fileMsgService;

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)

    public @ResponseBody String upload(MultipartFile file)throws Exception{ //接受上传的文件

        try {
            //FileUtils.writeByteArrayToFile快速写文件到磁盘
            FileUtils.writeByteArrayToFile(new File("D:/upload/" + file.getOriginalFilename()), file.getBytes());

            //文件信息存到数据库
            FileMsg fileMsg = new FileMsg();
            String s = file.getOriginalFilename();
            String fileAddress = "D:\\upload\\";
            String[] ss = s.split("\\.");
            fileMsg.setFileName(ss[0]);
            fileMsg.setFileAddress(fileAddress);
            fileMsg.setFileType(ss[1]);
            fileMsgService.add(fileMsg);

            //解析
            String address = fileAddress + file.getOriginalFilename();
            List<Weather> weatherList = ExcelReader.readExcel(address);
            int listSize = weatherList.size();
            int batchSize = 50;
            List<Weather> list = new ArrayList<>();

            for(int i = 0 ; i < listSize; i+=batchSize){

                if( i + batchSize <= listSize ){
                    list.addAll(weatherList.subList(i,i + batchSize));
                    weatherService.addbatch(list);
                    list.clear();
                }
                else {
                    list.addAll(weatherList.subList(i,listSize));
                    weatherService.addbatch(list);
                    list.clear();
                }
            }

            return file.getOriginalFilename() + " upload successful, parse successful. ";

        }catch (IOException e){
            e.printStackTrace();
            return "wrong";

        }
    }

}
