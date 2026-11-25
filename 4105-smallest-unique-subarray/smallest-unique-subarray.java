
import java.util.Arrays;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        // Step 1: Build suffix array
        int[] sa = buildSuffixArray(nums);

        // Step 2: Build inverse suffix array
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        // Step 3: Calculate LCP using Kasai's algorithm
        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int p = pos[i];

            if (p == 0) {
                h = 0;
                continue;
            }

            int j = sa[p - 1];

            while (i + h < n && j + h < n
                    && nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[p] = h;

            if (h > 0) {
                h--;
            }
        }

        // Step 4: Find minimum unique subarray length
        int ans = n;

        for (int p = 0; p < n; p++) {
            int maxLCP = 0;

            // LCP with previous suffix
            if (p > 0) {
                maxLCP = Math.max(maxLCP, lcp[p]);
            }

            // LCP with next suffix
            if (p < n - 1) {
                maxLCP = Math.max(maxLCP, lcp[p + 1]);
            }

            int uniqueLength = maxLCP + 1;
            int start = sa[p];

            if (start + uniqueLength <= n) {
                ans = Math.min(ans, uniqueLength);
            }
        }

        return ans;
    }

    private int[] buildSuffixArray(int[] nums) {
        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] newRank = new int[n];

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(sorted, nums[i]);
        }

        final int SHIFT = 17;
        final long MASK = (1L << SHIFT) - 1;

        int k = 1;

        while (k < n) {
            long[] order = new long[n];

            // Encode pairs of ranks
            for (int i = 0; i < n; i++) {
                int second = (i + k < n)
                        ? rank[i + k] : -1;

                long key = rank[i] * (n + 1L)
                        + second + 1;

                order[i] = (key << SHIFT) | i;
            }

            Arrays.sort(order);

            for (int i = 0; i < n; i++) {
                sa[i] = (int) (order[i] & MASK);
            }

            // Assign new ranks
            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int aSecond = (a + k < n)
                        ? rank[a + k] : -1;

                int bSecond = (b + k < n)
                        ? rank[b + k] : -1;

                boolean different =
                        rank[a] != rank[b]
                        || aSecond != bSecond;

                newRank[b] = newRank[a]
                        + (different ? 1 : 0);
            }

            int[] temp = rank;
            rank = newRank;
            newRank = temp;

            if (rank[sa[n - 1]] == n - 1) {
                break;
            }

            k *= 2;
        }

        return sa;
    }
}
