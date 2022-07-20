package phonebook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Util {

 /*   public static void bubleSort(String[] array) {
        for (int i  = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
               if (array[j + 1] < array[j]) {
                    String temp  = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
               }
            }
        }
    }*/
 static Comparator<Person> nameComparator = Comparator.comparing(Person::getName);

    public static boolean bubbleSort(List<Person> list) {
        long lo = System.currentTimeMillis();
        long lt = 0;
        int listSize = list.size();
        for (int i  = 0; i < listSize - 1; i++) {
            for (int j = 0; j < listSize - i - 1; j++) {
                 if (nameComparator.compare(list.get(j + 1), list.get(j)) < 0) {
                 Person temp  = list.get(j);
                 list.set(j, list.get(j + 1));
                 list.set(j + 1, temp);
                 }
                 lt = System.currentTimeMillis();
                 if (lt - lo > 38000) {
                     return false;
                 }
            }
        }
        return true;
    }

    public static void quickSort(List<Person> list, int left, int right) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int i = left;
        int j = right;
        Person temp;
        Person pivot = list.get((left + right) / 2);
        do {
            while (nameComparator.compare(list.get(i), pivot) < 0 && i < right) {
                i++;
            }
            while (nameComparator.compare(pivot, list.get(j)) < 0 && j > left) {
                j--;
            }
            if (i <= j) {
                temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j) {
            quickSort(list, left, j);
        }
        if (i < right) {
            quickSort(list, i, right);
        }
    }

    public static int jumpSearch(List<Person> list, Person p) {
        if (list == null || p == null || list.isEmpty()) {
            return 0;
        }
        int listSize = list.size();
        if (nameComparator.compare(list.get(0), p ) == 0) {
            return 1;
        } else if (listSize == 1) {
            return 0;
        } else {
            int blockLength = (int) Math.floor(Math.sqrt(list.size()));

            for (int i = blockLength; i < listSize; ) {
                int comparation = nameComparator.compare(list.get(i), p);
                if (comparation == 0) {
                    return 1;
                } else if (comparation > 0) {
                    List<Person> sublist = list.subList(i - blockLength + 1, i);
                    return linearSearchOfOnePerson(sublist, p);
                } else {
                    if (i == listSize - 1) {
                        return 0;
                    } else if (i + blockLength <= listSize - 1) {
                        i += blockLength;
                    } else {
                        i = listSize - 1;
                    }
                }
            }
        }
        return 0;
    }

    public static int binarySearchOfOnePerson(List<Person> list, Person p, int left, int right) { // left < right
        if (list == null || p == null || list.isEmpty()) {
            return 0;
        }
        int i = left;
        int j = right;
        Person temp;
        Person middle = list.get((left + right) / 2);
        if (nameComparator.compare(middle, p) == 0) {
            return 1;
        } else if (nameComparator.compare(p , middle) < 0) {
            return binarySearchOfOnePerson(list, p, left, (left + right) / 2 - 1);
        } else {
            return binarySearchOfOnePerson(list, p, (left + right) / 2 + 1, right);
        }
    }

    public static int linearSearchOfOnePerson(List<Person> list, Person p) {
        if (list == null || p == null || list.isEmpty()) {
            return 0;
        }
        if (list.stream().anyMatch(x -> nameComparator.compare(x, p) == 0)) {
            return 1;
        }
        return 0;
    }

    public static int linearSearchOfList(List<Person> list, List<Person> listToFind) {
        int counter = 0;
        for (Person p : listToFind) {
            counter += linearSearchOfOnePerson(list, p);
        }
        return counter;
    }

    public static int jumpSearchOfList(List<Person> list, List<Person> listToFind) {
        int counter = 0;
        for (Person p : listToFind) {
            counter += jumpSearch(list, p);
        }
        return counter;
    }

    public static int binarySearchOfList(List<Person> list, List<Person> listToFind) {
        int counter = 0;
        for (Person p : listToFind) {
            counter += binarySearchOfOnePerson(list, p, 0, list.size() - 1);
        }
        return counter;
    }

    public static String formatTime(long milisec) {
        long min = 0;
        long sec = 0;
        min = milisec / 60000;
        sec = (milisec % 60000) / 1000;
        milisec = milisec - min * 60000 - sec * 1000;
        return String.format("%s min. %s sec. %s ms.", min, sec, milisec);
    }
}
