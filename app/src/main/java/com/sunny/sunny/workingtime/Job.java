package com.sunny.sunny.workingtime;

/**
 * Created by Sunny on 15/11/2017.
 */

public class Job {

    private double id;
    private String name;
    private float perHour;
    private float discountMonthly;
    private float increaseMonthly;


    public Job(double id, String name, float perHour, float discountMonthly, float increaseMonthly) {
        this.id = id;
        this.name = name;
        this.perHour = perHour;
        this.discountMonthly = discountMonthly;
        this.increaseMonthly = increaseMonthly;
    }

    public Job(String name, float perHour, float discountMonthly, float increaseMonthly) {
        this.name = name;
        this.perHour = perHour;
        this.discountMonthly = discountMonthly;
        this.increaseMonthly = increaseMonthly;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPerHour() {
        return perHour;
    }

    public void setPerHour(float perHour) {
        this.perHour = perHour;
    }

    public float getDiscountMonthly() {
        return discountMonthly;
    }

    public void setDiscountMonthly(float discountMonthly) {
        this.discountMonthly = discountMonthly;
    }

    public float getIncreaseMonthly() {
        return increaseMonthly;
    }

    public void setIncreaseMonthly(float increaseMonthly) {
        this.increaseMonthly = increaseMonthly;
    }
}
