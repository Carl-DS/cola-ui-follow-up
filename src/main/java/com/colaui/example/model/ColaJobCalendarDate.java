package com.colaui.example.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "cola_job_calendar_date")
public class ColaJobCalendarDate {
    private String id;
    private Timestamp calendarDate;
    private String calendarId;
    private Integer dayOfMonth;
    private Integer dayOfWeek;
    private Integer monthOfYear;
    private String name;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CALENDAR_DATE_")
    public Timestamp getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Timestamp calendarDate) {
        this.calendarDate = calendarDate;
    }

    @Column(name = "CALENDAR_ID_")
    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    @Column(name = "DAY_OF_MONTH_")
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Column(name = "DAY_OF_WEEK_")
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Column(name = "MONTH_OF_YEAR_")
    public Integer getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(Integer monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    @Column(name = "NAME_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
