import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Stack;

public class HittingSet {
    public static int removeFromHashSet(HashSet<Integer> a) {
        int toberem = -1;
        for (int x : a) {
            toberem = x;
            break;
        }
        if (toberem == -1) {
            throw new RuntimeException();
        }

        a.remove(toberem);
        return toberem;
    }

    public static HashSet<Integer> hittingSet(ArrayList<ArrayList<Integer>> sets) {
        HashSet<Integer>[] cntSetsToNumbers = new HashSet[sets.size() + 1];
        HashSet<Integer> result = new HashSet<>();
        for (int i = 0; i < cntSetsToNumbers.length; i++) {
            cntSetsToNumbers[i] = new HashSet<>();
        }
        HashMap<Integer, HashSet<Integer>> numToSets = new HashMap<>();
        for (int i = 0; i < sets.size(); i++) {
            ArrayList<Integer> set = sets.get(i);
            for (int num : set) {
                numToSets.computeIfAbsent(num, k -> new HashSet<>()).add(i);
            }
        }
        for (Entry<Integer, HashSet<Integer>> e : numToSets.entrySet()) {
            cntSetsToNumbers[e.getValue().size()].add(e.getKey());
        }
        int max = sets.size();
        while (max > 0) {
            if (cntSetsToNumbers[max].isEmpty()) {
                max--;
            } else {
                int myNum = removeFromHashSet(cntSetsToNumbers[max]);
                result.add(myNum);
                HashSet<Integer> mySets = numToSets.get(myNum);
                for (int removedSet : mySets) {
                    for (int numInRemovedSet : sets.get(removedSet)) {
                        if (numInRemovedSet == myNum)
                            continue;
                        HashSet<Integer> setsOfNumInRemovedSet = numToSets.get(numInRemovedSet);
                        cntSetsToNumbers[setsOfNumInRemovedSet.size()].remove(numInRemovedSet);
                        setsOfNumInRemovedSet.remove(removedSet);
                        cntSetsToNumbers[setsOfNumInRemovedSet.size()].add(numInRemovedSet);
                    }
                }

            }
        }
        return result;
    }
}
