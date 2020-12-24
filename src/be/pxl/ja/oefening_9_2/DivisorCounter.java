package be.pxl.ja.oefening_9_2;

import java.util.List;
import java.util.LinkedList;

public class DivisorCounter extends Thread {
    public static void main(String[] args) {
        List<DivisorCounter> divisorCounters = new LinkedList<>();
        int minimum = 1, maximum = 50000;
        int threadCount = 10;
        int increaseValue = maximum / threadCount;
        int startRange = minimum, endRange = increaseValue;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            DivisorCounter divisorCounter = new DivisorCounter(startRange, endRange);
            divisorCounter.start();
            startRange += increaseValue;
            endRange += increaseValue;

            try {
                divisorCounter.join();
                divisorCounters.add(divisorCounter);
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        double calculationTime = (double) (endTime - startTime) / 1000;

        int highestCount = divisorCounters.stream()
            .mapToInt(divisorCounter -> divisorCounter.getHighestDivisor()[1])
            .max().getAsInt();

        DivisorCounter highestDivisorCounter = divisorCounters.stream()
                .filter(divisorCounter -> divisorCounter.getHighestDivisor()[1] == highestCount)
                .findFirst()
                .get();

        System.out.println("Range [" + minimum + "-" + maximum + "]");
        System.out.println(highestDivisorCounter);
        System.out.println("Tijd: " + calculationTime + " seconden");
    }

    private static final String LS = System.lineSeparator();
    private int minimum;
    private int maximum;
    private int[] highestDivisor = new int[2];

    public DivisorCounter(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int[] getHighestDivisor() {
        return this.highestDivisor;
    }

    private void calculateHighestDivisor() {
        int[] highestDivisor = new int[] {0, 0};
        int counter = 0;

        for (int i = this.maximum; i >= this.minimum; i--) {
            for (int j = 1; j <= i; j++) {
                if (i % j == 0) counter++;
            }

            if (counter > highestDivisor[1]) highestDivisor = new int[] {i, counter};
            counter = 0;
        }

        this.highestDivisor = highestDivisor;
    }

    @Override
    public void run() {
        calculateHighestDivisor();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Getal: " + this.highestDivisor[0]).append(LS);
        result.append("Aantal delers: " + this.highestDivisor[1]);

        return result.toString();
    }
}
