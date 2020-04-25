package com.example.mypoi.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 描述
 *
 * @author: Quifar
 * @version: 1.0
 */
public class ImageBean {
    private String url;
    //private ByteArrayOutputStream pngByteArray;
    private double width;
    @DateTimeFormat
    private double heigth;
}
