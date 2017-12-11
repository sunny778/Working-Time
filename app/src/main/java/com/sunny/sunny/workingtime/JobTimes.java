package com.sunny.sunny.workingtime;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sunny on 15/11/2017.
 */

public class JobTimes implements Parcelable {

    private int id;
    private int year;
    private int month;
    private int day;
    private String startingTime;
    private String endingTime;
    private float totalHours;

    public JobTimes(int id, int year, int month, int day, String startingTime, String endingTime, float totalHours) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.totalHours = totalHours;
    }

    public JobTimes(int year, int month, int day, String startingTime, String endingTime, float totalHours) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.totalHours = totalHours;
    }

    protected JobTimes(Parcel in) {
        id = in.readInt();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        startingTime = in.readString();
        endingTime = in.readString();
        totalHours = in.readFloat();
    }

    public static final Creator<JobTimes> CREATOR = new Creator<JobTimes>() {
        @Override
        public JobTimes createFromParcel(Parcel in) {
            return new JobTimes(in);
        }

        @Override
        public JobTimes[] newArray(int size) {
            return new JobTimes[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(day);
        parcel.writeString(startingTime);
        parcel.writeString(endingTime);
        parcel.writeFloat(totalHours);
    }
}
