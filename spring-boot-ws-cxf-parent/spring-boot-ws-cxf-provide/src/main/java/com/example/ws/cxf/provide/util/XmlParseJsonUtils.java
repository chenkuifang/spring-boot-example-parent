package com.example.ws.cxf.provide.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * xml 转换json格式工具
 *
 * @author QuiFar
 * @version V1.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XmlParseJsonUtils {

    /**
     * xml 格式转Json 格式
     *
     * @param xml 需要转换的xml
     * @return json
     * @throws JSONException
     */
    public static JSONObject xml2Json(String xml) throws JSONException {
        return XML.toJSONObject(xml);
    }

    /* *//**
     * 移除节点后，再转json
     * 例如： 需要<response> 节点，则传值：new String[]{"<response>","</response>"}
     * 注意：单节点不可以移除
     *
     * @param xml         需要转换的xml
     * @param removeNodes 需要移除的节点数组
     * @return
     *//*
    public static JSONObject xml2Json(String xml, String[] removeNodes) throws JSONException {
        return xml2Json(xml, false, removeNodes);
    }

    *//**
     * 移除节点后，再转json
     * 例如： 需要<response> 节点，则传值：new String[]{"<response>","</response>"}
     * 注意：单节点不可以移除
     *
     * @param xml            需要转换的xml
     * @param excludeEsbNode 是否排除esb 节点
     * @param removeNodes    需要移除的节点数组
     * @return
     *//*
    public static JSONObject xml2Json(String xml, boolean excludeEsbNode, String[] removeNodes) throws JSONException {
        // 去除多余节点
        if (removeNodes != null) {
            for (String node : removeNodes) {
                xml = xml.replaceAll(node, "");
            }
        }
        return xml2Json(xml, excludeEsbNode);
    }*/

    /**
     * 截取xml某个节点的内容
     * note:
     * 如果不需要截取 这node 为 null 即可
     *
     * @param xml  需要处理的原xml 字符串
     * @param node 需要截取xml的节点，如：request
     * @return 返回指定节点内的XML内容
     */
    public static String substringByNode(String xml, String node) {
        if (StringUtils.isBlank(xml) || node == null) {
            return xml;
        }
        String prefixString = "<" + node + ">";
        String suffixString = "</" + node + ">";

        if (!xml.contains(prefixString) || !xml.contains(suffixString)) {
            return xml;
        }

        int prefixIndex = xml.indexOf(prefixString);
        int suffixIndex = xml.indexOf(suffixString);

        return xml.substring(prefixIndex + prefixString.length(), suffixIndex);
    }

    /**
     * 获取xml的请求方法,即是ns1节点内的方法名称
     *
     * @param xml 需要处理的原xml 字符串
     * @return 报文内的请求方法, 如果获取不到返回null
     */
    public static String subStringMethod(String xml) {

        // 获取body内容
        String body = substringByNode(xml, "soap:Body");

        if (StringUtils.isBlank(body)) {
            return null;
        }

        String ns1 = "</ns1:";
        if (!xml.contains(ns1)) {
            return null;
        }

        int prefixIndex = body.indexOf(ns1);
        String result = body.substring(prefixIndex);

        return result.replace(ns1, "")
                .replace(">", "").trim();
    }
}
