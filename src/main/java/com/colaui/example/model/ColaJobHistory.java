package com.colaui.example.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "cola_job_history")
public class ColaJobHistory {
    private String id;
    private Timestamp endDate;
    private String exceptionMessage;
    private String jobId;
    private Timestamp startDate;
    private Boolean successful;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "END_DATE")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Column(name = "EXCEPTION_MESSAGE_")
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Column(name = "JOB_ID_")
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Column(name = "START_DATE")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Column(name = "SUCCESSFUL_")
    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

}
