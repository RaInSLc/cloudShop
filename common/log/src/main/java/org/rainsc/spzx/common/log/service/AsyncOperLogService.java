package org.rainsc.spzx.common.log.service;

import org.rainsc.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    // 保存日志数据
    public abstract void saveSysOperLog(SysOperLog sysOperLog);
}