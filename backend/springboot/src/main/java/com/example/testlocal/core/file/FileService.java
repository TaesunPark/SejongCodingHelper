package com.example.testlocal.core.file;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {

    // 파일 존재하면 삭제
    public void deleteFileIfExists(String path) throws IOException {
        Files.deleteIfExists(Path.of(path));
    }

    // 파일 생성
     public synchronized void createFile(String text, String path){
        try (OutputStream outputStream = Files.newOutputStream(Path.of(path))) {
            byte[] bytes = text.getBytes();
            outputStream.write(bytes);
        } catch (IOException e) {
            System.out.println(path);
            System.out.println(text);
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


}
