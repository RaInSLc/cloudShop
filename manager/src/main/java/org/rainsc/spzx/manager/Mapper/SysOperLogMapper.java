package org.rainsc.spzx.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainsc.spzx.model.entity.system.SysOperLog;

@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog sysOperLog);
}
