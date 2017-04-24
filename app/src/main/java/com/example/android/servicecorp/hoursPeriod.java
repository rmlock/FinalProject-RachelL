package com.example.android.servicecorp;

import java.io.Serializable;

/**
 * Created by Rachel Lockerman on 4/21/2017.
 */

public class hoursPeriod implements Serializable{
    int day1Hours;
    int day2Hours;
    int day3Hours;
    int day4Hours;
    int day5Hours;
    int day6Hours;
    int day7Hours;
    String period;

    public int getDay1Hours() {
        return day1Hours;
    }

    public void setDay1Hours(int day1Hours) {
        this.day1Hours = day1Hours;
    }

    public int getDay2Hours() {
        return day2Hours;
    }

    public void setDay2Hours(int day2Hours) {
        this.day2Hours = day2Hours;
    }

    public int getDay3Hours() {
        return day3Hours;
    }

    public void setDay3Hours(int day3Hours) {
        this.day3Hours = day3Hours;
    }

    public int getDay4Hours() {
        return day4Hours;
    }

    public void setDay4Hours(int day4Hours) {
        this.day4Hours = day4Hours;
    }

    public int getDay5Hours() {
        return day5Hours;
    }

    public void setDay5Hours(int day5Hours) {
        this.day5Hours = day5Hours;
    }

    public int getDay6Hours() {
        return day6Hours;
    }

    public void setDay6Hours(int day6Hours) {
        this.day6Hours = day6Hours;
    }

    public int getDay7Hours() {
        return day7Hours;
    }

    public void setDay7Hours(int day7Hours) {
        this.day7Hours = day7Hours;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public hoursPeriod(int day1Hours, int day2Hours, int day3Hours, int day4Hours, int day5Hours, int day6Hours, int day7Hours, String period) {
        this.day1Hours = day1Hours;
        this.day2Hours = day2Hours;
        this.day3Hours = day3Hours;
        this.day4Hours = day4Hours;
        this.day5Hours = day5Hours;
        this.day6Hours = day6Hours;
        this.day7Hours = day7Hours;
        this.period = period;
    }
    public hoursPeriod(){

    }
}
