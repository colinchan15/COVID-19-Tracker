package com.example.covid19tracker.models;

public class LocationStats {
    private String countOrReg;
    private String provOrState;
    private float latitude;
    private float longitude;
    private int latestTotalCases;
    private int diffCasesFromPreviousDay;
    private int latestTotalDeaths;
    private int diffDeathsFromPreviousDay;
    private int latestTotalRecoveries;
    private int diffRecoveriesFromPreviousDay;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getDiffCasesFromPreviousDay() {
        return diffCasesFromPreviousDay;
    }

    public void setDiffCasesFromPreviousDay(int diffCasesFromPreviousDay) {
        this.diffCasesFromPreviousDay = diffCasesFromPreviousDay;
    }

    public String getCountOrReg() {
        return countOrReg;
    }

    public void setCountOrReg(String countOrReg) {
        this.countOrReg = countOrReg;
    }

    public String getProvOrState() {
        return provOrState;
    }

    public void setProvOrState(String provOrState) {
        this.provOrState = provOrState;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getLatestTotalDeaths() {
        return latestTotalDeaths;
    }

    public void setLatestTotalDeaths(int latestTotalDeaths) {
        this.latestTotalDeaths = latestTotalDeaths;
    }

    public int getLatestTotalRecoveries() {
        return latestTotalRecoveries;
    }

    public void setLatestTotalRecoveries(int latestTotalRecoveries) {
        this.latestTotalRecoveries = latestTotalRecoveries;
    }

    public int getDiffDeathsFromPreviousDay() {
        return diffDeathsFromPreviousDay;
    }

    public void setDiffDeathsFromPreviousDay(int diffDeathsFromPreviousDay) {
        this.diffDeathsFromPreviousDay = diffDeathsFromPreviousDay;
    }

    public int getDiffRecoveriesFromPreviousDay() {
        return diffRecoveriesFromPreviousDay;
    }

    public void setDiffRecoveriesFromPreviousDay(int diffRecoveriesFromPreviousDay) {
        this.diffRecoveriesFromPreviousDay = diffRecoveriesFromPreviousDay;
    }

}
