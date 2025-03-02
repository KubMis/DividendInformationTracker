package com.example.dividendinformationtracker.Service;

import com.example.dividendinformationtracker.Model.Portfolio;
import com.example.dividendinformationtracker.Model.Stock;
import com.example.dividendinformationtracker.Repository.PortfolioRepository;
import com.example.dividendinformationtracker.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private StockRepository stockRepository;

    public Portfolio CreatePortfolio(String portfolioName, List<String> tickers) {
        if (portfolioRepository.existsByPortfolioName(portfolioName)) {
            System.out.println("Portfolio with this name already exists");
            return null;
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioName(portfolioName);
        portfolio.setTotalDividendAmount(0.0f);
        portfolio.setTotalPortfolioValue(0.0f);
        portfolioRepository.save(portfolio);

        List<Stock> stocks = tickers.stream().map(x -> {
            Stock stock = new Stock();
            stock.setTicker(x);
            stock.setName("");
            stock.setDividendYield(0.0f);
            stock.setSharePrice(0.0f);
            stock.setQuantity(0);
            stock.setPortfolio(portfolio);
            return stockRepository.save(stock);
        }).toList();

        portfolio.setStocks(stocks);
        return portfolio;
    }

    public Portfolio GetPortfolioByName(String portfolioName) {
        if(portfolioRepository.existsByPortfolioName(portfolioName)) {
            return portfolioRepository.findByPortfolioName(portfolioName);
        } else {
            System.out.println("Portfolio does not exist");
            return null;
        }
    }
}
