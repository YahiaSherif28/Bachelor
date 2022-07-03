import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Checker {
    public static boolean checker(HashSet<Integer> result, BipartiteGraph g) {
        Queue<Integer> q = new LinkedList<>();
        int[] good = new int[g.sizeV1 + g.sizeV2];
        for (int x : result) {
            if (x >= g.sizeV1) {
                return false;
            }
            q.add(x);
            good[x] = 1;
        }
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int x : g.adj[cur]) {
                if ((x < g.sizeV1 && ++good[x] == 1) || (x >= g.sizeV1 && ++good[x] == g.indeg(x))) {
                    q.add(x);
                }
            }
        }
        for (int i = 0; i < good.length; i++) {
            if (!g.removed[i] && good[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
