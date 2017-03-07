package com.colaui.example.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "COLA_JOB")
public class ColaJob {
    private String id;
    private String beanId;
    private String companyId;
    private String cronExpression;
    private String desc;
    private Timestamp endDate;
    private String name;
    private Timestamp startDate;
    private String state;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "BEAN_ID_")
    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    @Column(name = "COMPANY_ID_")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name = "CRON_EXPRESSION_")
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column(name = "DESC_")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(name = "END_DATE")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Column(name = "NAME_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "START_DATE")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Column(name = "STATE_")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
