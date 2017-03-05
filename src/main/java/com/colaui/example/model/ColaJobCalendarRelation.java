package com.colaui.example.model;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "cola_job_calendar_relation")
public class ColaJobCalendarRelation {
    private String id;
    private String calendarId;
    private String jobId;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CALENDAR_ID_")
    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    @Column(name = "JOB_ID_")
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

}
