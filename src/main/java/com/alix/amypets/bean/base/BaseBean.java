package com.alix.amypets.bean.base;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import lombok.Data;

import java.util.Date;

/**
 * 实体基类
 */
@Data
public class BaseBean<T>{

    private String creator;

    private Date createdTime;

    private String modifier;

    private Date modifiedTime;

}
