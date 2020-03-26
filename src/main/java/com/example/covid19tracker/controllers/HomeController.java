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
        int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffFromPreviousDay).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("lastUpdated", lastUpdatedAt);

        return "home";
    }
}
