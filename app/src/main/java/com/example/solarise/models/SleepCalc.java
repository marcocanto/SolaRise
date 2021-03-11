package com.example.solarise.models;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

public class SleepCalc {
    private String desired_wake;
    private String desired_sleep;
    private int time_to_sleep;
    private ArrayList<String> wakeup_times;
    private ArrayList<String> sleep_times;
    private int rem = 90;
    private Button lol;

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

    public ArrayList<String> calc_times(String sleep_time, boolean sleep_pref, int age){
        if (sleep_pref){
            if (age < 18){
                sleep_times = sleep_now(sleep_time);
                Collections.swap(sleep_times,0,1);
                return sleep_times;

            }
            else{
                sleep_times = sleep_now(sleep_time);
                return sleep_times;
            }
        }
        else{
            if (age < 18){
                sleep_times = wake_now(sleep_time);
                Collections.swap(sleep_times,0,1);
                return sleep_times;
            }
            else{
                sleep_times = wake_now(sleep_time);

                return sleep_times;
            }

        }
    }

    private ArrayList<String> sleep_now (String sleep_time){
        wakeup_times = new ArrayList<String>();
        String []time = sleep_time.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        String am_pm1;

        int cycle1H = hour + 4;
        int cycle1M = minute + 30 + time_to_sleep;
        if (cycle1M >= 60){
            cycle1M = cycle1M - 60;
            cycle1H += 1;
        }

        if (cycle1H >= 24 || cycle1H < 12){
            am_pm1 = " PM";
        }
        else{
            am_pm1 = " AM";
        }

        if (cycle1H > 12) {
            cycle1H = cycle1H - 12;
        }




        if (cycle1M == 0) {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M) + "0" + am_pm1);

        }
        else if(cycle1M < 10){
            wakeup_times.add(String.valueOf(cycle1H) + ":0" + String.valueOf(cycle1M)+ am_pm1);

        }
        else {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M)+ am_pm1);
        }




        int cycle2H = hour + 6;
        int cycle2M = minute + time_to_sleep;
        String am_pm2;
        if (cycle2M >= 60){
            cycle2M = cycle2M - 60;
            cycle2H += 1;
        }
        if (cycle2H >= 24 || cycle2H < 12){
            am_pm2 = " PM";
        }
        else{
            am_pm2 = " AM";
        }


        if (cycle2H > 12) {
            cycle2H = cycle2H - 12;
        }




        if (cycle2M == 0) {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M) + "0"+ am_pm2);


        }
        else if(cycle2M < 10){
            wakeup_times.add(String.valueOf(cycle2H) + ":0" + String.valueOf(cycle2M)+ am_pm2);

        }
        else {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M)+ am_pm2);
        }



        int cycle3H = hour + 7;
        int cycle3M = minute + 30 + time_to_sleep;

        String am_pm3;
        if (cycle3M >= 60){
            cycle3M = cycle3M - 60;
            cycle3H += 1;
        }

        if (cycle3H >= 24 || cycle3H < 12){
            am_pm3 = " PM";
        }
        else{
            am_pm3 = " AM";
        }

        if (cycle3H > 12) {
            cycle3H = cycle3H - 12;
        }



        if (cycle3M == 0) {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M) + "0"+ am_pm3);

        }
        else if(cycle3M < 10){
            wakeup_times.add(String.valueOf(cycle3H) + ":0" + String.valueOf(cycle3M)+ am_pm3);

        }
        else {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M)+ am_pm3);
        }
        int cycle4H = hour + 9;
        int cycle4M = minute + time_to_sleep;
        String am_pm4;
        if (cycle4M >= 60){
            cycle4M = cycle4M - 60;
            cycle4H += 1;
        }
        if (cycle4H >= 24 || cycle4H < 12){
            am_pm4 = " PM";
        }
        else{
            am_pm4 = " AM";
        }


        if (cycle4H > 12) {
            cycle4H = cycle4H - 12;
        }



        if (cycle4M == 0) {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M) + "0"+ am_pm4);

        }
        else if(cycle4M < 10){
            wakeup_times.add(String.valueOf(cycle4H) + ":0" + String.valueOf(cycle4M)+ am_pm4);

        }
        else {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M)+ am_pm4);
        }
        Collections.reverse(wakeup_times);
        Collections.swap(wakeup_times, 0, 1);
        return wakeup_times;
    }

    private ArrayList<String> wake_now (String sleep_time){
        wakeup_times = new ArrayList<String>();
        String []time = sleep_time.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        String am_pm4;

        int cycle4H = hour + 3;
        int cycle4M = minute + time_to_sleep;

        if (cycle4M >= 60){
            cycle4M = cycle4M - 60;
            cycle4H += 1;
        }
        if (cycle4H >= 24 || cycle4H < 12){
            am_pm4 = " PM";
        }
        else{
            am_pm4 = " AM";
        }

        if (cycle4H > 12) {
            cycle4H = cycle4H - 12;
        }



        if (cycle4M == 0) {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M) + "0"+ am_pm4);

        }
        else if(cycle4M < 10){
            wakeup_times.add(String.valueOf(cycle4H) + ":0" + String.valueOf(cycle4M)+ am_pm4);

        }
        else {
            wakeup_times.add(String.valueOf(cycle4H) + ":" + String.valueOf(cycle4M)+ am_pm4);
        }

        int cycle1H = hour + 4;
        int cycle1M = minute + 30 + time_to_sleep;
        String am_pm1;

        if (cycle1M >= 60){
            cycle1M = cycle1M - 60;
            cycle1H += 1;
        }
        if (cycle1H >= 24 || cycle1H < 12){
            am_pm1 = " PM";
        }
        else{
            am_pm1 = " AM";
        }

        if (cycle1H > 12) {
            cycle1H = cycle1H - 12;
        }




        if (cycle1M == 0) {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M) + "0"+ am_pm1);

        }
        else if(cycle1M < 10){
            wakeup_times.add(String.valueOf(cycle1H) + ":0" + String.valueOf(cycle1M)+ am_pm1);

        }
        else {
            wakeup_times.add(String.valueOf(cycle1H) + ":" + String.valueOf(cycle1M)+ am_pm1);
        }




        int cycle2H = hour + 6;
        int cycle2M = minute + time_to_sleep;
        String am_pm2;
        if (cycle2M >= 60){
            cycle2M = cycle2M - 60;
            cycle2H += 1;
        }
        if (cycle2H >= 24 || cycle2H < 12){
            am_pm2 = " PM";
        }
        else{
            am_pm2 = " AM";
        }

        if (cycle2H > 12) {
            cycle2H = cycle2H - 12;
        }




        if (cycle2M == 0) {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M) + "0"+ am_pm2);

        }
        else if(cycle2M < 10){
            wakeup_times.add(String.valueOf(cycle2H) + ":0" + String.valueOf(cycle2M)+ am_pm2);

        }
        else {
            wakeup_times.add(String.valueOf(cycle2H) + ":" + String.valueOf(cycle2M)+ am_pm2);
        }



        int cycle3H = hour + 7;
        int cycle3M = minute + 30 + time_to_sleep;
        String am_pm3;
        if (cycle3M >= 60){
            cycle3M = cycle3M - 60;
            cycle3H += 1;
        }
        if (cycle3H >= 24 || cycle3H < 12){
            am_pm3 = " PM";
        }
        else{
            am_pm3 = " AM";
        }

        if (cycle3H > 12) {
            cycle3H = cycle3H - 12;
        }


        if (cycle3M == 0) {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M) + "0" + am_pm3);

        }
        else if(cycle3M < 10){
            wakeup_times.add(String.valueOf(cycle3H) + ":0" + String.valueOf(cycle3M)+ am_pm3);

        }
        else {
            wakeup_times.add(String.valueOf(cycle3H) + ":" + String.valueOf(cycle3M)+ am_pm3);
        }


        return wakeup_times;



    }



    public static void main(String[] args) {
        SleepCalc sleepCalc = new SleepCalc();
        System.out.println(sleepCalc.sleep_now("8:00"));
        System.out.println(sleepCalc.wake_now("8:00"));

    }


}