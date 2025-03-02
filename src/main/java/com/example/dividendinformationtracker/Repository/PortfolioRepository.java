package com.example.dividendinformationtracker.Repository;
import com.example.dividendinformationtracker.Model.Portfolio;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    @SQL("SELECT * FROM Portfolio WHERE portfolioName = portfolioName.IgnoredCase")
    boolean existsByPortfolioName(String portfolioName);
    Portfolio findByPortfolioName(String portfolioName);
}
