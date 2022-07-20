package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> queryList = Files.readAllLines((Paths.get("/Users/beatka/Downloads/find.txt")));
        List<String> stringList = Files.readAllLines(Paths.get("/Users/beatka/Downloads/directory.txt"));
        List<Person> queryPerson =  queryList.stream().map(x -> new Person("", x)).collect(toList());

        List<Person> list = OutputManager.createPersonList(stringList);
        List<Person> listNotSorted2 = new ArrayList<>();
        listNotSorted2.addAll(list);
        List<Person> listNotSorted = new ArrayList<>();
        listNotSorted.addAll(list);


        OutputManager.linear(list, queryPerson);
        OutputManager.bubbleAndJump(queryList, list, queryPerson);
        OutputManager.quickAndBinary(queryPerson, listNotSorted);
        OutputManager.hashSearch(queryList, listNotSorted2);

    }
}
