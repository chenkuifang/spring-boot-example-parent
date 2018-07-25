package com.example.controller;

import com.example.dao.CustomerDao;
import com.example.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author QuiFar
 * @version V1.0
 **/
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/add")
    public String add() {
        Customer customer = new Customer("123", "quifar", "chen");
        customerDao.save(customer);
        return "ok";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable String id) {
        Optional<Customer> result = customerDao.findById(id);
        Customer customer = result.get();
        return customer.toString();
    }

}
