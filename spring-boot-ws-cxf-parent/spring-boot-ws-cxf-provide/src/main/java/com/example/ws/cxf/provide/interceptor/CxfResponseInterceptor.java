package com.example.ws.cxf.provide.interceptor;

import com.example.ws.cxf.provide.util.JsonParseXmlUtils;
import com.example.ws.cxf.provide.util.XmlParseJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import javax.xml.namespace.QName;
import java.io.*;

/**
 * cxf统一转换xml格式返回
 *
 * @author Quifar
 * @version V1.0
 **/
@Slf4j
public class CxfResponseInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static final String CHARSET = "UTF-8";

    public CxfResponseInterceptor() {

        //流关闭之前拦截
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        System.err.println("进入 CxfResponseInterceptor 拦截器");
        try {

            boolean isOutbound = message == message.getExchange().getOutMessage()
                    || message == message.getExchange().getOutFaultMessage();
            if (isOutbound) {
                OutputStream os = message.getContent(OutputStream.class);

                CachedStream cs = new CachedStream();
                message.setContent(OutputStream.class, cs);
                message.getInterceptorChain().doIntercept(message);

                CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);
                InputStream in = csnew.getInputStream();

                String xml = IOUtils.toString(in, CHARSET);
                byte[] response = packageResponseInfo(xml);

                IOUtils.copy(new ByteArrayInputStream(response), os);

                cs.close();
                os.flush();

                message.setContent(OutputStream.class, os);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("webservice请求响应异常:{}", e.getMessage());
        }

        QName fault = message.getVersion().getFault();
        System.err.println("fault:" + fault.getLocalPart());
    }

    /**
     * 统一封装结果成xml 返回
     *
     * @param responseBody 未处理的响应返回体
     * @return 转成xml, base 后返回
     */
    private byte[] packageResponseInfo(String responseBody) throws UnsupportedEncodingException {
        String returnStr = XmlParseJsonUtils.substringByNode(responseBody, "return");
        String xml = JsonParseXmlUtils.json2Xml(returnStr);
        return Base64.encodeBase64String(xml.getBytes(CHARSET)).getBytes();
    }

    private class CachedStream extends CachedOutputStream {

        public CachedStream() {
            super();
        }

        protected void doFlush() throws IOException {
            currentStream.flush();
        }

        protected void doClose() throws IOException {
        }

        protected void onWrite() throws IOException {
        }

    }

}
