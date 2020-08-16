package io.jiadai.covid19tracker.services;

import io.jiadai.covid19tracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    private static String VIRUS_DATA_URL = "https://covid.ourworldindata.org/data/ecdc/total_cases.csv";
    private List<LocationStats> allStats = new ArrayList<>();
    private int worldCasesToday;
    private int usCasesToday;
    private String dataString;

    public List<LocationStats> getAllStats() {
        return this.allStats;
    }

    public int getWorldCasesToday() {
        return worldCasesToday;
    }

    public int getUsCasesToday() {
        return usCasesToday;
    }

    public String getDataString() {
        return dataString;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        String data = "[";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats stat = new LocationStats();
            String date = record.get("date");
            stat.setDate(date);
            int worldCases = Integer.parseInt(record.get("World")) ;
            stat.setWorldCases(worldCases);
            int usCases = Integer.parseInt(record.get("United States")) ;
            stat.setUsCases(usCases);
            data += ("{\"date\":\"" + date + "\",\"world\":" + worldCases + ",\"us\":" + usCases + "},");
            newStats.add(stat);
        }
        data += "]";
        this.dataString = data;
        this.allStats = newStats;
        this.worldCasesToday = this.allStats.get(this.allStats.size() - 1).getWorldCases();
        this.usCasesToday = this.allStats.get(this.allStats.size() - 1).getUsCases();
    }
}
