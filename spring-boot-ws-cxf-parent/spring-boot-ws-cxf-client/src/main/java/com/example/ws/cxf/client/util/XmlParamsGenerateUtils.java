package com.example.ws.cxf.client.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * xml 参数生成工具
 *
 * @author QuiFar
 * @version V1.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class XmlParamsGenerateUtils {

    /**
     * 获取调用智慧药房订单接口入参构造
     *
     * @param companyPwd 调用机构密码
     * @param companyNum 调用机构码
     * @param orderData  <data> 节点内的数据
     * @return
     */
    public static String getDrugstoreOrderApiXmlParams(String companyPwd
            , String companyNum, String orderData) throws Exception {
        //String invokeMethod = "saveOrderInfo";
        String invokeMethod = "getOrderByPost";
        return getDrugstoreApiXmlParams(invokeMethod, companyPwd, companyNum, orderData);
    }

    /**
     * 获取康美API接口的调用的XML参数
     *
     * @param invokeMethod 需要调用的方法
     * @param companyPwd   调用机构密码
     * @param companyNum   调用机构码
     * @param dataParams   <data> 节点内的数据
     * @return
     * @throws Exception
     */
    private static String getDrugstoreApiXmlParams(String invokeMethod
            , String companyPwd, String companyNum, String dataParams) throws Exception {
        Long key = System.currentTimeMillis();
        String sign = MDUtils.encodeMD5(invokeMethod + key + companyPwd);
        sign = sign.toUpperCase();
        StringBuilder content;
        content = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        content.append("<orderInfo>");

        // head
        content.append("<head>");
        content.append("<company_num>").append(companyNum).append("</company_num>");
        content.append("<key>").append(key).append("</key>");
        content.append("<sign>").append(sign).append("</sign>");
        content.append("</head>");

        // data
        content.append("<data>");
        content.append(dataParams);
        content.append("</data>");
        content.append("</orderInfo>");

        StringBuilder params;
        params = new StringBuilder();
        params.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"  >");
        //params.append("<soap:Header>");
        //params.append("<username>quifar</username>");
        //params.append("<password>123456</password>");
        //params.append("</soap:Header>");
        params.append("<soap:Body>");
        // params.append("<ns1:").append(invokeMethod).append(" xmlns:ns1=\"http://factory.service.cxf.kangmei.com/\">");
        params.append("<ns1:").append(invokeMethod).append(" xmlns:ns1=\"http://localhost:8085/ws/\">");

        // request
        params.append("<request>");
        params.append(Base64.encodeBase64String(content.toString().getBytes("UTF-8")));
        params.append("</request>");

        params.append("</ns1:").append(invokeMethod).append(">");
        params.append("</soap:Body>");
        params.append("</soap:Envelope>");

        return params.toString();
    }

//    public static void main(String[] args) {
////        Map<String, String> mapData = new LinkedHashMap<>(8);
////        mapData.put("title", "drugsDistribution");
////        mapData.put("factoryId", "PS-001");
////        mapData.put("userId", "zzskzx");
////        mapData.put("password", "qwe123");
////        mapData.put("type", "0");
////        String result = getXmlParams(mapData);
////        System.err.println(result);
//
//        try {
//            String kangMeiOderApiXmlParams = getDrugstoreOderApiXmlParams("");
//
//            System.err.println(kangMeiOderApiXmlParams);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
