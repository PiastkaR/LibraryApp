package com.javastart.library.model;

import java.time.MonthDay;
import java.util.Objects;

public class Magazine extends Publication {
    public static final String TYPE = "Magazine";

    private int month;
    private MonthDay monthDay;
    private String language;

    public Magazine(String title, String publisher, String language, int year, int month, int monthDay) {
        super(title, publisher, year);
        this.language = language;
        this.month = month;
        this.monthDay = MonthDay.of(month, monthDay);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public MonthDay  getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay  monthDay) {
        this.monthDay = monthDay;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getTitle() + ";" + getPublisher() + ";" + getYear() + ";" + month + ";" + monthDay + ";" + language + "";
    }

    @Override
    public String toString() {
        return super.toString() + ", " + month + ", " + monthDay + ", " + language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return month == magazine.month &&
                monthDay == magazine.monthDay &&
                Objects.equals(language, magazine.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), month, monthDay, language);
    }
}
