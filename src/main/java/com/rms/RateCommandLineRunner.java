package com.rms;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rms.service.RateService;
import com.rms.vo.RateVO;

@Component
public class RateCommandLineRunner implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(RateCommandLineRunner.class);

    @Autowired
    private RateService rateService;

    @Override
    public void run(String... args) throws Exception {
        rateService.saveRate(new RateVO(null, "Rate Description 1", LocalDate.now(), LocalDate.now(), 100));
        log.debug("Rate Entity is saved");
    }

}
