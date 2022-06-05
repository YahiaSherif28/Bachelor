import java.util.Arrays;
import java.util.HashSet;

public class BipartiteGraph {
    public int sizeV1, sizeV2;
    public boolean[] removed;
    public HashSet<Integer>[] adj, adjRev;

    public void addEdge(int u, int v) {
        adj[u].add(v);
        adjRev[v].add(u);
    }


    public BipartiteGraph(int sizeV1, int sizeV2, double edgeDensity) {
        this(sizeV1, sizeV2);
        for (int i = 0; i < sizeV1; i++) {
            for (int j = sizeV1; j < sizeV1 + sizeV2; j++) {
                if (Math.random() < edgeDensity) {
                    if (Math.random() < 0.5) {
                        addEdge(i, j);
                    } else {
                        addEdge(j, i);
                    }
                }
            }
        }
        for (int i = sizeV1; i < sizeV1 + sizeV2; i++) {
            if (indeg(i) == 0) {
                removeNode(i);
            }
        }
    }

    public BipartiteGraph(int sizeV1, int sizeV2) { // create empty graph
        this.sizeV1 = sizeV1;
        this.sizeV2 = sizeV2;
        removed = new boolean[sizeV1 + sizeV2];
        adj = new HashSet[sizeV1 + sizeV2];
        adjRev = new HashSet[sizeV1 + sizeV2];
        for (int i = 0; i < sizeV1 + sizeV2; i++) {
            adj[i] = new HashSet<>();
            adjRev[i] = new HashSet<>();
        }
    }

    public int indeg(int u) { // deg-
        return adjRev[u].size();
    }

    public int outdeg(int u) { // deg+
        return adj[u].size();
    }

    public void reverseGraph() {
        HashSet<Integer>[] temp = adj;
        adj = adjRev;
        adjRev = temp;
    }

    public void removeNode(int v) {
        removed[v] = true;
        for (int x : adj[v]) {
            adjRev[x].remove(v);
        }
        for (int x : adjRev[v]) {
            adj[x].remove(v);
        }
        adj[v].clear();
        adjRev[v].clear();
    }

    @Override
    public String toString() {
        return "BipartiteGraph{" +
                "sizeV1=" + sizeV1 +
                ", sizeV2=" + sizeV2 +
                ", removed=" + Arrays.toString(removed) +
                "\n, adj=" + Arrays.toString(adj) +
                "\n, adjRev=" + Arrays.toString(adjRev) +
                '}';
    }
}
