package org.rainsc.spzx.manager.Service.impl;

import org.rainsc.spzx.common.log.service.AsyncOperLogService;
import org.rainsc.spzx.manager.Mapper.SysOperLogMapper;
import org.rainsc.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /**
     * 保存日志
     *
     * @param sysOperLog 日志
     */
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
