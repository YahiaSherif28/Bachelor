import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graph { // undirected
    public int size;
    public boolean[] removed;
    public HashSet<Integer>[] adj;
    boolean[] inSubGraph;
    int[] edgesInSubGraph;
    boolean[] inResult;

    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    public Graph(int size) {
        this.size = size;
        removed = new boolean[size];
        adj = new HashSet[size];
        for (int i = 0; i < size; i++) {
            adj[i] = new HashSet<>();
        }
        inSubGraph = new boolean[size];
        edgesInSubGraph = new int[size];
        inResult = new boolean[size];
    }

    public Graph(BipartiteGraph g) {
        size = g.sizeV1;
        removed = new boolean[size];
        adj = new HashSet[size];
        inSubGraph = new boolean[size];
        edgesInSubGraph = new int[size];
        inResult = new boolean[size];
        for (int i = g.sizeV1; i < g.sizeV1 + g.sizeV2; i++) {
            for (int u : g.adjRev[i]) {
                for (int v : g.adj[i]) {
                    if (u != v) {
                        addEdge(u, v);
                    }
                }
            }
        }
    }


    public void find23SubGraph() {
        inSubGraph = new boolean[size];
        edgesInSubGraph = new int[size];
        for (int i = 0; i < size; i++) {
            int cnt = 0;
            boolean canAdd = true;
            for (int x : adj[i]) {
                if (inSubGraph[x]) {
                    cnt++;
                    canAdd &= edgesInSubGraph[x] <= 2;
                }
            }
            if (canAdd && cnt <= 3) {
                inSubGraph[i] = true;
                edgesInSubGraph[i] = cnt;
                for (int x : adj[i]) {
                    if (inSubGraph[x]) {
                        edgesInSubGraph[x]++;
                    }
                }
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            if (edgesInSubGraph[i] == 1) {
                q.add(i);
                inSubGraph[i] = false;
            } else if (edgesInSubGraph[i] == 0) {
                inSubGraph[i] = false;
            }
        }
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int x : adj[cur]) {
                if (inSubGraph[x] && --edgesInSubGraph[x] == 1) {
                    q.add(x);
                    inSubGraph[x] = false;
                }
            }
        }
    }

    public void findCriticalPoints() {
        // mark components in G - H then check if adding i forms a cycle
        int[] comp = new int[size];
        int curComp = 1;
        for (int i = 0; i < size; i++) {
            if (inSubGraph[i] || comp[i] != 0)
                continue;
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            comp[i] = curComp;
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int x : adj[cur]) {
                    if (!inSubGraph[x] && comp[x] == 0) {
                        comp[x] = curComp;
                        q.add(x);
                    }
                }
            }
            curComp++;
        }
        for (int i = 0; i < size; i++) {
            if (!inSubGraph[i]) continue;
            HashSet<Integer> prevComps = new HashSet<>();
            inResult[i] = true;
            for (int x : adj[i]) {
                if (!inSubGraph[x]) {
                    inResult[i] &= prevComps.add(comp[x]);
                }
            }
        }
    }

    public void getBranchPoints() {
        for (int i = 0; i < size; i++) {
            if (inSubGraph[i] && edgesInSubGraph[i] == 3) {
                inResult[i] = true;
            }
        }
    }


}
