package com.example.demo.olap;

import com.example.demo.olap.service.OlapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class OlapApplicationCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(OlapApplicationCommandLineRunner.class);

    @Autowired
    @Qualifier("defaultImpl")
    OlapService olapService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Processing data file...");
        olapService.getUniquePagesIds();
        logger.info("Unique page ids are stored in cache.");
    }
}
