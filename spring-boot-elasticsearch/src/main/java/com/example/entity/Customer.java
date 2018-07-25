package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 客户对象
 *
 * @author QuiFar
 * @version V1.0
 **/
@Data
@AllArgsConstructor
@Document(indexName = "customer", type = "customer", shards = 1, replicas = 0, refreshInterval = "-1")
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}
