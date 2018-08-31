package com.example.demo.common;

import lombok.*;

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
    private static List<?> list = new ArrayList<>(32);
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

    /***
     * 获取单例(双重检查锁定) 懒汉单例
     *
     * @return
     */
    public static JsonResult getInstance() {
        if (resultBean == null) {
            synchronized (JsonResult.class) {
                if (resultBean == null) {
                    resultBean = new JsonResult();
                }
            }
        }

        initList();

        return resultBean;
    }

    /**
     * 初始化对象
     */
    private static void initList() {
        list.clear();
        resultBean.setData(list);
    }


}
