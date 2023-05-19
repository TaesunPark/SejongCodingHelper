package com.example.testlocal.module.compiler.presentation.controller;

import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.compiler.application.dto.CompilerRequest;
import com.example.testlocal.module.compiler.application.dto.CompilerResponse;
import com.example.testlocal.util.Constants;
import com.example.testlocal.module.compiler.application.service.CompilerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class CompilerController {
    private final CompilerService compilerService;

    @PostMapping(value = "/compiler/c", produces = "application/json; charset=UTF-8")
    public ResponseEntity<SuccessResponse<CompilerResponse>> executeGCCCompiler(@RequestBody CompilerRequest compilerRequest) throws IOException, InterruptedException, TimeoutException {
        return SuccessResponse.success(SuccessCode.COMPILER_GCC_SUCCESS, CompilerResponse.of(true,compilerService.executeGccCompiler(compilerRequest.getCode(), compilerRequest.getInput())));
    }

    @PostMapping(value = "/compiler/python", produces = "application/json; charset=UTF-8")
    public ResponseEntity<SuccessResponse<CompilerResponse>> executePython3(@RequestBody CompilerRequest compilerRequest) throws IOException, InterruptedException, TimeoutException {
        return SuccessResponse.success(SuccessCode.COMPILER_PYTHON_SUCCESS, CompilerResponse.of(true,compilerService.executePythonCompiler(compilerRequest.getCode(), compilerRequest.getInput())));
    }


}
