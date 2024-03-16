package org.rainsc.spzx.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    LOGIN_ERROR(201, "密码错误,请检查密码后重新输入"),
    LOGIN_ACCOUNT_ERROR(2001, "用户名错误,请检查用户名"),
    VALIDATE_CODE_ERROR(202, "验证码错误"),
    LOGIN_AUTH(208, "用户未登录"),
    USER_NAME_IS_EXISTS(209, "用户名已经存在!!"),

    SYSTEM_ERROR(9999, "您的网络有问题请稍后重试/前端逻辑错误/检查请求方式"),
    NODE_ERROR(217, "该节点下有子节点，不可以删除"),
    DATA_ERROR(204, "数据异常"),
    ACCOUNT_STOP(216, "账号已停用"),

    STOCK_LESS(219, "库存不足"),

    MENU_NULL(7001, "菜单输入项目不能为空"),

    EXCEL_ERR(8001, "excel文件导出异常"),

    EXCEL_IN_ERR(8002, "excel文件导入异常");;

    private Integer code;      // 业务状态码
    private String message;    // 响应消息

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
