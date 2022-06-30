import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LogReduction {

    public static void reduce(Graph g) {
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < g.size; i++) {
            if (g.adj[i].size() == 1) {
                leaves.add(i);
            }
        }
        while (!leaves.isEmpty()) {
            int cur = leaves.poll();
            for (int x : g.adj[cur]) {
                g.adj[x].remove(cur);
                if (g.adj[x].size() == 1) {
                    leaves.add(x);
                }
            }
            g.removed[cur] = true;
            g.adj[cur].clear();
        }
        for (int i = 0; i < g.size; i++) {
            ArrayList<Integer> nodes = new ArrayList<>(g.adj[i]);
            if (g.adj[i].size() == 2) {
                g.adj[nodes.get(0)].add(nodes.get(1));
                g.adj[nodes.get(1)].add(nodes.get(0));
            }
            g.adj[i].clear();
            g.removed[i] = true;
        }
    }

    // returns null if the graph has no cycles
    public static ArrayList<Integer> findShortestCycle(Graph g) {
        ArrayList<Integer> ans = null;
        for (int s = 0; s < g.size; s++) {
            Queue<Integer> q = new LinkedList<>();
            int[] dist = new int[g.size];
            int[] parent = new int[g.size];
            Arrays.fill(dist, (int) 1e9);
            Arrays.fill(parent, -1);
            q.add(s);
            dist[s] = 0;
            int minCycle = (int) 1e9;
            int bestU = -1, bestV = -1;
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int x : g.adj[cur]) {
                    if (parent[x] == -1) {
                        parent[x] = cur;
                        dist[x] = 1 + dist[cur];
                        q.add(x);
                    } else if (parent[x] != cur && parent[cur] != x) {
                        if (dist[x] + dist[cur] + 1 < minCycle) {
                            minCycle = dist[x] + dist[cur] + 1;
                            bestU = x;
                            bestV = cur;
                        }
                    }
                }
            }
            if (minCycle == (int) 1e9) {
                continue;
            }
            ArrayList<Integer> candidate = new ArrayList<>();
            while (bestU != s) {
                candidate.add(bestU);
                bestU = parent[bestU];
            }
            while (bestV != s) {
                candidate.add(bestV);
                bestV = parent[bestV];
            }
            candidate.add(s);
            if (ans == null || ans.size() > candidate.size()) {
                ans = candidate;
            }
        }
        return ans;
    }

    public static ArrayList<Integer> process(Graph g) {
        ArrayList<Integer> ans = new ArrayList<>();
        while (true) {
            reduce(g);
            ArrayList<Integer> shortestCycle = findShortestCycle(g);
            System.out.println(shortestCycle);
            if (shortestCycle == null)
                break;
            for (int x : shortestCycle) {
                ans.add(x);
                for (int v : g.adj[x]) {
                    g.adj[v].remove(x);
                }
                g.adj[x].clear();
                g.removed[x] = true;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(2,1);
        g.addEdge(1,3);
        g.addEdge(3,4);
        g.addEdge(4,2);
        reduce(g);
        for(var x : g.adj) {
            System.out.println(x);
        }
//        System.out.println(findShortestCycle(g));
//        System.out.println(process(g));
    }
}
