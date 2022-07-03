import java.util.ArrayList;
import java.util.HashSet;

public class CHBWithFourAprox {

    public static HashSet<Integer> CHB(BipartiteGraph g) {
        HashSet<Integer> max = new HashSet<>();
        for (int i = 0; i < g.sizeV1; i++) {
            if (!g.removed[i] && g.indeg(i) == 0) {
                max.add(i);
            }
        }
        Trim.trim(g);
        Trim.reverseTrim(g);
        HashSet<Integer> result = FourAlgorithm.findFVS(new Graph(g));
        result.addAll(max);
        return result;
    }

}
