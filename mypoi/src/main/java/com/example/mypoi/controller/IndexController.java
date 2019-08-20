package com.example.mypoi.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.mypoi.bean.MyBean;
import com.example.mypoi.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * index
 *
 * @author: Quifar
 * @version: 1.0
 */
@RestController
public class IndexController {

    @GetMapping("/index/{name}")
    public String index(@PathVariable String name) {
        return "hello ".concat(name);
    }

    /**
     * 数据源
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excel", produces = {"application/octet-stream;charset=UTF-8"})
    public void excel(HttpServletResponse response) throws IOException {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        //writer.addHeaderAlias()
        //out为OutputStream，需要写出到的目标流

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 从本地获取输入流
     * Apache POI 插入图片至 Excel 的两种方法
     * 1.将图形插入到sheet中的指定坐标中
     * 2.将图形插入到sheet中的cell中
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excelPic", produces = {"application/octet-stream;charset=UTF-8"})
    public void excelPic(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter();
        HSSFWorkbook workbook = (HSSFWorkbook) writer.getWorkbook();
        HSSFSheet sheet = (HSSFSheet) writer.getSheet();

        BufferedInputStream inputStream = FileUtil.getInputStream("f:/11.jpg");
        byte[] bytes = IoUtil.readBytes(inputStream);

        //八个主要属性
        //dx1 dy1 起始单元格中的x,y坐标.
        //dx2 dy2 结束单元格中的x,y坐标
        //col1,row1 指定起始的单元格，下标从0开始
        //col2,row2 指定结束的单元格 ，下标从0开始
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 1, 1, (short) 5, 8);
        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
        // 插入图片
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        patriarch.createPicture(anchor, workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG));

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);

        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 插入网上的图片到excel表格中
     * Apache POI 插入图片至 Excel 的两种方法
     * 1.将图形插入到sheet中的指定坐标中
     * 2.将图形插入到sheet中的cell中
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excelPicByUrl", produces = {"application/octet-stream;charset=UTF-8"})
    public void excelPicByUrl(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter();
        HSSFWorkbook workbook = (HSSFWorkbook) writer.getWorkbook();
        HSSFSheet sheet = (HSSFSheet) writer.getSheet();
        sheet.setColumnWidth(1, 30 * 256);

        String from = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566108021258&di=fbb1baa161b0a620e98b54048c5c65f1&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110815%2F8002769_082424424381_2.jpg";
        URL url = new URL(from);
        URLConnection con = url.openConnection();
        InputStream inputStream = con.getInputStream();
        //byte[] bytes = IoUtil.readBytes(inputStream);
        BufferedImage bufferImg = ImageIO.read(inputStream);
        byte[] bytes = IoUtil.readBytes(inputStream);

        int width = bufferImg.getWidth();//原始宽度
        int height = bufferImg.getHeight();//原始高度

        height = (int) Math.round((height * (30 * 13) * 1.0 / width));
        // excel单元格高度是以点单位，1点=2像素; POI中Height的单位是1/20个点，故设置单元的等比例高度如下
        sheet.setDefaultRowHeight((short) (height / 2 * 20));

        //八个主要属性
        //dx1 dy1 起始单元格中的x,y坐标.
        //dx2 dy2 结束单元格中的x,y坐标
        //col1,row1 指定起始的单元格，下标从0开始
        //col2,row2 指定结束的单元格 ，下标从0开始
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 1, 1, (short) 5, 8);
        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);

        // 获取图形对象
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        patriarch.createPicture(anchor, workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG));

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);

        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 将图形插入到sheet中的cell中
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excelToCell", produces = {"application/octet-stream;charset=UTF-8"})
    public void excelToCell(HttpServletResponse response) throws IOException {
        String from = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566108021258&di=fbb1baa161b0a620e98b54048c5c65f1&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110815%2F8002769_082424424381_2.jpg";
        URL url = new URL(from);
        URLConnection con = url.openConnection();
        InputStream inputStream = con.getInputStream();
        byte[] bytes = IoUtil.readBytes(inputStream);

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        Workbook workbook = writer.getWorkbook();
        int pictureIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

        // anchor锚点，主要设置图片的属性
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        // 可设置图片其他属性，如大小等
        anchor.setCol1(2);
        anchor.setCol2(2);
        anchor.setRow1(1);
        anchor.setRow2(2);
        //anchor.setDx1();
        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);

        HSSFSheet sheet = (HSSFSheet) writer.getSheet();
        Drawing drawing = sheet.createDrawingPatriarch();
        Picture picture = drawing.createPicture(anchor, pictureIndex);
        picture.resize();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
    }


    /**
     * 将图形插入到sheet中的cell中
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excelByBean", produces = {"application/octet-stream;charset=UTF-8"})
    public void excelByBean(HttpServletResponse response) throws IOException {
        String from = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566108021258&di=fbb1baa161b0a620e98b54048c5c65f1&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110815%2F8002769_082424424381_2.jpg";
        URL url = new URL(from);
        URLConnection con = url.openConnection();
        InputStream inputStream = con.getInputStream();
        byte[] bytes = IoUtil.readBytes(inputStream);

//        MyBean myBean1 = new MyBean(1, "name1", bytes);
//        MyBean myBean2 = new MyBean(2, "name2", bytes);
//        MyBean myBean3 = new MyBean(3, "name3", bytes);
//        MyBean myBean4 = new MyBean(4, "name4", bytes);
//
//        List<MyBean> data = CollUtil.newArrayList(myBean1, myBean2, myBean3, myBean4);

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        Workbook workbook = writer.getWorkbook();
        int pictureIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

        // anchor锚点，主要设置图片的属性
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        // 可设置图片其他属性，如大小等
        anchor.setCol1(2);
        anchor.setCol2(2);
        anchor.setRow1(1);
        anchor.setRow2(2);
        //anchor.setDx1();
        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);

        HSSFSheet sheet = (HSSFSheet) writer.getSheet();
        Drawing drawing = sheet.createDrawingPatriarch();
        Picture picture = drawing.createPicture(anchor, pictureIndex);
        picture.resize();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
    }

    @GetMapping(value = "/test", produces = {"application/octet-stream;charset=UTF-8"})
    public void test(HttpServletResponse response) {
        String title = "人员";
        String[] rowsName = new String[]{"序号", "头像", "昵称", "姓名"};

        //查询需要导出的数据
        Map<String, Object> map1 = new HashMap<>();
        map1.put("headUrl", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566108021258&di=fbb1baa161b0a620e98b54048c5c65f1&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110815%2F8002769_082424424381_2.jpg");
        map1.put("nickname", "大哥");
        map1.put("realName", "dagege");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("headUrl", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566108021258&di=fbb1baa161b0a620e98b54048c5c65f1&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110815%2F8002769_082424424381_2.jpg");
        map2.put("nickname", "大哥2");
        map2.put("realName", "dagege2");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("headUrl", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566108021258&di=fbb1baa161b0a620e98b54048c5c65f1&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110815%2F8002769_082424424381_2.jpg");
        map3.put("nickname", "大哥3");
        map3.put("realName", "dagege3");

        List<Map<String, Object>> maps = CollUtil.newArrayList(map1, map2, map3);

        List<Object[]> dataList = new ArrayList<>();
        Object[] objs = null;
        for (int i = 0; i < maps.size(); i++) {
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = maps.get(i).get("headUrl");   //头像url
            objs[2] = maps.get(i).get("nickname");
            objs[3] = maps.get(i).get("realName");

            dataList.add(objs);
        }
        ExportExcelUtils ex = new ExportExcelUtils(title, rowsName, dataList, response);
        ex.exportData();
    }


}
