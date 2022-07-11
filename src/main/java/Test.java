import com.google.common.collect.HashMultiset;

import java.util.HashSet;

public class Test {
    static int randomRange(int l, int r) {
        int x = (int) (Math.random() * (r - l + 1)) + l;
        return x;
    }

    public static void main(String[] args) {

        double edgeDensity = 0.1;
        int sizeV1l = 21;
        int sizeV1r = 40;
        int tries = 10000;
//        boolean allTrue = true;
//        int countMistakes = 0;
        double startTime = System.currentTimeMillis();
        long totalBSize = 0;
        for (int i = 0; i < tries; i++) {
            int sizeV1 = randomRange(sizeV1l, sizeV1r);
            int sizeV2 = randomRange(2, sizeV1);
//            int sizeV2 = sizeV1;
            BipartiteGraph g = new BipartiteGraph(sizeV1, sizeV2, edgeDensity);
//            HashSet<Integer> resultCHB = CHB.CHB(g.copy());
//            HashSet<Integer> resultCHBPlus = CHBPlus.CHBPlus(g.copy());
//            HashSet<Integer> resultCHBWith4 = CHBWithFourAprox.CHB(g.copy());
            HashSet<Integer> resultCHBPlusWith4 = CHBPlusWithFourApprox.CHBPlus(g.copy());
            totalBSize += resultCHBPlusWith4.size();
//            System.out.println(g);
            // CHB
//            System.out.println(resultCHB);
//            boolean checkCHB = Checker.checker(resultCHB, g);
//            allTrue &= checkCHB;
//            System.out.println(checkCHB + "a");
//            // CHB+
//            System.out.println(resultCHBPlus);
//            boolean checkCHBPlus = Checker.checker(resultCHBPlus, g);
//            allTrue &= checkCHBPlus;
//            System.out.println(checkCHBPlus + "b");
            // CHB4
//            System.out.println(resultCHBWith4);
//            boolean checkCHB4 = Checker.checker(resultCHBWith4, g);
//            allTrue &= checkCHB4;
//            System.out.println(checkCHB4 + "c");
            //CHB+4
//            System.out.println(resultCHBPlusWith4);
//            boolean checkCHBPlus4 = Checker.checker(resultCHBPlusWith4, g);
//            allTrue &= checkCHBPlus4;
//            System.out.println(checkCHBPlus4);
//            if (/*!checkCHB  || !checkCHBPlus || !checkCHB4 ||*/ !checkCHBPlus4) {
//                countMistakes++;
//            }

        }
        double totalTime = System.currentTimeMillis() - startTime;
        double avgTime = totalTime / tries;
        double avgSetSize = 1.0 * totalBSize / tries;
        System.out.println("Avg |B|: " + avgSetSize);
        System.out.println("Avg Time: " + avgTime);
//        System.out.println(countMistakes);
//        System.out.println(allTrue);
    }
}
