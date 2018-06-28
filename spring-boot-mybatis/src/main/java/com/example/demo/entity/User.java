package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户实体类
 * @author QuiFar
 * @date 2017年11月11日 下午12:21:01
 * @version V1.0
 */
@Data
public class User implements Serializable {
	private static final long serialVersionUID = 2027317201994678710L;

	private Integer id;
	private String userName;
	private String password;
	private String mobile;
	private String sex;
	private String status;
}
