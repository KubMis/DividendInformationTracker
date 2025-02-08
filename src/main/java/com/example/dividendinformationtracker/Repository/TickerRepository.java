package com.example.dividendinformationtracker.Repository;

import com.example.dividendinformationtracker.Model.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Integer> {
    Ticker findByTicker(String ticker);
}
