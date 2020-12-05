package cn.sea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

    private String id;
    private String name;
    private String imgPath;
    private Double salary;
    private Integer age;
}
