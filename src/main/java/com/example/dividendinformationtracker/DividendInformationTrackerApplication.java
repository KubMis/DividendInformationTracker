package com.example.dividendinformationtracker;
import com.example.dividendinformationtracker.Service.DataFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class DividendInformationTrackerApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        ApplicationContext context = SpringApplication.run(DividendInformationTrackerApplication.class, args);
        DataFetcherService dataFetcherService = context.getBean(DataFetcherService.class);
        dataFetcherService.SaveActiveTickersToDb();
    }
}
