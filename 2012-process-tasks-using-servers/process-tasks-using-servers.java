class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        //Free Servers:[weight,index]
        PriorityQueue<int[]> free=new PriorityQueue<>(
            (a,b)->{
                if(a[0]!=b[0]){
                    return a[0]-b[0];
                }
                return a[1]-b[1];
            }
        );
        //Busy Servers:[finishTime,weight,index]
        PriorityQueue<long[]> busy=new PriorityQueue<>(
            (a,b)->{
                if(a[0]!=b[0]){
                    return Long.compare(a[0],b[0]);
                }
                if(a[1]!=b[1]){
                    return Long.compare(a[1],b[1]);
                }
                return Long.compare(a[2],b[2]);
            }
        );

        //Initially ,all servers are free
        for(int i=0;i<servers.length;i++){
            free.offer(new int[]{servers[i],i});
        }
        int[] ans=new int[tasks.length];
        long time=0;
        for(int i=0;i<tasks.length;i++){
            time=Math.max(time,i);

            //Move completed servers into the free heap
            while(!busy.isEmpty() && busy.peek()[0]<=time){
                long[] server=busy.poll();

                free.offer(new int[]{
                    (int) server[1],
                    (int) server[2]
                });
            }

            //If no server is free,jump to the next finish time
            if(free.isEmpty()){
                time=busy.peek()[0];

                while(!busy.isEmpty() && busy.peek()[0]<=time){
                    long[] server=busy.poll();

                    free.offer(new int[]{
                        (int) server[1],
                        (int) server[2]
                    });
                }
            }

            //Select the best available server
            int[] server=free.poll();

            int weight=server[0];
            int index=server[1];

            ans[i]=index;

            //Mark server as busy
            busy.offer(new long[]{
                time+tasks[i],
                weight,
                index
            });
        }
        return ans;
    }
}