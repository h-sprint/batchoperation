package com.example.batchoperation.mapper;

import com.example.batchoperation.entity.FileMsg;
import org.springframework.stereotype.Component;

/**
 * @author 学无止境~冲
 */
@Component("FileMsgMapper")
public interface FileMsgMapper {

    /**
     * 写入文件信息
     *
     * @param fileMsg
     * @return int
     */
    int insertfile(FileMsg fileMsg);
}
