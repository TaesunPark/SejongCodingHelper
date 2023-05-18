package com.example.testlocal.core.file;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileServiceTest {
    @Test
    public void 파일_생성_테스트(){
        // 테스트 파일
        Path path = Path.of(FilePathEnum.TEST_TEXT_FILE_PATH.getPath());

        try (OutputStream outputStream = Files.newOutputStream(path)) {
            String text = "test";
            byte[] bytes = text.getBytes();
            outputStream.write(bytes);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        Assertions.assertThat(Files.exists(path)).isEqualTo(true);
    }

    @Test
    public void 파일_삭제_테스트() throws IOException {
        Path path = Path.of(FilePathEnum.TEST_TEXT_FILE_PATH.getPath());
        Files.deleteIfExists(path);
        Assertions.assertThat(Files.exists(path)).isEqualTo(false);
    }


}