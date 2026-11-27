//Greedy + 0/1 Knapsack DP
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n=items.length;

        //calculate free copies obtained by buying each item type
        int[] free=new int[n];

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i!=j && items[j][0] % items[i][0]==0){
                    free[i]++;
                }
            }
        }

        //0/1 knapsack DP for activating item types
        int[] dp=new int[budget+1];
        for(int i=0;i<n;i++){
            int price=items[i][1];
            int value=1+free[i];

            for(int b=budget;b>=price;b--){
                dp[b]=Math.max(dp[b],dp[b-price]+value);
            }
        }
        //Add extra purchased copies using remaining budget
        int ans=0;
        for(int b=0;b<=budget;b++){
            int remaining=budget-b;
            int minPrice=Integer.MAX_VALUE;

            for(int i=0;i<n;i++){
                minPrice=Math.min(minPrice,items[i][1]);
            }
            int extraCo=remaining/minPrice;
            ans=Math.max(dp[b]+extraCo,ans);
        }
        return ans;
    }
}