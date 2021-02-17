package com.example.solarise;

public class User{

    private String name;
    private int age;
    private boolean earlyBird;
    private int height;
    private int weight;

    public User() {}

    public User(String name, int age, boolean earlyBird, int height, int weight) {

        this.name = name;
        this.age = age;
        this.earlyBird = earlyBird;
        this.height = height;
        this.weight = weight;
    }

    public void setUserName(String name) {this.name = name; }

    public void setAge(int age) {this.age = age; }

    public void setSleepStatus(boolean sleepStatus) {this.earlyBird = sleepStatus; }

    public void setHeight(int height) {this.height = height; }

    public void setWeight(int weight) {this.weight = weight; }

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
}
