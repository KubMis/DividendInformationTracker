package com.example.dividendinformationtracker.Controller;
import com.example.dividendinformationtracker.Service.DataFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CompanyController {

    @Autowired
    private DataFetcherService dataFetcherService;

    @GetMapping("/getCompanyInfo")
    public String getDividendYield(@RequestParam String CompanyTicker) throws IOException, InterruptedException {
        return dataFetcherService.GetDividendYieldForCompanyTicker(CompanyTicker);
    }

    @GetMapping("/getCurrentSharePrice")
    public String getCurrentSharePrice(@RequestParam String CompanyTicker) throws IOException, InterruptedException {
        return dataFetcherService.GetCurrentSharePriceForCompanyTicker(CompanyTicker);
    }

    @GetMapping("/calculateDividendAmount")
    public Double calculateDividendAmount(@RequestParam String CompanyTicker, @RequestParam int NumberOfShares) throws IOException, InterruptedException {
        return dataFetcherService.CalculateDividendAmount(CompanyTicker, NumberOfShares);
    }
}

