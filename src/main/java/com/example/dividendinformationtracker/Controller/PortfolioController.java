package com.example.dividendinformationtracker.Controller;

import com.example.dividendinformationtracker.Model.Portfolio;
import com.example.dividendinformationtracker.Model.PortfolioDto;
import com.example.dividendinformationtracker.Service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/createPortfolio")
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody PortfolioDto dto) {
        Portfolio portfolio = portfolioService.CreatePortfolio(dto.getPortfolioName(), dto.getStocks());

        if(portfolio ==null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Portfolio with this name already exists");
        }

        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }

    @GetMapping("/getPortfolio")
    public ResponseEntity<Portfolio> getPortfolio(@RequestParam String portfolioName) {
        Portfolio portfolio = portfolioService.GetPortfolioByName(portfolioName);

        if(portfolio == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found");
        }

        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }
}
