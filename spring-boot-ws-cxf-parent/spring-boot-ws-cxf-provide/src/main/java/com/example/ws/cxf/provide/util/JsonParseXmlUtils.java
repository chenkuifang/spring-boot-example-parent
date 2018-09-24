package com.example.ws.cxf.provide.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * json 转换xml格式工具
 *
 * @author QuiFar
 * @version V1.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonParseXmlUtils {

    /**
     * json 格式转xml 格式
     *
     * @param jsonStr 需要转换的json 字符串
     * @return xml 字符串
     * @throws JSONException
     */
    public static String json2Xml(String jsonStr) {
        return json2Xml(jsonStr, null);
    }

    /**
     * json 格式转xml 格式
     *
     * @param jsonStr 需要转换的json 字符串
     * @return xml 字符串
     * @throws JSONException
     */
    public static String json2Xml(String jsonStr, String tagName) {
        JSONObject jsonObj = new JSONObject(jsonStr);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + XML.toString(jsonObj, tagName);
    }

//    public static void main(String[] args) {
//        //String str = "{\"code\":200,\"msg\":\"成功\"}";
//        String str = "{\"code\":200,\"msg\":\"成功\",\"data\":{\"orderId\":\"KM17081300789\",\"uid\":null}}";
//        System.err.println(str);
//        String s = json2Xml(str,null);
//        System.err.println(s);
//    }

}
