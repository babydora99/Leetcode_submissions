class Solution {
    public boolean canAliceWin(int n) {
        int stone=n;
        int remove=10;
        boolean aliceTurn=true;
        while(stone>=remove){
            stone-=remove;
            remove--;
            aliceTurn=!aliceTurn;
        }
        return !aliceTurn;
    }
}