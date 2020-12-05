package cn.sea.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestUser implements Serializable {
    private String name;
    private Integer age;
}
