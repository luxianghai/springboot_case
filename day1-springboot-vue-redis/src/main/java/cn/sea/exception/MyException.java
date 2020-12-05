package cn.sea.exception;

import cn.sea.enums.ExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{
    private ExceptionEnums exceptionEnums;
}
