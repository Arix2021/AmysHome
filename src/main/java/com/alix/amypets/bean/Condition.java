package com.alix.amypets.bean;

import lombok.Data;

/**
 * 分页查询条件对象
 */
@Data
public class Condition {

    // 要查询的id
    private Integer id;

    // 要查询的名字
    private String name;

    // 要查询的页码
    private Integer pageNo;

    //要查询的每页大小
    private Integer pageSize;

    // 要查询的条件对象
    private Object condition;
}
