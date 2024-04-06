package de.dennisguse.opentracks.ui.aggregatedStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import de.dennisguse.opentracks.data.models.Speed;

/*
    NEED dynamic data from Group 7 -- need to verify the difference between Chairlift and Skilift.
 */

public class LiftStats {
    private String test;        //testing purpose

    private String liftName;
    private Speed avgSpeed;
    private Duration totalTime;
    private Duration movingTime;
    private double altitudeGain;

    public LiftStats(String liftName, Speed avgSpeed, Duration totalTime, Duration movingTime, double altitudeGain) {
        this.liftName = liftName;
        this.avgSpeed = avgSpeed;
        this.totalTime = totalTime;
        this.movingTime = movingTime;
        this.altitudeGain = altitudeGain;
    }

    public LiftStats(LiftStats liftStats){
        this.liftName = liftStats.liftName;
        this.avgSpeed = liftStats.avgSpeed;
        this.totalTime = liftStats.totalTime;
        this.movingTime = liftStats.movingTime;
        this.altitudeGain = liftStats.altitudeGain;
    }

    //Test

    public LiftStats(String liftName, double altitudeGain) {
        this.liftName = liftName;
        this.altitudeGain = altitudeGain;
    }

    public String getTest() {
        return test;
    }

    public String getLiftName() {
        return liftName;
    }

    public void setLiftName(String liftName) {
        this.liftName = liftName;
    }

    public Speed getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Speed avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getTotalTime() {
        String formattedTime = formatDuration(totalTime);
        return formattedTime;
    }

    public void setTotalTime(Duration totalTime) {
        this.totalTime = totalTime;
    }

    public String getMovingTime() {
        String formattedTime = formatDuration(movingTime);
        return formattedTime;
    }

    public void setMovingTime(Duration movingTime) {
        this.movingTime = movingTime;
    }

    public double getAltitudeGain() {
        return altitudeGain;
    }

    public void setAltitudeGain(double altitudeGain) {
        this.altitudeGain = altitudeGain;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        System.out.println("valueeeeee:, " + value);
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        // Format hours, minutes and seconds to ensure they are in 00:00:00 format
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
