package com.example.ws.cxf.provide.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Quifar
 * @version V1.0
 **/
@WebService(targetNamespace = "http://localhost:8081/ws/")
public interface UserService {

    @WebMethod
    String getName(@WebParam(name = "name") String name);

    @WebMethod
    String getUser(long userId);

}
