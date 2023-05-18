package com.example.testlocal.module.compiler.application.service;

import com.example.testlocal.core.file.FilePathEnum;
import com.example.testlocal.core.file.FileService;
import com.example.testlocal.module.compiler.domain.ExternalProcessRunner;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.util.concurrent.TimeoutException;

@Service

public class CompilerService {
    private final List<String> gccCommandList;
    private final List<String> executionCFileCommandList;
    private final List<String> python3CommandList;
    private final FileService fileService;

    public CompilerService(List<String> gccCommandList, List<String> executionCFileCommandList, List<String> python3CommandList, FileService fileService) {
        this.gccCommandList = gccCommandList;
        this.executionCFileCommandList = executionCFileCommandList;
        this.python3CommandList = python3CommandList;
        this.fileService = fileService;
    }

    @Transactional
    public String executeGccCompiler(String code, String input) throws IOException, InterruptedException, TimeoutException {
        fileService.deleteFileIfExists(FilePathEnum.C_INPUT_TEXT_FILE_PATH.getPath());
        fileService.deleteFileIfExists(FilePathEnum.C_FILE_PATH.getPath());
        fileService.deleteFileIfExists(FilePathEnum.C_EXECUTION_FILE_PATH.getPath());
        fileService.createFile(code, FilePathEnum.C_FILE_PATH.getPath());
        fileService.createFile(input, FilePathEnum.C_INPUT_TEXT_FILE_PATH.getPath());

        String result = "파일 없음";

        if (Files.exists(Path.of(FilePathEnum.C_FILE_PATH.getPath())) && Files.exists(Path.of(FilePathEnum.C_INPUT_TEXT_FILE_PATH.getPath()))) {
            ExternalProcessRunner runner = new ExternalProcessRunner(gccCommandList);
            result = runner.execute();
            // 컴파일 실패해서 파일 없으면 동작 x
            if (Files.exists(Path.of(FilePathEnum.C_EXECUTION_FILE_PATH.getPath()))) {
                runner.setCommand(executionCFileCommandList);
                result = runner.execute();
            }
        } else{
            System.out.println("파일 없음");
            // exeception 처리
        }
        return result;
    }

    @Transactional
    public String executePythonCompiler(String code, String input) throws IOException, InterruptedException, TimeoutException {
        String result = "파일 없음";
        fileService.deleteFileIfExists(FilePathEnum.PYTHON_FILE_PATH.getPath());
        fileService.deleteFileIfExists(FilePathEnum.PYTHON_INPUT_TEXT_FILE_PATH.getPath());
        fileService.createFile(code, FilePathEnum.PYTHON_FILE_PATH.getPath());
        fileService.createFile(input, FilePathEnum.PYTHON_INPUT_TEXT_FILE_PATH.getPath());

        if (Files.exists(Path.of(FilePathEnum.PYTHON_FILE_PATH.getPath())) && Files.exists(Path.of(FilePathEnum.PYTHON_INPUT_TEXT_FILE_PATH.getPath()))) {
            ExternalProcessRunner runner = new ExternalProcessRunner(python3CommandList);
            result = runner.execute();
        } else{
            System.out.println("파일 없음");
            // exeception 처리
        }
        return result;
    }



}
