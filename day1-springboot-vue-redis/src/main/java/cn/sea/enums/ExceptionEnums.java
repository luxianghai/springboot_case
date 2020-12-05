package cn.sea.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 关于异常的枚举
 */

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnums {

    USER_ADD_ERROR(500, "添加用户失败"),
    USER_ALREADY_EXISTS(500, "用户已存在"),

    ;
    private int code; // 状态码
    private String msg; // 异常信息

}
