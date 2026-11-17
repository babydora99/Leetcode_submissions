
import java.util.Arrays;

class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();
        int INF = 1000000;

        // Step 1: Precompute minimum changes for every substring
        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {

            for (int start = 0; start + len <= n; start++) {

                int best = INF;

                // Try every proper divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Check all groups
                    for (int offset = 0; offset < d; offset++) {

                        int count = (len - 1 - offset) / d + 1;

                        // Compare mirrored characters
                        for (int t = 0; t < count / 2; t++) {

                            int left = start + offset + t * d;

                            int right = start + offset
                                      + (count - 1 - t) * d;

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[start][start + len - 1] = best;
            }
        }

        // Step 2: Dynamic Programming
        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        // Number of substrings
        for (int parts = 1; parts <= k; parts++) {

            // Number of characters considered
            for (int end = 2 * parts; end <= n; end++) {

                // Starting index of the last substring
                for (int start = 0; start <= end - 2; start++) {

                    if (dp[parts - 1][start] == INF) {
                        continue;
                    }

                    int total = dp[parts - 1][start]
                              + cost[start][end - 1];

                    dp[parts][end] =
                        Math.min(dp[parts][end], total);
                }
            }
        }

        return dp[k][n];
    }
}
