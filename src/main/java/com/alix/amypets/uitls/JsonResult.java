package com.alix.amypets.uitls;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 向前端返回的结果集
 */
@Data
@NoArgsConstructor
public class JsonResult  implements Serializable {

    public static final boolean STATE_SUCCESS = true;

    public static final boolean STATE_FAIL = false;

    private boolean state; // 状态

    private int code; // 状态码

    private String msg; // 信息

    private Object data; // 包装的数据

    // 当业务中发现抛出的异常则调用这个构造器
    public JsonResult(Throwable e){
        this.state = false;
        this.msg = e.getMessage();
    }

    // 当业务正常执行且不返回数据时调用这个构造器
    public JsonResult(boolean state){
        this.state = true;
    }

    // 当业务正常执行且返回数据时调用这个构造器
    public JsonResult(boolean state,Object data){
        this.state = state;
        this.data = data;
    }



}
