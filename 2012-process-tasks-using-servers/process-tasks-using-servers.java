import java.util.PriorityQueue;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        // Free servers heap: sorted by weight ascending, then by index ascending
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
        );
        
        // Busy servers heap: sorted by freeTime, then weight, then index
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) :
                      a[1] != b[1] ? Long.compare(a[1], b[1]) : 
                      Long.compare(a[2], b[2])
        );
        
        // Initialize all servers into the free pool
        for (int i = 0; i < servers.length; i++) {
            free.offer(new int[]{servers[i], i});
        }
        
        int[] ans = new int[tasks.length];
        long time = 0;
        
        for (int i = 0; i < tasks.length; i++) {
            // Ensure time is at least the arrival time of the current task
            time = Math.max(time, (long) i);
            
            // If all servers are busy, fast-forward time to the next available slot
            if (free.isEmpty() && !busy.isEmpty()) {
                time = busy.peek()[0];
            }
            
            // Release all servers that have finished processing by the current time
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                long[] s = busy.poll();
                free.offer(new int[]{(int) s[1], (int) s[2]});
            }
            
            // Assign the task to the best available free server
            int[] server = free.poll();
            ans[i] = server[1];
            
            // Place the server back into the busy queue with its updated free time
            busy.offer(new long[]{ time + tasks[i], server[0], server[1] });
        }
        
        return ans;
    }
}
