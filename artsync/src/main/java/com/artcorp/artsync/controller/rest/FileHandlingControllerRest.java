package com.artcorp.artsync.controller.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.artcorp.artsync.constant.FileConstant.MEDIA_CHAT_BASE_FOLDER;
import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@RestController
public class FileHandlingControllerRest {
    @PostMapping("/api/chat/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File parentDir = new File(MEDIA_CHAT_BASE_FOLDER);
        parentDir.mkdirs();
        File saveFile = new File(parentDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
        saveFile.mkdirs();
        file.transferTo(saveFile);
    }

    @GetMapping("/media/chat/{image}")
    public void getImageChat(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        File dir = new File(MEDIA_CHAT_BASE_FOLDER);
        File file = new File(dir.getAbsolutePath() + File.separator + fileName);

        if (file.exists()) {
            response.setContentType("image/jpeg");

            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.flush();
            out.close();
        }
    }

    @GetMapping("/media/images/{image}")
    public void getImage(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        File dir = new File(USER_FOLDER);
        File file = new File(dir.getAbsolutePath() + File.separator + fileName);

        if (file.exists()) {
            response.setContentType("image/jpeg");

            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.flush();
            out.close();
        }
    }
}
