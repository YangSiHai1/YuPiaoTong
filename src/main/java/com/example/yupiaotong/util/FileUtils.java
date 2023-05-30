package com.example.yupiaotong.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class FileUtils {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readJsonFile() {
        Resource resource = resourceLoader.getResource("classpath:station.json");

        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            // 处理文件内容
        } catch (IOException e) {
            // 处理读取文件异常
        }
        return "null";
    }

}
