package io.jiadai.covid19tracker.models;

public class LocationStats {
    private String date;
    private int worldCases;
    private int usCases;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWorldCases() {
        return worldCases;
    }

    public void setWorldCases(int worldCases) {
        this.worldCases = worldCases;
    }

    public int getUsCases() {
        return usCases;
    }

    public void setUsCases(int usCases) {
        this.usCases = usCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "date='" + date + '\'' +
                ", worldCases=" + worldCases +
                ", usCases=" + usCases +
                '}';
    }
}
