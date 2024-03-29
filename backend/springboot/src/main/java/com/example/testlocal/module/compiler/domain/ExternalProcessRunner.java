package com.example.testlocal.module.compiler.domain;

import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// 외부 프로세스 실행하는 클래스
@Slf4j
public class ExternalProcessRunner{

    private final long TIMEOUT_SECONDS = 3;
    private List<String> command;
    private ProcessExecutor processExecutor;
    private File file;
    private String response; // 응답 값

    public ExternalProcessRunner(List<String> command) {
        this.command = command;
        this.processExecutor = new ProcessExecutor();
        this.file = new File("./"); // 외부 프로세스 실행할 위치
    }

    public String execute() throws IOException, InterruptedException, TimeoutException {
        try {
            response = processExecutor
                    .directory(file)
                    .command(command)
                    .timeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .redirectErrorStream(true)
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
        }
        catch (InvalidExitValueException e) {
            response = e.getResult().outputUTF8();
        }

        return response;
    }

    public void setCommand(List<String> command) {
        this.command = command;
    }

}
