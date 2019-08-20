package com.example.mypoi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class MyBean {
    //private byte[] pic;
    ImageBean imageBean;
    private Integer id;
    private String name;
}
