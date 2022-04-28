package net.craigscode.primer;

public class Configuration implements Comparable<Configuration> {

    int leastTurns;
    double leastFairRatio, upperFairRatio, unfairRatio, winRatio;
    long totalPoints;

    public Configuration(int leastTurns, double leastFairRatio, double upperFairRatio, double unfairRatio, long totalPoints, double winRatio) {
        this.leastTurns = leastTurns;
        this.leastFairRatio = leastFairRatio;
        this.upperFairRatio = upperFairRatio;
        this.unfairRatio = unfairRatio;
        this.totalPoints = totalPoints;
        this.winRatio = winRatio;
    }

    public int compareTo(Configuration o) {
        return Double.compare(o.winRatio, winRatio);
    }

    public String toString() {
        return String.format("%d,%4.2f,%4.2f,%4.2f,%d,%5.3f\n", leastTurns, leastFairRatio, upperFairRatio, unfairRatio, totalPoints, winRatio);
    }
}
