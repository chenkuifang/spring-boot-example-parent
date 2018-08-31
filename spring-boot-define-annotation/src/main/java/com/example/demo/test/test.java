package com.example.demo.test;

import org.dom4j.*;

import java.util.Iterator;
import java.util.*;

public class test {

    //存储xml元素信息的容器
    private static List<Map<String, String>> elemList = new ArrayList<>();

    public static void main(String[] args) {
        parseXml();
    }

    private static void parseXml() {
        try {
            Element root = getRootElement();
            getElementList(root);
            getListString(elemList);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取根元素
     *
     * @return
     * @throws DocumentException
     */
    private static Element getRootElement() throws DocumentException {
        Document document = DocumentHelper.parseText(getXml());
        return document.getRootElement();
    }


    /**
     * 递归遍历方法
     *
     * @param element
     */
    private static void getElementList(Element element) {
        List elements = element.elements();
        Map<String, String> map = new HashMap<>();
        if (elements.size() == 0) {
            //没有子元素
            String xpath = element.getName();
            String value = element.getTextTrim();
            map.put(xpath, value);
            elemList.add(map);
        } else {
            //有子元素
            for (Iterator it = elements.iterator(); it.hasNext(); ) {
                Element elem = (Element) it.next();
                //递归遍历
                getElementList(elem);
            }
        }
    }

    private static void getListString(List<Map<String, String>> elemList) {
        StringBuffer sb = new StringBuffer();
        for (Iterator<Map<String, String>> it = elemList.iterator(); it.hasNext(); ) {
            Map<String, String> map = it.next();
            for (Map.Entry entry : map.entrySet()) {
                System.err.println(entry.getKey() + ":" + entry.getValue());
            }
            //sb.append(map.get()).append(" = ").append(leaf.getValue()).append("\n");
        }
        //return sb.toString();
    }

    private static String getXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<return>");
        xml.append("<code>").append("200").append("</code>");
        xml.append("<msg>").append("成功").append("</msg>");
        xml.append("<body>");
        xml.append("    <response>");
        xml.append("        <infos>");
        xml.append("            <info>");
        xml.append("                <name>").append("zhanshan").append("</name>");
        xml.append("                <age>").append("18").append("</age>");
        xml.append("            </info>");
        xml.append("            <info>");
        xml.append("                <name>").append("lisi").append("</name>");
        xml.append("                <age>").append("20").append("</age>");
        xml.append("            <info>");
        xml.append("                <name>").append("wangwu").append("</name>");
        xml.append("                <age>").append("21").append("</age>");
        xml.append("            </info>");
        xml.append("            </info>");
        xml.append("        </infos>");
        xml.append("    </response>");
        xml.append("</body>");
        xml.append("</return>");
        return xml.toString();
    }
}
