package org.rainsc.spzx.exception;

import lombok.Data;
import org.rainsc.spzx.model.vo.common.ResultCodeEnum;

@Data
public class R_Exception extends RuntimeException {
    private Integer code;          // 错误状态码
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public R_Exception(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

}
