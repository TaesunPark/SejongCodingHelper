package com.example.testlocal.module.compiler.presentation.controller;

import com.example.testlocal.core.security.jwt.JwtTokenProvider;
import com.example.testlocal.module.compiler.application.dto.CompilerRequest;
import com.example.testlocal.module.compiler.application.service.CompilerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(CompilerController.class)
public class CompilerControllerTest {

    @MockBean
    private CompilerService compilerService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void GCC_실행_컨트롤러_테스트() throws Exception {
        CompilerRequest compilerRequest = new CompilerRequest();
        compilerRequest.setCode("#include<stdio.h> \n int main(){int a = 1; printf(\"%d\",a);}");
        compilerRequest.setInput("");

        Mockito.when(compilerService.executeGccCompiler(compilerRequest.getCode(), compilerRequest.getInput()))
                .thenReturn("1\n");

        this.mockMvc
                .perform(
                        post("/compiler/c")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(compilerRequest))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("1\n"));
    }

    @Test
    void Python3_실행_컨트롤러_테스트() throws Exception {
        CompilerRequest compilerRequest = new CompilerRequest();
        compilerRequest.setCode("print(1)");
        compilerRequest.setInput("");

        Mockito.when(compilerService.executePythonCompiler(compilerRequest.getCode(), compilerRequest.getInput()))
                .thenReturn("1\n");

        this.mockMvc
                .perform(
                        post("/compiler/python")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(compilerRequest))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("1\n"));
    }


}