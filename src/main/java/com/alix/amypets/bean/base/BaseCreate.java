package com.alix.amypets.bean.base;

import lombok.Data;

import java.util.Date;

/**
 * 基类
 *      只记录创建者、创建时间
 */
@Data
public class BaseCreate<T> {

    private String creator;

    private Date createdTime;

}
