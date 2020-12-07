package cn.sea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 景点实体
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Place implements Serializable {

    private String id;
    private String name;
    private String picpath; // 图片路径
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date hottime; // 旺季时间
    private Double hotticket; // 旺季门票
    private Double dimticket; // 淡季门票
    private String placedesc; // 景点描述
    private String provinceid; // 对应省份id

}
