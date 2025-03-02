package com.example.dividendinformationtracker.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(?!\\s*$)[\\p{L}\\p{N} ]+$", message = "The name must contain letters or digits ")
    private String portfolioName;
    private Float totalDividendAmount;
    private Float totalPortfolioValue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="portfolio")
    @JsonManagedReference
    private List<Stock> stocks;
}