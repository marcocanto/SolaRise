package com.example.solarise.models;

import java.util.ArrayList;

public class SleepCalc {
    private String desired_wake;
    private String desired_sleep;
    private int time_to_sleep;
    private ArrayList<String> wakeup_times;
    private ArrayList<String> sleep_times;
    private int rem = 90;

    public SleepCalc(){

    }

    public SleepCalc(int time_to_sleep){
        if (time_to_sleep >= 60) {
            this.time_to_sleep = 60;
        }
        else {
            this.time_to_sleep = 60;
        }

    }

    private ArrayList<String> sleep_now (String sleep_time){
        wakeup_times = new ArrayList<String>();
        String []time = sleep_time.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        int cycle1H = hour + 4;
        int cycle1M = minute + 30 + time_to_sleep;

        if (cycle1H > 12) {
            cycle1H = cycle1H - 12;
        }


        if (cycle1M > 60){
            cycle1M = cycle1M - 60;
            cycle1H += 1;
        }

        if (cycle1M == 0) {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M));
        }




        int cycle2H = hour + 6;
        int cycle2M = minute + time_to_sleep;

        if (cycle2H > 12) {
            cycle2H = cycle2H - 12;
        }


        if (cycle2M > 60){
            cycle2M = cycle2M - 60;
            cycle2H += 1;
        }

        if (cycle2M == 0) {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M));
        }



        int cycle3H = hour + 7;
        int cycle3M = minute + 30 + time_to_sleep;

        if (cycle3H > 12) {
            cycle3H = cycle3H - 12;
        }

        if (cycle3M > 60){
            cycle3M = cycle3M - 60;
            cycle3H += 1;
        }

        if (cycle3M == 0) {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M));
        }
        int cycle4H = hour + 9;
        int cycle4M = minute + time_to_sleep;

        if (cycle4H > 12) {
            cycle4H = cycle4H - 12;
        }

        if (cycle4M > 60){
            cycle4M = cycle3M - 60;
            cycle4H += 1;
        }

        if (cycle2M == 0) {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M));
        }

        return wakeup_times;
    }

    private ArrayList<String> wake_now (String sleep_time){
        wakeup_times = new ArrayList<String>();
        String []time = sleep_time.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        int cycle4H = hour + 3;
        int cycle4M = minute + time_to_sleep;

        if (cycle4H > 12) {
            cycle4H = cycle4H - 12;
        }

        if (cycle4M > 60){
            cycle4M = cycle4M - 60;
            cycle4H += 1;
        }

        if (cycle4M == 0) {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M));
        }

        int cycle1H = hour + 4;
        int cycle1M = minute + 30 + time_to_sleep;

        if (cycle1H > 12) {
            cycle1H = cycle1H - 12;
        }


        if (cycle1M > 60){
            cycle1M = cycle1M - 60;
            cycle1H += 1;
        }

        if (cycle1M == 0) {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M));
        }




        int cycle2H = hour + 6;
        int cycle2M = minute + time_to_sleep;

        if (cycle2H > 12) {
            cycle2H = cycle2H - 12;
        }


        if (cycle2M > 60){
            cycle2M = cycle2M - 60;
            cycle2H += 1;
        }

        if (cycle2M == 0) {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M));
        }



        int cycle3H = hour + 7;
        int cycle3M = minute + 30 + time_to_sleep;

        if (cycle3H > 12) {
            cycle3H = cycle3H - 12;
        }

        if (cycle3M > 60){
            cycle3M = cycle3M - 60;
            cycle3H += 1;
        }

        if (cycle3M == 0) {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M) + "0");

        }
        else {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M));
        }


        return wakeup_times;



    }



    public static void main(String[] args) {
        SleepCalc sleepCalc = new SleepCalc();
        System.out.println(sleepCalc.sleep_now("8:00"));
        System.out.println(sleepCalc.wake_now("8:00"));

    }


}