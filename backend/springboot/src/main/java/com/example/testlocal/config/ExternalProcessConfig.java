package com.example.testlocal.config;

import com.example.testlocal.core.file.FilePathEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExternalProcessConfig {

    @Bean
    public List<String> gccCommandList(){
        ArrayList<String> commends = new ArrayList<String>();
        commends.add("gcc");
        commends.add(FilePathEnum.C_FILE_PATH.getPath());
        commends.add("-o");
        commends.add(FilePathEnum.C_EXECUTION_PATH.getPath());
        return commends;
    }

    @Bean
    public List<String> executionCFileCommandList(){
        List commends = new ArrayList<String>();
        commends.add(FilePathEnum.C_EXECUTION_PATH.getPath());
        return commends;
    }

    @Bean
    public List<String> python3CommandList(){
        List commends = new ArrayList<String>();
        commends.add("/bin/sh");
        commends.add("-c");
        commends.add("python3 "+FilePathEnum.PYTHON_FILE_PATH.getPath()+"<"+FilePathEnum.PYTHON_INPUT_TEXT_FILE_PATH.getPath());
        return commends;
    }



}
