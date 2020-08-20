package com.example.batchoperation.controller;

import com.example.batchoperation.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hou
 * @date 2020/8/17
 */
@RestController
public class DeleteController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/deleteById",produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "single")String single,
                @RequestParam(name = "intervalLeft")int intervalLeft,
                @RequestParam(name = "intervalRight")int intervalRight)throws Exception{
        // single 单一删除, 输入需要删除的 id, 以 ”,“ 隔开, 例如: 6,7,8; 如果不采用此种方式，请输入 null
        // interval 区间删除, 输入删除的区间, 以 ”-“ 隔开, 例如: 8-9; 如果不采用此种方式，请输入 0
        // 两者只能输入一个
        if(single.equals("null")){
            int[] ids = new int[intervalRight - intervalLeft + 1];
            for( int i = intervalLeft, j = 0; i < intervalRight+1; i++, j++){
                ids[j] = i;
            }
            weatherService.delete(ids);
        }else {
            String[] strings = single.split(",");
            int[] ids = new int[strings.length];
            for(int i = 0; i < strings.length; i++){
                ids[i] = Integer.parseInt(strings[i]);
            }
            weatherService.delete(ids);
        }

        return "delete success";
    }
}
