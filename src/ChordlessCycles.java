import java.util.ArrayList;
import java.util.Arrays;

public class ChordlessCycles {

    public static ArrayList<ArrayList<Integer>> triplets(BipartiteGraph g) {
        ArrayList<ArrayList<Integer>> tr = new ArrayList<>();
        for (int n1 = 0; n1 < g.sizeV1; n1++) {
            if (g.removed[n1]) continue;
            for (int n2 : g.adj[n1]) {
                for (int n3 : g.adj[n2]) {
                    tr.add(new ArrayList<>(Arrays.asList(n1, n2, n3)));
                }
            }
        }
        return tr;
    }

    public static ArrayList<ArrayList<Integer>> chordlessCycles(BipartiteGraph g) {
        ArrayList<ArrayList<Integer>> paths = triplets(g);
        ArrayList<ArrayList<Integer>> ch = new ArrayList<>();
        while (!paths.isEmpty()) {
            ArrayList<Integer> m = paths.remove(paths.size() - 1);
            for (int k : g.adj[m.get(m.size() - 1)]) {
                boolean chord = false;
                for (int i = 1; i < m.size() - 1; i++) {
                    int c = m.get(i);
                    if (g.adj[c].contains(k) || g.adj[k].contains(c)) {
                        chord = true;
                        break;
                    }
                }
                if (!chord) {
                    ArrayList<Integer> mclone = new ArrayList<>(m);
                    mclone.add(k);
                    if (g.adj[k].contains(mclone.get(0))) {
                        ArrayList<Integer> nodesInV1 = new ArrayList<>();
                        for (int i = 0; i < mclone.size(); i += 2) {
                            nodesInV1.add(m.get(i));
                        }
                        ch.add(nodesInV1);
                    } else {
                        paths.add(mclone);
                    }
                }
            }
        }
        return ch;
    }

}
