package de.dennisguse.opentracks.data.models;

public class SkiRun {
    //TODO: Add class attributes
    private double maxVertical;
    private int numRuns;
    private double avgSpeed;
    private double maxSpeed;
    // skied distance
    private double skiedDistance;
    // ski + lift total distance
    private double totalDistance;

    public SkiRun(double maxVertical, int numRuns, double avgSpeed, double maxSpeed, double skiedDistance, double totalDistance) {
        this.maxVertical = maxVertical;
        this.numRuns = numRuns;
        this.avgSpeed = avgSpeed;
        this.maxSpeed = maxSpeed;
        this.skiedDistance = skiedDistance;
        this.totalDistance = totalDistance;
    }

    public double getMaxVertical() {
        return maxVertical;
    }

    public int getNumRuns() {
        return numRuns;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getSkiedDistance() {
        return skiedDistance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setMaxVertical(double maxVertical) {
        this.maxVertical = maxVertical;
    }

    public void setNumRuns(int numRuns) {
        this.numRuns = numRuns;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSkiedDistance(double skiedDistance) {
        this.skiedDistance = skiedDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
}
