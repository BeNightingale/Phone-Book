package phonebook;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class OutputManager {
    protected static List<Person> createPersonList(List<String> stringList) {
        List<Person> list = new ArrayList<>();
        for (String s : stringList) {
            String[] array = s.split(" ");
            Person p = new Person();
            p.setPhoneNumber(array[0]);
            if (array.length == 3) {
                p.setName(array[1] + " " + array[2]);
            } else if (array.length == 2) {
                p.setName(array[1]);
            }
            list.add(p);
        }
        return list;
    }

    protected static void hashSearch(List<String> queryList, List<Person> list) {
        Map<Integer, String> map = new HashMap<>();
        IntStream.rangeClosed(0, list.size() - 1).forEach(x -> map.put(x, list.get(x).getName()));
        long startHash = System.currentTimeMillis();
        Hashtable<Integer, String> hashtable = new Hashtable<>(map);
        long endCreatingHash = System.currentTimeMillis();
        long creatingTime = endCreatingHash - startHash;
        String hashString = String.format("Creating time: %s%n", Util.formatTime(creatingTime));
        HashSet<Integer> hs = hashtable.values().stream().map(String::hashCode).map(Math::abs).collect(Collectors.toCollection(HashSet::new));
        List<Integer> listQha = queryList.stream().map(String::hashCode).map(Math::abs).distinct().collect(toList());
        long startSearching = System.currentTimeMillis();
        int foundHash = getFoundHash(hs, listQha);
        long endSearching = System.currentTimeMillis();
        long searchHashTime = endSearching - startSearching;
        System.out.println("Start searching (hash table)...");
        System.out.printf("Found %s / %s entries. Time taken: %s%n", foundHash, queryList.size(), Util.formatTime(creatingTime + searchHashTime));
        System.out.printf(hashString);
        System.out.printf("Searching time: %s%n%n", Util.formatTime(searchHashTime));
    }

    protected static int getFoundHash(HashSet<Integer> hs, List<Integer> listQha) {
        int foundHash = 0;
        for (int s : listQha) {
            if (hs.contains(s)) {
                foundHash++;
            }
        }
        return foundHash;
    }

    protected static void bubbleAndJump(List<String> queryList, List<Person> list, List<Person> queryPerson) {
        System.out.println("Start searching (bubble sort + jump search)...");
        long lo = System.currentTimeMillis();
        boolean sortingResult = Util.bubbleSort(list);
        long lt = System.currentTimeMillis();
        long sortTime = lt - lo;
        String lnrSrch = String.format("Sorting time: %s", Util.formatTime(sortTime));
        long lo1 = System.currentTimeMillis();
        long lt1;
        long searchTime1;
        String foundString = "Found %s / %s entries. Time taken: %s%n";
        if (sortingResult) {
            int foundBubble = Util.jumpSearchOfList(list, queryPerson);
            lt1 = System.currentTimeMillis();
            searchTime1 = lt1 - lo1;
            System.out.printf(foundString,
                    foundBubble, queryList.size(), Util.formatTime(sortTime + searchTime1));
            System.out.println(lnrSrch);
        } else {
            long foundLinear = Util.linearSearchOfList(list, queryPerson);
            lt1 = System.currentTimeMillis();
            searchTime1 = lt1 - lo1;
            System.out.printf(foundString,
                    foundLinear, queryList.size(), Util.formatTime(sortTime + searchTime1));
            System.out.printf("%s  - STOPPED, moved to linear search%n", lnrSrch);
        }
        System.out.printf("Searching time: %s%n%n", Util.formatTime(searchTime1));
    }

    protected static void linear(List<Person> list, List<Person> queryPerson) {
        System.out.println("Start searching (linear search)...");
        long lo = System.currentTimeMillis();
        int foundLinear = Util.linearSearchOfList(list, queryPerson);
        long lt = System.currentTimeMillis();
        long searchTime = lt - lo;
        System.out.printf("Found %s / %s entries. Time taken: %s%n%n",
                foundLinear, queryPerson.size(), Util.formatTime(searchTime));
    }

    protected static void quickAndBinary(List<Person> queryPerson,
                               List<Person> listNotSorted) {

        System.out.println("Start searching (quick sort + binary search)...");
        long lo = System.currentTimeMillis();
        Util.quickSort(listNotSorted, 0 , listNotSorted.size() - 1);
        long lt = System.currentTimeMillis();
        long quickSortTime = lt - lo;
        String quickStr = String.format("Sorting time: %s", Util.formatTime(quickSortTime));
        lo = System.currentTimeMillis();
        int foundBinary = Util.binarySearchOfList(listNotSorted, queryPerson);
        lt = System.currentTimeMillis();
        long searchTime = lt - lo;
        System.out.printf("Found %s / %s entries. Time taken: %s%n",
                foundBinary, queryPerson.size(), Util.formatTime(quickSortTime + searchTime));
        System.out.println(quickStr);
        System.out.printf("Searching time: %s%n%n", Util.formatTime(searchTime));
    }
}
