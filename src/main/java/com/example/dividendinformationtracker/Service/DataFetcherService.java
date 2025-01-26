package com.example.dividendinformationtracker.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DataFetcherService {
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");
    private final String BASE_URL = "https://www.alphavantage.co";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String GetDividendYieldForCompanyTicker(String CompanyTicker) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/query?function=OVERVIEW&symbol="+CompanyTicker+"&apikey="+API_KEY))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() > 200){
                return "There is a problem with external api";
            }

        JSONObject jsonObject = new JSONObject(response.body());

        return jsonObject.optString("DividendYield", "N/A");
    }

    public String GetCurrentSharePriceForCompanyTicker(String CompanyTicker) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+"/query?function=GLOBAL_QUOTE&symbol="+CompanyTicker+"&apikey="+API_KEY))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() > 200){
            return "There is a problem with external api";
        }

        JSONObject jsonObject = new JSONObject(response.body());
        System.out.println(response.body());
        return jsonObject.getJSONObject("Global Quote").getString("05. price");
    }

    public Double CalculateDividendAmount(String CompanyTicker, int NumberOfShares) throws IOException, InterruptedException {
        Double dividendYield = Double.parseDouble(this.GetDividendYieldForCompanyTicker(CompanyTicker));

        return dividendYield*NumberOfShares;
    }
}