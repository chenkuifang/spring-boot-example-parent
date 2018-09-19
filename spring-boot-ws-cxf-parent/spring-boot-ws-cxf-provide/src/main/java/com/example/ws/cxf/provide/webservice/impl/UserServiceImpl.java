package com.example.ws.cxf.provide.webservice.impl;

import com.example.ws.cxf.provide.webservice.UserService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @author Quifar
 * @version V1.0
 **/
@WebService(targetNamespace = "http://localhost:8081/ws/"
        , endpointInterface = "com.example.ws.cxf.provide.webservice.UserService")
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getName(String name) {
        return "hi " + name;
    }

    @Override
    public String getUser(long userId) {
        return "getUser " + userId;
    }
}
