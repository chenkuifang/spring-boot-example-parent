package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author: Quifar
 * @version: 1.0
 */
@Data
public class Order {

    private Integer id;

    private Integer uid;

    private String goodsName;

    private BigDecimal price;
}
