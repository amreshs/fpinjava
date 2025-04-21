package com.fpinjava.advancedtrees.exercise11_08;

import java.util.Comparator;

public class Person implements Comparator<Person> {
    private final String name;
    private final int age;

    public Person(final String name, final int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    public int compare(final Person o1, final Person o2) {
        return Integer.compare(o1.getAge(), o2.getAge());
    }

    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
}
