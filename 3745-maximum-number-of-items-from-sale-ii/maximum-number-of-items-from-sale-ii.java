
import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // Step 1: Count frequencies of factors
        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // Step 2: Precompute the number of items
        // divisible by each possible factor
        int[] divisibleCount = new int[n + 1];

        for (int x = 1; x <= n; x++) {
            for (int multiple = x; multiple <= n; multiple += x) {
                divisibleCount[x] += freq[multiple];
            }
        }

        // Step 3: Create item information
        // [price, bonus capacity]
        int[][] sorted = new int[n][2];

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            int free = divisibleCount[factor] - 1;

            sorted[i][0] = price;
            sorted[i][1] = free;
        }

        // Step 4: Sort by price
        Arrays.sort(sorted, (a, b) ->
            Integer.compare(a[0], b[0])
        );

        // Step 5: Buy the most valuable bonus copies first
        long remaining = budget;
        long bonusCopies = 0;

        for (int[] item : sorted) {
            int price = item[0];
            int capacity = item[1];

            if (price > 2L * minPrice) {
                break;
            }

            long take = Math.min(
                (long) capacity,
                remaining / price
            );

            bonusCopies += take;
            remaining -= take * price;
        }

        // Step 6: Spend remaining budget on cheapest items
        long answer = 2L * bonusCopies
                    + remaining / minPrice;

        return (int) answer;
    }
}
