package phonebook;


import java.util.Comparator;

public class Person {
    @Override
    public String toString() {
        return "Person{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private String phoneNumber;
    private String name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public Person() {
    }

    //Comparator<Person> nameComparator = (p1, p2) -> p1.getName().compareTo(p2.getName());
}
