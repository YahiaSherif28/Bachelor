import java.util.ArrayList;
import java.util.HashSet;

public class CHB {
    public static HashSet<Integer> CHB(BipartiteGraph g) {
        HashSet<Integer> max = new HashSet<>();
        for (int i = 0; i < g.sizeV1; i++) {
            if (!g.removed[i] && g.indeg(i) == 0) {
                max.add(i);
            }
        }
        Trim.trim(g);
        Trim.reverseTrim(g);
        ArrayList<ArrayList<Integer>> chordlessCycles = ChordlessCycles.chordlessCycles(g);
        HashSet<Integer> result = HittingSet.hittingSet(chordlessCycles);
        result.addAll(max);
        return result;
    }
}
