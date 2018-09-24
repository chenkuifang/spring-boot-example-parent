package com.example.ws.cxf.client.controller;

import com.example.ws.cxf.client.util.HttpRequestUtils;
import com.example.ws.cxf.client.util.XmlParamsGenerateUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author Quifar
 * @version V1.0
 **/
@RestController
public class IndexController1 {

    // 成功
    @GetMapping("/index1")
    public String test() throws UnsupportedEncodingException {
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8081/ws/userService?wsdl");
        Object[] objects = new Object[0];
        try {
            objects = client.invoke("getOrder", "TD18090300004");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = objects[0].toString();

        System.out.println("返回的xml:" + result);


        return new String(result.getBytes(), "utf-8");
    }

    @GetMapping("/getUser")
    public String xmlTest() {
        String result;
        try {
            result = XmlParamsGenerateUtils.getDrugstoreOrderApiXmlParams("密码", "10025", "TD18090300004");

            System.err.println(result);

            result = HttpRequestUtils.post("http://localhost:8081/ws/userService?wsdl", result);
            System.err.println("结果未解码：" + result);
            byte[] encode = Base64.decodeBase64(result.getBytes());
            System.out.println("返回的xml:" + new String(encode, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ok";
    }

}
