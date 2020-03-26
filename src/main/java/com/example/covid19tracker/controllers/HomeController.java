package com.example.covid19tracker.controllers;

import com.example.covid19tracker.models.LocationStats;
import com.example.covid19tracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;
    @GetMapping("/")
    public String home(Model model) {
        ArrayList<LocationStats> allStats = coronavirusDataService.getAllStats();
        String lastUpdatedAt = coronavirusDataService.getLastUpdatedAt();
        int totalReportedCases = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffCasesFromPreviousDay).sum();
        int totalReportedDeaths = allStats.stream().mapToInt(LocationStats::getLatestTotalDeaths).sum();
        int totalNewDeaths = allStats.stream().mapToInt(LocationStats::getDiffDeathsFromPreviousDay).sum();
        int totalReportedRecoveries = allStats.stream().mapToInt(LocationStats::getLatestTotalRecoveries).sum();
        int totalNewRecoveries = allStats.stream().mapToInt(LocationStats::getDiffRecoveriesFromPreviousDay).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalDeaths", totalReportedDeaths);
        model.addAttribute("totalNewDeaths", totalNewDeaths);
        model.addAttribute("totalRecoveries", totalReportedRecoveries);
        model.addAttribute("totalNewRecoveries", totalNewRecoveries);
        model.addAttribute("lastUpdated", lastUpdatedAt);

        return "home";
    }
}
