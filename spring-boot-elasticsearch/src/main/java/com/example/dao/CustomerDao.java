package com.example.dao;

import com.example.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户数据处理层
 *
 * @author QuiFar
 * @version V1.0
 **/
@Repository
public interface CustomerDao extends ElasticsearchRepository<Customer, String> {

    /**
     * 根据名字查找
     *
     * @param firstName 名字
     * @return 客户对象
     */
    Customer findByFirstName(String firstName);

    /**
     * 根据姓氏查找
     *
     * @param lastName 姓氏
     * @return 客户对象列表
     */
    List<Customer> findByLastName(String lastName);
}
