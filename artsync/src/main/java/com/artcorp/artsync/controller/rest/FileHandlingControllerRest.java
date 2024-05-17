package com.artcorp.artsync.controller.rest;

import com.artcorp.artsync.exception.domain.FileFormatException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static com.artcorp.artsync.constant.FileConstant.*;
import static org.springframework.http.MediaType.*;

@RestController
public class FileHandlingControllerRest {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PostMapping("/api/chat/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException, FileFormatException {
        if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(file.getContentType())){
            throw new FileFormatException("/utilisateur/conversation");
        }
        File parentDir = new File(MEDIA_CHAT_BASE_FOLDER);
        parentDir.mkdirs();
        File saveFile = new File(parentDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
        saveFile.mkdirs();
        Files.copy(file.getInputStream(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        file.getInputStream().close();
    }

    @GetMapping("/media/chat/{image}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getImageChat(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        return readFile(fileName, MEDIA_CHAT_BASE_FOLDER);
    }

    @GetMapping("/media/images/{image}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getGeneralImage(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        return readFile(fileName, GENERAL_FOLDER);
    }

    @GetMapping("/media/images/utilisateur/{image}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getImage(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        return readFile(fileName, USER_FOLDER);
    }

    @GetMapping("/media/images/post/{image}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getImagePost(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        return readFile(fileName, POST_FOLDER);
    }

    @GetMapping("/media/fichier/groupe/{fichier}")
    public void getFichierGroupe(@PathVariable("fichier") String fileName, HttpServletResponse response) throws IOException {
        readFileGroupe(fileName, response, FICHIER_GROUPE);
    }

    private ResponseEntity<FileSystemResource> readFile( String fileName, String baseFolder) throws IOException {
        File dir = new File(baseFolder);
        File file = new File(dir.getAbsolutePath() + File.separator + fileName);

        if (file.exists()) {
            FileSystemResource resource = new FileSystemResource(file);
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentDisposition(ContentDisposition.parse("inline; filename=\"" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8) + "\""));
            responseHeaders.setContentType(MediaType.valueOf(mimeType));
            responseHeaders.setContentLength(resource.contentLength());

           return new ResponseEntity<>(resource,responseHeaders, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void readFileGroupe(String fileName, HttpServletResponse response, String baseFolder) throws IOException {
        File dir = new File(baseFolder);
        File file = new File(dir.getAbsolutePath() + File.separator + fileName);

        if (file.exists()) {
            String fileExtension = com.google.common.io.Files.getFileExtension(file.getName());
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            if(Arrays.asList(DOWNLOADABLE_FILES).contains(fileExtension)){
                response.setHeader("Content-Disposition", "attachement; filename=\"" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8) + "\"");
            }else{
                response.setHeader("Content-Disposition", "inline; filename=\"" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8) + "\"");
            }
            response.setContentType(mimeType);

            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                in.close();
                out.flush();
                out.close();
            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
            }
        }
    }

    @ExceptionHandler(IOException.class)
    private void handleIOException(IOException ioException){
        LOGGER.error(ioException.getMessage());
    }
}
