package com.example.ws.cxf.provide.interceptor;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
public class MyFaultOutInterceptor extends AbstractPhaseInterceptor<Message> {


    public MyFaultOutInterceptor() {
        super(Phase.PRE_PROTOCOL);
        getAfter().add(CxfAuthenticationInterceptor.class.getName());
        //super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        System.err.println("MyFaultOutInterceptor.....");

//        Fault f = (Fault) message.getContent(Exception.class);
//        if (f == null) {
//            return;
//        }
//
//        Throwable cause = f.getCause();
//        if (cause == null) {
//            return;
//        }
//
//        System.err.println("异常信息：  " + f.getMessage());


        MessageContentsList lstContent = MessageContentsList.getContentsList(message);

        HttpServletResponse response = (HttpServletResponse)message.get(AbstractHTTPDestination.HTTP_RESPONSE);

        try {
            ServletOutputStream os = response.getOutputStream();
            os.write("异常啦".getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
