package com.example.dividendinformationtracker.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String portfolioName;
    private Float totalDividendAmount;
    private Float totalPortfolioValue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stock> stocks;
}