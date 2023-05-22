package com.example.testlocal.module.compiler.application.service;

import com.example.testlocal.module.compiler.application.dto.CompilerRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
@WebAppConfiguration
@SpringBootTest
class CompilerServiceTest {

    @Autowired
    private CompilerService compilerService;


    @Test
    public void GCC_컴파일러_실행() throws IOException, InterruptedException, TimeoutException {
        CompilerRequest compilerRequest = new CompilerRequest();
        compilerRequest.setCode("#include<stdio.h> \n int main(){int a = 2; printf(\"%d\",a);}");
        compilerRequest.setInput("");
        String result = compilerService.executeGccCompiler(compilerRequest.getCode(), compilerRequest.getInput());
        Assertions.assertThat(result).isEqualTo("2");
    }

    @Test
    public void Python_컴파일러_실행() throws IOException, InterruptedException, TimeoutException {
        CompilerRequest compilerRequest = new CompilerRequest();
        compilerRequest.setCode("print(1)");
        compilerRequest.setInput("");
        String result = compilerService.executePythonCompiler(compilerRequest.getCode(), compilerRequest.getInput());
        Assertions.assertThat(result).isEqualTo("1\n");
    }


}