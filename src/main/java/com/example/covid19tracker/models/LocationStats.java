package com.example.covid19tracker.models;

public class LocationStats {
    private String countOrReg;
    private String provOrState;
    private int latestTotalCases;
    private int diffFromPreviousDay;

    public int getDiffFromPreviousDay() {
        return diffFromPreviousDay;
    }

    public void setDiffFromPreviousDay(int diffFromPreviousDay) {
        this.diffFromPreviousDay = diffFromPreviousDay;
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

}
