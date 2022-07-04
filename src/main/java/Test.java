import com.google.common.collect.HashMultiset;

import java.util.HashSet;

public class Test {
    public static void main(String[] args) {

        double edgeDensity = 0.8;
        int sizeV1 = 200;
        int sizeV2 = 200;
        int tries = 100;
        boolean allTrue = true;
        int countMistakes = 0;
        while (tries-- > 0) {
            BipartiteGraph g = new BipartiteGraph(sizeV1, sizeV2, edgeDensity);
//            HashSet<Integer> resultCHB = CHB.CHB(g.copy());
//            HashSet<Integer> resultCHBPlus = CHBPlus.CHBPlus(g.copy());
            HashSet<Integer> resultCHBWith4 = CHBWithFourAprox.CHB(g.copy());
            HashSet<Integer> resultCHBPlusWith4 = CHBPlusWithFourApprox.CHBPlus(g.copy());
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
            boolean checkCHB4 = Checker.checker(resultCHBWith4, g);
            allTrue &= checkCHB4;
//            System.out.println(checkCHB4 + "c");
            //CHB+4
//            System.out.println(resultCHBPlusWith4);
            boolean checkCHBPlus4 = Checker.checker(resultCHBPlusWith4, g);
//            System.out.println(checkCHBPlus4);
            if (/*!checkCHB || !checkCHBPlus ||*/ !checkCHB4 || !checkCHBPlus4) {
                countMistakes++;
            }
        }
        System.out.println(countMistakes);
        System.out.println(allTrue);
    }
}
