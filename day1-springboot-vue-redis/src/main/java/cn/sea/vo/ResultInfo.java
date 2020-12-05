package cn.sea.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装响应信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo {

    private boolean status;
    private String msg;
    private Object data;
}
