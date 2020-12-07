package cn.sea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 省份实体类
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Province implements Serializable {

    private String id;
    private String name;
    private String tags;
    private Integer placecounts; // 景点个数

}
