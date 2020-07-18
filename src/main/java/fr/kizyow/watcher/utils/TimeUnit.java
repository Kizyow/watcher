package fr.kizyow.watcher.utils;

public enum TimeUnit {

    MINUTES("Minutes", 60),
    HEURES("Hours", 60 * 60),
    JOURS("Day", 60 * 60 * 24),
    SEMAINES("Week", 60 * 60 * 24 * 7),
    MOIS("Month", 60 * 60 * 24 * 30),
    ANNEES("Year", 60 * 60 * 24 * 30 * 12);

    private final String name;
    private final long toSecond;

    TimeUnit(String name, long toSecond){
        this.name = name;
        this.toSecond = toSecond;

    }

    public String getName(){
        return name;

    }

    public long getToSecond(){
        return toSecond;

    }

}