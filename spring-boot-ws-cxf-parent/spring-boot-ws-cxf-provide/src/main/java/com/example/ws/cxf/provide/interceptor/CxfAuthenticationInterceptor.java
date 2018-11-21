package com.example.ws.cxf.provide.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * cxf认证拦截器
 * <p>
 * 拦截soap请求协议
 *
 * @author Quifar
 * @version V1.0
 **/
@Slf4j
@Component
public class CxfAuthenticationInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public CxfAuthenticationInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        // 获取方法信息
        Exchange exchange = message.getExchange();
        BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
        MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(MethodDispatcher.class.getName());
        Method method = md.getMethod(bop);
        String methodName = method.getName();
        System.out.println("方法命名：" + methodName);

        if (methodName.equalsIgnoreCase("getUser")) {
            throw new Fault(new IllegalArgumentException("就是不给你调用我的方法"));
        }

        // 获取参数
        List<?> content = message.getContent(List.class);
        System.out.println("请求参数：" + content.get(0));

    }

    @Override
    public void handleFault(SoapMessage message) {
        super.handleFault(message);
        try {
            System.err.println("请求捕获到异常处理...");

            OutputStream os = new ByteArrayOutputStream(1024);

            IOUtils.copy(new ByteArrayInputStream("xxxx你妹".getBytes()), os);


            os.flush();
            message.setContent(OutputStream.class, os);

            //Exception exception = new Exception();


//            message.setContent(Exception.class, );
            //System.err.println("异常内容：" + fault.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // super.handleFault(message);
    }
}
