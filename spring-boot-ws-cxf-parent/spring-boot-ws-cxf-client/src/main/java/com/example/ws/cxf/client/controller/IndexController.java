package com.example.ws.cxf.client.controller;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Quifar
 * @version V1.0
 **/
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping()
    public String index() {
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8081/ws/userService?wsdl");
        Object[] objects = new Object[0];
        try {
            objects = client.invoke("getName", "quifar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(objects[0].toString());

        return objects[0].toString();
    }
}
