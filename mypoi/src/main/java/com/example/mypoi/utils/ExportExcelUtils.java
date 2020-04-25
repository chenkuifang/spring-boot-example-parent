package com.example.mypoi.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用原生poi工具类
 *
 * @author: Quifar
 * @version: 1.0
 */
public class ExportExcelUtils {
    private String title; // 导出表格的表名

    private String[] rowName;// 导出表格的列名

    private List<Object[]> dataList = new ArrayList<Object[]>(); // 对象数组的List集合

    private HttpServletResponse response;

    // 传入要导入的数据
    public ExportExcelUtils(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse response) {
        this.title = title;
        this.rowName = rowName;
        this.dataList = dataList;
        this.response = response;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    // 导出数据
    public void exportData() {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel对象
            HSSFSheet sheet = workbook.createSheet(title); // 创建表格
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);  // 行
            HSSFCell cellTiltle = rowm.createCell(0);  // 单元格
            // sheet样式定义
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); // 头样式
            HSSFCellStyle style = this.getStyle(workbook);  // 单元格样式
            /**
             * 参数说明
             * 从0开始   第一行 第一列 都是从角标0开始
             * 行 列 行列    (0,0,0,5)  合并第一行 第一列  到第一行 第六列
             * 起始行，起始列，结束行，结束列
             *
             * new Region()  这个方法使过时的
             */
            // 合并第一行的所有列
            sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            int columnNum = rowName.length;  // 表格列的长度
            HSSFRow rowRowName = sheet.createRow(1);  // 在第二行创建行
            HSSFCellStyle cells = workbook.createCellStyle();
            cells.setBottomBorderColor(HSSFColor.BLACK.index);
            rowRowName.setRowStyle(cells);

            // 循环 将列名放进去
            for (int i = 0; i < columnNum; i++) {
                HSSFCell cellRowName = rowRowName.createCell((int) i);
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 单元格类型

                HSSFRichTextString text = new HSSFRichTextString(rowName[i]);  // 得到列的值
                cellRowName.setCellValue(text); // 设置列的值
                cellRowName.setCellStyle(columnTopStyle); // 样式
            }

            // 将查询到的数据设置到对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 2);//创建所需的行数
                row.setHeightInPoints(40);

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if (j == 0) {
                        // 第一列设置为序号
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);

                        if (!"".equals(obj[j]) && obj[j] != null) {
                            if (obj[j].toString().length() > 30) {
                                cell.setCellValue("  ");
                                drawPictureInfoExcel(workbook, patriarch, obj[j].toString(), i + 2);//i+2代表当前的行
                            } else {
                                cell.setCellValue(obj[j].toString());
                            }
                            //设置单元格的值
                        } else {
                            cell.setCellValue("  ");
                        }

                    }
                    cell.setCellStyle(style); // 样式
                }
            }
            //  让列宽随着导出的列长自动适应

            sheet.setColumnWidth(0, 8 * 256);  //调整第一列宽度
            sheet.setColumnWidth(1, 8 * 256);
            sheet.setColumnWidth(2, 20 * 256);  ///调整第二列宽度
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            sheet.setColumnWidth(5, 21 * 256);
            sheet.setColumnWidth(6, 20 * 256);

            if (workbook != null) {
                try {
                    // excel 表文件名
                    String fileName = title + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    String fileName11 = URLEncoder.encode(fileName, "UTF-8");
                    String fileName12 = java.net.URLDecoder.decode(fileName11, "UTF-8");
                    String headStr = "attachment; filename=\"" + fileName12 + "\"";
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    // response.setHeader("Content-Disposition", headStr);
                    response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName12.getBytes("gb2312"), "ISO8859-1"));
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void drawPictureInfoExcel(HSSFWorkbook wb, HSSFPatriarch patriarch, String pictureUrl, int rowIndex) {
        //rowIndex代表当前行
        try {
            if (pictureUrl != null) {
                URL url = new URL(pictureUrl);//获取人员照片的地址
                //打开链接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置请求方式为"GET"
                conn.setRequestMethod("GET");
                //超时响应时间为5秒
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = readInputStream(inStream);
                //anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 1, rowIndex, (short) 1,
                        rowIndex);
                //Sets the anchor type （图片在单元格的位置）
                //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                patriarch.createPicture(anchor, wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 9);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        //style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        //style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        //style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        //style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        //style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        //style.setAlignment(HSSFCellStyle.);
        //设置垂直对齐的样式为居中对齐;
        //style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;
    }

    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        //style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        //style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        //style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        //style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        //style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }
}
