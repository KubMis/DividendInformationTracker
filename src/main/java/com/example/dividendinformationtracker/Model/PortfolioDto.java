package com.example.dividendinformationtracker.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioDto {
    private String portfolioName;
    private List<String> stocks;
}

