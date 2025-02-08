package com.example.dividendinformationtracker.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Stream;
import com.example.dividendinformationtracker.Model.Ticker;
import com.example.dividendinformationtracker.Repository.TickerRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFetcherService {
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");
    private final String BASE_URL = "https://www.alphavantage.co";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    @Autowired
    private TickerRepository tickerRepository;

    private JSONObject SendGetRequest(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + url + "&apikey=" + API_KEY))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() > 200) {
            JSONObject.quote("There is a problem with external api");
        }

        System.out.println(response.body());
        return new JSONObject(response.body());
    }

    public String GetDividendYieldForCompanyTicker(String CompanyTicker) throws IOException, InterruptedException {
        JSONObject jsonObject = this.SendGetRequest("/query?function=OVERVIEW&symbol=" + CompanyTicker);

        return jsonObject.optString("DividendYield", "N/A");
    }

    public String GetCurrentSharePriceForCompanyTicker(String CompanyTicker) throws IOException, InterruptedException {
        JSONObject jsonObject = this.SendGetRequest("/query?function=GLOBAL_QUOTE&symbol=" + CompanyTicker);

        return jsonObject.getJSONObject("Global Quote").getString("05. price");
    }

    public Float CalculateDividendAmount(String CompanyTicker, int NumberOfShares) throws IOException, InterruptedException {
        float dividendYield = Float.parseFloat(this.GetDividendYieldForCompanyTicker(CompanyTicker));
        float companySharePrice = Float.parseFloat(this.GetCurrentSharePriceForCompanyTicker(CompanyTicker));
        return companySharePrice * dividendYield * NumberOfShares;
    }

    public void SaveActiveTickersToDb() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/query?function=LISTING_STATUS&apikey=" + API_KEY))
                .GET()
                .build();

        HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());

        System.out.println(response.statusCode());

        List<String> lines = response.body().toList();

        if (response.statusCode() > 200 || lines.isEmpty()) {
            System.out.println("There is a problem with external api");
        } else {
            lines.stream()
                    .filter(line -> line.toUpperCase().contains("ACTIVE"))
                    .map(line -> line.substring(0, line.indexOf(',')))
                    .forEach(tickerSymbol -> {
                        if (tickerRepository.findByTicker(tickerSymbol) == null || !tickerRepository.findByTicker(tickerSymbol).getTicker().equals(tickerSymbol)) {
                            Ticker ticker = new Ticker();
                            ticker.setTicker(tickerSymbol);
                            tickerRepository.save(ticker);
                            System.out.println("Added ticker " + tickerSymbol + " to db");
                        }
                    });
        }
    }

    public List<String> GetActiveTickers() {
        List<Ticker> tickers = tickerRepository.findAll();
        return tickers.stream().map(Ticker::getTicker).toList();
    }
}