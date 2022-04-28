package net.craigscode.primer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Driver {
    public static void main(String[] args) throws IOException {
        //System.out.println(play(1,0.50,0.55,1.15));
        bigLoad();
    }

    static void bigLoad() throws IOException {
        FileOutputStream stream = new FileOutputStream("simulation.csv");


        // We wish to vary these parameters:
        // leastTurns                                                                               1 - 20
        // leastFairRatio, upperFairRatio (what bounds are we comfortable saying they are fair?)    0.25 - 0.75 dr = 0.05
        // unfairRatio (at what point do we call them a cheater?)                                   1.0 - 5.0   dr = 0.05
        // Each game will be played 1,000 times
        // This will be 1000 * 20 * 20 * 100 = 40,00,000 games

        List<Configuration> configurations = new ArrayList<>(40000000);
        for (int leastTurns = 1; leastTurns <= 20; leastTurns++) {
            for (double leastFairRatio = 0.25; leastFairRatio <= 0.75; leastFairRatio += 0.05) {
                for (double upperFairRatio = leastFairRatio + 0.05; upperFairRatio <= 0.75; upperFairRatio++) {
                    for (double unfairRatio = 1.0; unfairRatio <= 5.0; unfairRatio += 0.05) {
                        long totalPoints = 0;
                        for (int i = 1; i <= 1000; i++) {
                            totalPoints += play(leastTurns, leastFairRatio, upperFairRatio, unfairRatio);
                        }
                        double winRatio = (totalPoints * 100.0) / blobsPlayed;
                        blobsPlayed = 0;
                        configurations.add(new Configuration(leastTurns, leastFairRatio, upperFairRatio, unfairRatio, totalPoints, winRatio));
                    }
                }
            }
        }
        System.out.println("Finished simulation. Sorting...");
        Collections.sort(configurations);
        for (Configuration configuration : configurations) {
            stream.write(configuration.toString().getBytes());
        }
        System.out.println("Finished");
    }

    static final int MAX_TOTAL_TURNS = 1000;
    static final int MAX_BLOB_TURNS = 100;
    static long blobsPlayed = 0;

    // Return points after a maximum of MAX_TURNS
    static int play(int leastTurns, double leastFairRatio, double upperFairRatio, double unfairRatio) {
        int flips = 100;
        int totalTurns = 0;
        int points = 0;

        // Game overall
        while (totalTurns <= MAX_TOTAL_TURNS && flips > 0) {
            blobsPlayed++;
            // One single blob
            double heads = 0, tails = 0;
            // true = fair
            boolean actual = ThreadLocalRandom.current().nextBoolean();
            int blobTurns = 0;
            while (blobTurns <= MAX_BLOB_TURNS && flips > 0) {
                if (flip(actual)) {
                    heads++;
                } else {
                    tails++;
                }
                flips--;
                if (blobTurns >= leastTurns) {
                    double ratio = heads / tails;
                    // Label unfair
                    if (ratio >= unfairRatio) {
                        if (actual) {
                            flips -= 30;
                        } else {
                            flips += 15;
                            points++;
                        }
                        break;
                        // Label fair
                    } else if (ratio >= leastFairRatio && ratio <= upperFairRatio) {
                        if (actual) {
                            flips += 15;
                            points++;
                        } else {
                            flips -= 30;
                        }
                        break;
                    }
                }
                blobTurns++;
            }
            totalTurns += blobTurns;
        }
        return points;
    }

    static boolean flip(boolean actual) {
        int p = ThreadLocalRandom.current().nextInt(1, 101);
        if (actual) {
            return p <= 50;
        } else {
            return p <= 75;
        }
    }
}
