package com.alix.amypets.bean;

import com.alix.amypets.bean.base.BaseBean;
import com.alix.amypets.bean.base.BaseCreate;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 每项数据的基础日志
 * 创建者 创建时间 修改者 修改时间
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_log_request")
public class RequestLog extends BaseCreate<RequestLog> {

    @TableId(value = "l_request_id",type = IdType.AUTO)
    private Integer id;

    private String ip;

    private String url;
    
    private String method;
    
    private String args;

    private Object res;

    private Integer localPort;

    private Integer serverPort;

    private Integer clientPort;
    
    private Integer runtime;

}

