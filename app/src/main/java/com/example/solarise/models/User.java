package com.example.solarise.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String uid;
    private int age;
    private boolean earlyBird;
    private String test;
    private int averageCaffeine;
    private List<Day> days = new ArrayList<Day>();

    public User() {}

    public User(String name, int age, boolean earlyBird, int averageCaffeine, String uid) {

        this.name = name;
        this.age = age;
        this.earlyBird = earlyBird;
        this.averageCaffeine = averageCaffeine;
        this.uid = uid;

    }

    public void setUserName(String name) {this.name = name; }

    public void setAge(int age) {this.age = age; }

    public void setSleepStatus(boolean sleepStatus) {this.earlyBird = sleepStatus; }

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

    public int getAverageCaffeine() { return this.averageCaffeine; }

    public String getUid(){
        return this.uid;
    }

    public List<Day> getDays() { return this.days; }

    public void addDay(Day d) {

        if(days.size() > 7) {
            this.days.remove(0);
        }

        this.days.add(d);

    }

    public String toString(){
        return "Name: " + name + " " + "Age: " + age + " " + "Early Bird: " + earlyBird + " " + "averageCaffeine: " + averageCaffeine;
    }
}