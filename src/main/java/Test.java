import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        double edgeDensity = 0.5;
        int sizeV1 = 10;
        int sizeV2 = 10;
        int tries = 100;
        boolean allTrue = true;
        while (tries-- > 0) {
            BipartiteGraph g = new BipartiteGraph(sizeV1, sizeV2, edgeDensity);
            HashSet<Integer> resultCHB = CHB.CHB(g.copy());
            HashSet<Integer> resultCHBPlus = CHBPlus.CHBPlus(g.copy());
            HashSet<Integer> resultCHBWith4 = CHBWithFourAprox.CHB(g.copy());
//            HashSet<Integer> resultCHBPlusWith4 = CHBPlusWithFourApprox.CHBPlus(g.copy());
            System.out.println(g);
            // CHB
            System.out.println(resultCHB);
            boolean checkCHB = Checker.checker(resultCHB, g);
            allTrue &= checkCHB;
            System.out.println(checkCHB);
            // CHB+
            System.out.println(resultCHBPlus);
            boolean checkCHBPlus = Checker.checker(resultCHBPlus, g);
            allTrue &= checkCHBPlus;
            System.out.println(checkCHBPlus);
            // CHB4
            System.out.println(resultCHBWith4);
            boolean checkCHB4 = Checker.checker(resultCHBWith4, g);
            allTrue &= checkCHB4;
            System.out.println(checkCHB4);
            //CHB+4
//            System.out.println(resultCHBPlusWith4);
//            boolean checkCHBPlus4 = Checker.checker(resultCHBPlusWith4, g);
//            System.out.println(checkCHBPlus4);
        }
        System.out.println(allTrue);
    }
}
