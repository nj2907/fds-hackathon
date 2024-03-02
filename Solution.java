import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        // Test case 1
        int[][] flights1 = {{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}};
        System.out.println("Actual: " + findCheapestPrice(4, flights1, 0, 3, 1) + ", Expected: 700");

        // Test case 2
        int[][] flights2 = {{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}};
        System.out.println("Actual: " + findCheapestPrice(4, flights2, 0, 3, 0) + ", Expected: -1");

        // Test case 3
        int[][] flights3 = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        System.out.println("Actual: " + findCheapestPrice(3, flights3, 0, 2, 0) + ", Expected: 500");
    }

    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        Map<Integer, List<int[]>> destOptionsForEachSrcCities = new HashMap<>();

        for (int[] flight : flights) {
            destOptionsForEachSrcCities.computeIfAbsent(flight[0], key -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }

        //After this step, you will have all the sources as key in HashMap and values as the possible destinations and cost.

        PriorityQueue<int[]> flightDetailQueueSortedByCost
                = new PriorityQueue<>(Comparator.comparingInt(flightDetail -> flightDetail[0]));

        //initialize with 0 cost and source city.
        int[] initialValue = new int[]{0, src, 0};
        flightDetailQueueSortedByCost.offer(initialValue);

        int city, cost, noOfStops;
        while (!flightDetailQueueSortedByCost.isEmpty()) {
            int[] current = flightDetailQueueSortedByCost.poll();

            city = current[1];
            cost = current[0];
            noOfStops = current[2];

            //Return the current cost as total cost if it matches your destination
            if (city == dst) {
                return cost;
            }

            if (noOfStops <= k) {
                //Check from the current city where all you can go & continue the same process
                List<int[]> destinationsFromCurrentCity = destOptionsForEachSrcCities.getOrDefault(city, Collections.emptyList());
                for (int[] destinationFromCurrentCity : destinationsFromCurrentCity) {
                    flightDetailQueueSortedByCost.offer(new int[]{cost + destinationFromCurrentCity[1], destinationFromCurrentCity[0], noOfStops + 1});
                }
            }
        }
        return -1;
    }

}
