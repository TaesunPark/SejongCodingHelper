package com.example.testlocal.module.compiler.domain;

import com.example.testlocal.core.file.FilePathEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;



public class ExternalProcessRunnerTest {

    @Test
    public void 외부_프로세스_커맨드로_실행() throws IOException, InterruptedException, TimeoutException {
        List<String> command = new ArrayList<>();
        command.add("pwd");
        ProcessExecutor processExecutor = new ProcessExecutor();
        String value = processExecutor.directory(new File(FilePathEnum.TEST_PATH.getPath())).command(command).redirectErrorStream(true).readOutput(true).execute().outputUTF8();
        Assertions.assertThat(value.equals(FilePathEnum.TEST_PATH.getPath()+"\n")).isTrue();
    }


}