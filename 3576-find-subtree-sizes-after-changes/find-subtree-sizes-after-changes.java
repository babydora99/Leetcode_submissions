//Tree + DFS + Character Ancestor Tracking
class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n=parent.length;
        //step 1: build a original tree
        List<Integer>[] children=new ArrayList[n];

        for(int i=0;i<n;i++){
            children[i]=new ArrayList<>();
        }
        for(int i=1;i<n;i++){
            children[parent[i]].add(i);
        }
        //step 2:find the new parent of each node
        int[] newParent=new int[n];
        int[] last=new int[26];
        int[] oldLast=new int[n];

        Arrays.fill(last,-1);
        newParent[0]=-1;

        //Interative DFS:negative values represent exit events
        int[] stack=new int[2*n+5];
        int top=0;

        stack[top++]=0;

        while(top>0){
            int item=stack[--top];
            //exit event:restore previous ancestor
            if(item<0){
                int node=~item;
                int c=s.charAt(node)-'a';

                last[c]=oldLast[node];
                continue;
            }
            //enter event
            int node=item;
            int c=s.charAt(node)-'a';
            oldLast[node]=last[c];

            if(last[c]!=-1){
                newParent[node]=last[c];
            }else{
                newParent[node]=parent[node];
            }
            last[c]=node;

            //push exit event
            stack[top++]=~node;

            //push original children in reverse order
            for(int j=children[node].size()-1;j>=0;j--){
                stack[top++]=children[node].get(j);
            }
        }
        //step 3:Build the updated tree
        List<Integer>[] updated=new ArrayList[n];
        for(int i=0;i<n;i++){
            updated[i]=new ArrayList<>();
        }
        for(int i=1;i<n;i++){
            updated[newParent[i]].add(i);
        }
        //step 4:get traversal order of updated tree
        int[] order=new int[n];
        top=0;
        stack[top++]=0;
        int count=0;
        while(top>0){
            int node=stack[--top];
            order[count++]=node;

            for(int child:updated[node]){
                stack[top++]=child;
            }
        }
        //step 5:calculate subtree sizes in reverse order
        int[] ans=new int[n];
        Arrays.fill(ans,1);

        for(int i=n-1;i>0;i--){
            int node=order[i];
            ans[newParent[node]]+=ans[node];
        }
        return ans;
    }
}