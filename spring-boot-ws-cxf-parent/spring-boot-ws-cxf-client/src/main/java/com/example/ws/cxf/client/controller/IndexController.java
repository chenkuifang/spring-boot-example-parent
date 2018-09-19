package com.example.ws.cxf.client.controller;

import com.example.ws.cxf.client.util.HttpRequestUtils;
import com.example.ws.cxf.client.util.XmlParamsGenerateUtils;
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
        Client client = clientFactory.createClient("http://localhost:8085/ws/orderService?wsdl");
        Object[] objects = new Object[0];
        try {
            objects = client.invoke("getOrder", "TD18090300004");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(objects[0].toString());

        return objects[0].toString();
    }

    @GetMapping("/xml")
    public String xmlTest() {
        String result = "";
        try {
            result = XmlParamsGenerateUtils.getDrugstoreOrderApiXmlParams("密码", "公司编码", "TD18090300004");

            System.err.println(result);

            HttpRequestUtils.post("http://localhost:8085/ws/orderService?wsdl", result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
