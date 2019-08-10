package com.example.demo.dto;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
@Data
public class UserDTO extends User {

    // 存在和订单一对多的关系
    private List<Order> orders;
}
