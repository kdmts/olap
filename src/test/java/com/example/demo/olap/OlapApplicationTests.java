package com.example.demo.olap;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class OlapApplicationTests {

    private final static Logger logger = LoggerFactory.getLogger(OlapApplicationTests.class);

    @Value("${data.file.name}")
    Resource resource;

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkThatDataFileExist() throws IOException {
        Assertions.assertThat(resource.getFile()).isNotNull();
        Assertions.assertThat(resource.getFile()).isFile();
    }

}
