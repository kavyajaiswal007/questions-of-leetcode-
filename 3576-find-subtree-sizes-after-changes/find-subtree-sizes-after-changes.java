import java.util.*;

class Solution {
    private List<Integer>[] adj;
    private List<Integer>[] newAdj;
    private int[] newParent;
    private int[] lastSeen;
    private int[] ans;
    private String s;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.s = s;
        this.newParent = parent.clone();
        this.lastSeen = new int[26];
        Arrays.fill(this.lastSeen, -1);
        
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        // Step 1: Find the closest matching ancestor for every node
        dfsFindParents(0);

        // Step 2: Rebuild the tree with the updated parents
        newAdj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newAdj[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            newAdj[newParent[i]].add(i);
        }

        // Step 3: Compute subtree sizes in the final tree
        ans = new int[n];
        dfsSubtreeSize(0);
        
        return ans;
    }

    private void dfsFindParents(int u) {
        int charIdx = s.charAt(u) - 'a';
        int prevAncestor = lastSeen[charIdx];

        if (prevAncestor != -1) {
            newParent[u] = prevAncestor;
        }

        lastSeen[charIdx] = u;

        for (int v : adj[u]) {
            dfsFindParents(v);
        }

        // Backtrack
        lastSeen[charIdx] = prevAncestor;
    }

    private int dfsSubtreeSize(int u) {
        int size = 1;
        for (int v : newAdj[u]) {
            size += dfsSubtreeSize(v);
        }
        ans[u] = size;
        return size;
    }
}
