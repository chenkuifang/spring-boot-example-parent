package com.example.ws.cxf.provide.interceptor;

import com.example.ws.cxf.provide.util.XmlParseJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


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

    private SAAJInInterceptor saajIn = new SAAJInInterceptor();

    public CxfAuthenticationInterceptor() {
        super(Phase.PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        String str = null;
        try {

            SOAPMessage doc = message.getContent(SOAPMessage.class);

            if (doc == null) {
                saajIn.handleMessage(message);
                doc = message.getContent(SOAPMessage.class);
            }

            // 获取SoapHeader
            SOAPHeader header = doc.getSOAPHeader();
            if (header == null) {
                return;
            }

            //获取SoapHeader
            SOAPBody body = doc.getSOAPBody();
            if (body == null) {
                return;
            }

            Node node = body.getChildNodes().item(0);
            String requestMethod = node.getLocalName();
            String requestParams = node.getTextContent();
            byte[] decode = Base64.getDecoder().decode(requestParams.getBytes());
            String decodeParams = new String(decode, "utf-8");
            System.err.println("请求方法: " + requestMethod);
            System.err.println("请求参数： " + decodeParams);

            JSONObject jsonBody = XmlParseJsonUtils.xml2Json(decodeParams);

            String authResult = authenticationHandler(jsonBody.getJSONObject("orderInfo"));
            if (StringUtils.isNotBlank(authResult)) {
                throw new Fault(new IOException(authResult));
            }

        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private String authenticationHandler(JSONObject params) {
        int companyNum;
        try {

            if (!params.has("head") || !params.has("data")) {
                return "请求参数必须包含head节点和data节点。";
            }

            // sign  companyPass 不一定都存在，物流认证不存在companyPass
            JSONObject head = params.getJSONObject("head");
            if (!(head.has("key") &&
                    (head.has("sign")
                            || head.has("company_pass")))) {
                return "请求参数不全。";
            }

            if (head.has("company_pass")) {
                String companyPass = head.getString("company_pass");
               /* if (!companyPass.equals(offOper.getCompanyPass())) {
                    return "密码错误。";
                }*/
            }

            if (head.has("key")) {
                String key = head.get("key").toString();
                Long sc = System.currentTimeMillis();
                System.err.println(sc);
                Long keyLong = Long.parseLong(key);
                if (Math.abs(sc - keyLong) > 5 * 1000 * 60L) {
                    return "时间戳差别大于5分钟。";
                }
            }

        } catch (JSONException e) {
            log.error("请求参数解析异常:{},请求参数:{}", e.getMessage(), params);
            return "请求参数解析异常。";
        }

        return null;
    }


}
