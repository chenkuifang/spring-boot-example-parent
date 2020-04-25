package com.example.demo.controller;

import com.example.demo.sftp.SFTPClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 使用sftp上传到指定的服务图片服务器
 *
 * @author quifar
 */
@Slf4j
@Controller
@RequestMapping("/uploadSftp")
public class UploadSftpController {

    private String fileName;

    @Autowired
    private SFTPClientUtils sftpClientUtils;

    @Autowired
    private SftpRemoteFileTemplate sftpRemoteFileTemplate;

    @Autowired
    private SessionFactory sftpSessionFactory;

    @GetMapping("/list")
    @ResponseBody
    public String list() throws IOException {
        Session session = sftpSessionFactory.getSession();
        Object[] list = session.list("/");
        return Arrays.toString(list);
    }

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
        return "uploadsftp";
    }

    @PostMapping()
    public String upload(@RequestParam("file") MultipartFile file, HttpServletResponse response, Model model) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();

        SftpSession session = (SftpSession)sftpSessionFactory.getSession();

        session.write(file.getInputStream(),"/upload");

        //保存到数据库的路径
        model.addAttribute("imgPath", "/img/" + fileName);
        return "uploadsftp";
    }

    @GetMapping("/test")
    @ResponseBody
    public String testUpload() throws IOException {
        sftpClientUtils.upload();
        return "test";
    }

}
