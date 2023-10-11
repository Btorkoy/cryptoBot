package org.cryptobot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChartService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String CRYPTO_WATCH_URL = "https://cryptowatch.net/?";

    private static final String URL_PREFIX = "chart=BINANCE:";
    private static final String URL_SUFFIX = ".P";

    public static List<ChartEntity> getCharts() throws JsonProcessingException {
        var data = getData();
        List<ChartEntity> charts = new ArrayList<>();
        for (ChartEntity datum : data) {
            if (datum.getCount() >= 500000) {
                charts.add(datum);
            }
        }

        charts.sort(Comparator.comparing(ChartEntity::getCount).reversed());

        return charts;
    }

    public static String assembleUrl(List<ChartEntity> charts) {
        StringBuilder url = new StringBuilder(CRYPTO_WATCH_URL);
        charts.stream()
                .map(ChartEntity::getSymbol)
                .forEach(symbol -> url.append(URL_PREFIX)
                        .append(symbol)
                        .append(URL_SUFFIX)
                        .append("&"));

        return url.toString();
    }

    private static ChartEntity[] getData() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://fapi.binance.com/fapi/v1/ticker/24hr"))
                    .build();
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
            System.out.println("Headers: " + response.headers().allValues("content-type"));
            System.out.println("Body: " + response.body());


            return objectMapper.readValue(response.body(), ChartEntity[].class);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ChartEntity[0];
    }
}
