import java.util.*;

import com.google.common.collect.HashMultiset;

public class FourAlgorithm {
    static HashSet<Integer> vis;
    static LinkedList<Integer> path = new LinkedList<>();
//    static int[] edgesInH;
//    static HashSet<Integer>[] withEdgesInH;

    static int pop(HashSet<Integer> hs) {
        int res = -1;
        for (int x : hs) {
            res = x;
            break;
        }
        hs.remove(res);
        return res;
    }

    static int[] depth;

    public static boolean findCycle(Graph g, int u, int p, int root) {
        depth[root] = 0;
        vis.add(u);
        path.addLast(u);
        int myX = -1;
        for (int x : g.adj[u]) {
            if (vis.contains(x) && (p != x || g.adj[u].count(x) > 1)) {
                if (myX == -1 || depth[myX] < depth[x]) {
                    myX = x;
                }
            }
        }
        if (myX != -1) {
            while (path.peekFirst() != myX)
                path.pollFirst();
            return true;
        }
        for (int x : g.adj[u]) {
            if (myX == -1 && !vis.contains(x)) {
                depth[x] = 1 + depth[u];
                if (findCycle(g, x, u, root))
                    return true;
            }
        }

        path.removeLast();
        return false;
    }

    public static boolean findPathToAdd(Graph g, Graph h, int u, int p, int root) {
        vis.add(u);
        path.addLast(u);
        for (int x : g.adj[u]) {
            if (!(u == root && !h.removed[x]))
                if ((x != p || g.adj[u].count(x) > 1) && (vis.contains(x) || !h.removed[x]) && x != root) {
                    path.add(x);
                    return true;
                }
            if (!vis.contains(x)) {
                if (findPathToAdd(g, h, x, u, root))
                    return true;
            }
        }
        path.removeLast();
        return false;
    }

    static void removeLeaves(Graph g, Queue<Integer> leaves) {
        while (!leaves.isEmpty()) {
            int cur = leaves.poll();
            g.removed[cur] = true;
            for (int x : g.adj[cur]) {
                g.adj[x].remove(cur);
                if (g.adj[x].size() == 1) {
                    leaves.add(x);
                }
            }
            g.adj[cur].clear();
        }
    }

//    public static void increaseEdgesInH(int u) {
//        withEdgesInH[Math.min(edgesInH[u], withEdgesInH.length - 1)].remove(u);
//        edgesInH[u]++;
//        withEdgesInH[Math.min(edgesInH[u], withEdgesInH.length - 1)].add(u);
//    }

    public static Graph SubGraph23(Graph g) {

        depth = new int[g.size];
        Graph h = new Graph(g.size);
//        edgesInH = new int[g.size];
//        withEdgesInH = new HashSet[5];
//        for (int i = 0; i < withEdgesInH.length; i++) {
//            withEdgesInH[i] = new HashSet<>();
//        }
        vis = new HashSet<>();
        Arrays.fill(h.removed, true);
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < g.size; i++) {
            if (g.adj[i].size() == 1) {
                leaves.add(i);
            }
        }
        for (int i = 0; i < g.size; i++) {
            removeLeaves(g, leaves);
            if (g.removed[i])
                continue;
            ArrayList<Integer> addedToH = new ArrayList<>();
            Queue<Integer> r = new LinkedList<>();
            path = new LinkedList<>();
            vis = new HashSet<>();
            findCycle(g, i, -1, i);
//            System.out.println(path + " Cycle");
            if (path.isEmpty())
                continue;
//            for (int x : path) {
//                for (int y : g.adj[x]) {
//                    increaseEdgesInH(y);
//                }
//            }
//            for (int x : withEdgesInH[4]) {
//                g.removeNode(x);
//            }
            for (int j = 0; j < path.size(); j++) {
                if (j != 0) {
                    addedToH.add(path.get(j));
                }
                r.add(path.get(j));
                h.removed[path.get(j)] = false;
                h.addEdge(path.get(j), path.get((j + 1) % path.size()));
                g.removeEdge(path.get(j), path.get((j + 1) % path.size()));
            }
            while (!r.isEmpty()) {

                Queue<Integer> deg3 = new LinkedList<>();
                path = new LinkedList<>();
                vis = new HashSet<>();
//                if (!withEdgesInH[2].isEmpty()) {
//                    int cur = pop(withEdgesInH[2]);
//                    if (g.removed[cur] || !h.removed[cur])
//                        continue;
//                    for (int x : g.adj[cur]) {
//                        if (!h.removed[x] && h.adj[x].size() == 2) {
//                            path.add(x);
//                        }
//                    }
//                    if (path.size() != 2 || path.get(0).equals(path.get(1))) {
//                        continue;
//                    }
//                    path.add(1, cur);
//                } else if (!withEdgesInH[3].isEmpty()) {
//                    int cur = pop(withEdgesInH[3]);
//                    if (g.removed[cur] || !h.removed[cur])
//                        continue;
//                    for (int x : g.adj[cur]) {
//                        if (!h.removed[x] && h.adj[x].size() == 2 && path.size() < 2 && (path.isEmpty() || x != path.get(0))) {
//                            path.add(x);
//                        }
//                    }
//                    if (path.size() != 2) {
//                        continue;
//                    }
//                    path.add(1, cur);
//                } else {
                    int u = r.poll();
                    if (h.adj[u].size() != 2)
                        continue;
                    findPathToAdd(g, h, u, -1, u);
//                }
//                System.out.println(path + " Path");
//                for (int j = 1; j < path.size() - 1; j++) {
//                    for (int y : g.adj[path.get(j)]) {
//                        increaseEdgesInH(y);
//                    }
//                }
                for (int j = 0; j < path.size() - 1; j++) {
                    h.removed[path.get(j)] = false;
                    h.addEdge(path.get(j), path.get(j + 1));
                    if (h.adj[path.get(j)].size() == 3) {
                        deg3.add(path.get(j));
                    }
                    if (h.adj[path.get(j + 1)].size() == 3) {
                        deg3.add(path.get(j + 1));
                    }
                }

                ArrayList<Integer> toBeRemoved = new ArrayList<>();
//                toBeRemoved.addAll(withEdgesInH[4]);
//                withEdgesInH[4].clear();
                for (int x : deg3) {
                    for (int y : g.adj[x]) {
                        toBeRemoved.add(y);
                    }
                }
                for (int x : toBeRemoved) {
                    for (int y : g.adj[x]) {
                        if (g.adj[y].size() == 2) {
                            leaves.add(y);
                        }
                    }
                    g.removeNode(x);
                }
                removeLeaves(g, leaves);
            }
            for (int x : addedToH) {
                for (int y : g.adj[x]) {
                    if (g.adj[y].size() == 2) {
                        leaves.add(y);
                    }
                }
                g.removeNode(x);
            }
            removeLeaves(g, leaves);
        }
        return h;
    }

    public static boolean[] findCriticalPoints(Graph g, Graph h) {
        boolean[] isCriticalPoint = new boolean[h.size];
        // mark components in G - H then check if adding i forms a cycle
        int[] comp = new int[g.size];
        int curComp = 1;
        for (int i = 0; i < g.size; i++) {
            if (!h.removed[i] || comp[i] != 0)
                continue;
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            comp[i] = curComp;
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int x : g.adj[cur]) {
                    if (h.removed[x] && comp[x] == 0) {
                        comp[x] = curComp;
                        q.add(x);
                    }
                }
            }
            curComp++;
        }
        for (int i = 0; i < g.size; i++) {
            if (h.removed[i]) continue;
            HashSet<Integer> prevComps = new HashSet<>();
            isCriticalPoint[i] = false;
            for (int x : g.adj[i]) {
                if (h.removed[x]) {
                    isCriticalPoint[i] |= prevComps.add(comp[x]);
                }
            }
        }
        return isCriticalPoint;
    }

    public static ArrayList<Integer> findIsolatedCycles(Graph h, boolean[] isCriticalPoint) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[h.size];
        for (int i = 0; i < h.size; i++) {
            if (h.removed[i] || visited[i])
                continue;
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            visited[i] = true;
            int maxDegree = 0;
            boolean criticalPointFound = false;
            while (!q.isEmpty()) {
                int cur = q.poll();
                maxDegree = Math.max(maxDegree, h.adj[cur].size());
                criticalPointFound |= isCriticalPoint[cur];
                for (int x : h.adj[cur]) {
                    if (!visited[x]) {
                        visited[x] = true;
                        q.add(x);
                    }
                }
            }
            if (maxDegree == 2 && !criticalPointFound) {
                result.add(i);
            }
        }
        return result;
    }

    public static HashSet<Integer> findFVS(Graph g) {
        HashSet<Integer> f = new HashSet<>();
        while (true) {
            int best = -1;
            int bestCnt = 0;
            for (int i = 0; i < g.size; i++) {
                int cnt = 0;
                for (int x : g.adj[i]) {
                    if (g.adj[i].count(x) == 2) {
                        cnt++;
                    }
                }
                if (cnt > bestCnt) {
                    bestCnt = cnt;
                    best = i;
                }
            }
            if (best == -1)
                break;
            f.add(best);
            g.removeNode(best);
        }
        Graph h = SubGraph23(g.copy());
        boolean[] isCriticalPoint = findCriticalPoints(g, h);
        for (int i = 0; i < h.size; i++) {
            if (h.adj[i].size() == 3 || isCriticalPoint[i]) {
                f.add(i);
            }
        }
        f.addAll(findIsolatedCycles(h, isCriticalPoint));
        return f;
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 2);
        g.addEdge(5, 1);
        Graph h = SubGraph23(g);
        System.out.println(g);
        System.out.println(h);
    }
}
