package com.example.solarise.models;

import java.util.ArrayList;
import java.util.List;

public class User{

    private String name;
    private int age;
    private boolean earlyBird;
    private int height;
    private int weight;
    private int averageCaffeine;
    private List<Day> days = new ArrayList<Day>();

    public User() {}

    public User(String name, int age, boolean earlyBird, int height, int weight, int averageCaffeine) {

        this.name = name;
        this.age = age;
        this.earlyBird = earlyBird;
        this.height = height;
        this.weight = weight;
        this.averageCaffeine = averageCaffeine;

    }

    public void setUserName(String name) {this.name = name; }

    public void setAge(int age) {this.age = age; }

    public void setSleepStatus(boolean sleepStatus) {this.earlyBird = sleepStatus; }

    public void setHeight(int height) {this.height = height; }

    public void setWeight(int weight) {this.weight = weight; }

    public void setAverageCaffeine(int averageCaffeine) {this.averageCaffeine = averageCaffeine;}

    public String getUserName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public boolean getSleepPreference() {
        return this.earlyBird;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getAverageCaffeine() { return this.averageCaffeine; }

    public List<Day> getDays() { return this.days; }

    public void addDay(Day d) {

        if(days.size() > 7) {
            this.days.remove(0);
        }

        this.days.add(d);

    }
}
