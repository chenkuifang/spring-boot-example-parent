package com.example.demo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果返回类,控制器返回值只可以是String,和JsonResult这两种类型
 *
 * @author QuiFar
 * @version V1.0
 */
@Data
public final class JsonResult implements Serializable {

    private static final long serialVersionUID = -4908915966053299827L;
    private static JsonResult resultBean = null;
    private static List<?> list = new ArrayList<>();
    /**
     * 编码
     */
    private String code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 列表总数，供分页使用
     */
    private Integer count;
    /**
     * 数据列表
     */
    private List<?> data;

}
