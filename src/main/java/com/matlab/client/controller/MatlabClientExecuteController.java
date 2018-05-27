package com.matlab.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RestController
public class MatlabClientExecuteController {

    @GetMapping("/v1/test")
    public String getTestString() {
        return "Hello!";
    }

    @GetMapping("v1/matlab")
    public String getMatlabResults() throws IOException, InterruptedException {
        String command = "C:\\Users\\User\\IdeaProjects\\matlab\\vectorAdd.exe";
        command = "\"C:\\Program Files\\MATLAB\\R2017a\\bin\\win64\\MATLAB.exe\" -nodisplay -nosplash -nodesktop -r \"run('C:\\Users\\User\\Documents\\Golovashkin\\China2DMPITestGPUParallel.m');exit;\"";
        Runtime run  = Runtime.getRuntime();
        Process process = run.exec(command);
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        System.out.println(exitCode);
        return null;
        //maybe still runniing after complete code
    }
    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }
}
