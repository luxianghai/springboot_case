package cn.sea.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ResultInfo implements Serializable {

    private boolean status;
    private String msg;
    private Object data;
}
