package de.dennisguse.opentracks.ui.aggregatedStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class RunDetailStats {
    private String runName;
    private String maximumSpeed;
    private String averageSpeed;
    private Duration runDuration;
    private double totalDistance;
    private double elevation;
    private double slope;

    // Constructor with all the parameters
    public RunDetailStats(String runName, String maximumSpeed, String averageSpeed, Duration runDuration, double totalDistance, double elevation, double slope) {
        this.runName = runName;
        this.maximumSpeed = maximumSpeed;
        this.averageSpeed = averageSpeed;
        this.runDuration = runDuration;
        this.totalDistance = totalDistance;
        this.elevation = elevation;
        this.slope = slope;
    }

    // Copy constructor
    public RunDetailStats(RunDetailStats runDetailStats) {
        this(runDetailStats.runName, runDetailStats.maximumSpeed, runDetailStats.averageSpeed, runDetailStats.runDuration,
                runDetailStats.totalDistance, runDetailStats.elevation, runDetailStats.slope);
    }

    // Constructors for partial information
    public RunDetailStats(String runName, String maximumSpeed, double totalDistance) {
        this(runName, maximumSpeed, null, null, totalDistance, 0, 0);
    }

    // Getters with rounding
    public String getRunName() {return runName;}
    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public String getRunDuration() {
        return formatDuration(runDuration);
    }

    public String getTotalDistance() {
        return roundValue(totalDistance);
    }

    public String getElevation() {
        return roundValue(elevation);
    }

    public String getSlope() {
        return roundValue(slope);
    }

    // Setters
    public void setRunName(String runName){this.runName = runName;}
    public void setMaximumSpeed(String maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setRunDuration(Duration runDuration) {
        this.runDuration = runDuration;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    // Utility methods
    private String formatDuration(Duration duration) {
        if (duration == null) return "00:00:00";
        long seconds = duration.getSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    private String roundValue(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}