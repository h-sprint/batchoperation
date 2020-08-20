package com.example.batchoperation.service;

import com.example.batchoperation.entity.FileMsg;
import com.example.batchoperation.mapper.FileMsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 学无止境~冲
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileMsgService {

    @Autowired
    private FileMsgMapper fileMsgMapper;

    public boolean add(FileMsg fileMsg) throws Exception {
        return this.fileMsgMapper.insertfile(fileMsg) > 0;
    }

}
