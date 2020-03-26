package com.example.covid19tracker.services;

import com.example.covid19tracker.models.LocationStats;
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
import java.util.Date;

@Service
public class CoronavirusDataService {

    private static final String CONFIRMED_GLOBAL_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String DEATHS_GLOBAL_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static final String RECOVERED_GLOBAL_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    private ArrayList<LocationStats> allStats = new ArrayList<>();
    private String lastUpdatedAt;

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public ArrayList<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ArrayList<LocationStats> confirmedData = fetchConfirmedData(client);
        ArrayList<LocationStats> deathData = fetchDeathsData(client, confirmedData);
        ArrayList<LocationStats> recoveryData = fetchRecoveriesData(client, deathData);
        lastUpdatedAt = new Date().toString();
        allStats = recoveryData;
    }

    private Iterable<CSVRecord> getRecords(HttpClient client, String dataset) throws IOException, InterruptedException {
        HttpRequest confirmedRequest = HttpRequest.newBuilder().uri(URI.create(dataset)).build();
        HttpResponse confirmedResponse = client.send(confirmedRequest, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(confirmedResponse.body().toString());
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
    }

    private ArrayList<LocationStats> fetchDeathsData(HttpClient client, ArrayList<LocationStats> data) throws IOException, InterruptedException {
        Iterable<CSVRecord> records = getRecords(client, DEATHS_GLOBAL_DATA);
        for (CSVRecord record : records) {
            int latestDeaths = !record.get(record.size() - 1).isEmpty() ? Integer.parseInt(record.get(record.size() - 1)) : 0;
            int previousDayDeaths = !record.get(record.size() - 2).isEmpty() ? Integer.parseInt(record.get(record.size() - 2)) : 0;
            for (LocationStats row : data) {
                if (row.getLatitude() == Float.parseFloat(record.get("Lat")) && row.getLongitude() == Float.parseFloat(record.get("Long"))) {
                    row.setLatestTotalDeaths(latestDeaths);
                    row.setDiffDeathsFromPreviousDay(latestDeaths - previousDayDeaths);
                }
            }
        }
        return data;
    }

    private ArrayList<LocationStats> fetchRecoveriesData(HttpClient client, ArrayList<LocationStats> data) throws IOException, InterruptedException {
        Iterable<CSVRecord> records = getRecords(client, RECOVERED_GLOBAL_DATA);
        for (CSVRecord record : records) {
            int latestRecoveries = !record.get(record.size() - 1).isEmpty() ? Integer.parseInt(record.get(record.size() - 1)) : 0;
            int previousDayRecoveries = !record.get(record.size() - 2).isEmpty() ? Integer.parseInt(record.get(record.size() - 2)) : 0;
            for (LocationStats row : data) {
                if (row.getLatitude() == Float.parseFloat(record.get("Lat")) && row.getLongitude() == Float.parseFloat(record.get("Long"))) {
                    row.setLatestTotalRecoveries(latestRecoveries);
                    row.setDiffRecoveriesFromPreviousDay(latestRecoveries - previousDayRecoveries);
                }
            }
        }
        return data;
    }

    private ArrayList<LocationStats> fetchConfirmedData(HttpClient client) throws IOException, InterruptedException {
        ArrayList<LocationStats> newStats = new ArrayList<>();
        Iterable<CSVRecord> records = getRecords(client, CONFIRMED_GLOBAL_DATA);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            int latestCases = !record.get(record.size() - 1).isEmpty() ? Integer.parseInt(record.get(record.size() - 1)) : 0;
            int previousDayCases = !record.get(record.size() - 2).isEmpty() ? Integer.parseInt(record.get(record.size() - 2)) : 0;

            locationStat.setCountOrReg(record.get("Country/Region"));
            locationStat.setProvOrState(record.get("Province/State").equals("") ? "Entire Country" : record.get("Province/State"));
            locationStat.setLatitude(Float.parseFloat(record.get("Lat")));
            locationStat.setLongitude(Float.parseFloat(record.get("Long")));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffCasesFromPreviousDay(latestCases - previousDayCases);
            newStats.add(locationStat);
        }
        return newStats;
    }
}
