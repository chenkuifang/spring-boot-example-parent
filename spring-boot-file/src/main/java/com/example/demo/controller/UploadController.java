package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 上传测试
 *
 * @author quifar
 */
@Slf4j
@Controller
@RequestMapping("/upload")
public class UploadController {

    private String fileName;

    /**
     * 使用spring boot 工程文件上传和下载
     * 1.简单的一种方式，文件和部署jar放在同一个服务器；
     * 直接指定文件上传的路径，然后重新定义spring boot的静态资源路径
     * 2.如果文件和jar在不同的服务器，可使用ftp上传+nginx做下载
     *
     * @return
     */
    @GetMapping()
    public String upload() {
        return "upload";
    }

    @PostMapping()
    public String upload(@RequestParam("file") MultipartFile file, HttpServletResponse response, Model model) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "E://upload-imgs/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            this.fileName = fileName;
        } catch (IOException e) {
            log.error(e.toString(), e);
            response.getWriter().write("上传失败");
        }

        //保存到数据库的路径
        model.addAttribute("imgPath", "/img/" + fileName);
        return "upload";
    }

}
