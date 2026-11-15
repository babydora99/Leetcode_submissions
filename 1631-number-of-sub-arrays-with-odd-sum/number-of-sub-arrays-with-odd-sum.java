class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD=1000_000_007;
        long evenCount=1;
        long oddCount=0;
        int prefix=0;
        long ans=0;
        for(int num:arr){
            //update prefix parity
            prefix=(prefix+num)%2;
            if(prefix==1){
                ans=(ans+evenCount)%MOD;
                oddCount++;
            }
            else{
                ans=(ans+oddCount)%MOD;
                evenCount++;
            }
        }
        return (int) ans;
    }
}