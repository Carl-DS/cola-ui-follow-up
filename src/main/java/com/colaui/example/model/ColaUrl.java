package com.colaui.example.model;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "cola_url")
public class ColaUrl {
    private String id;
    private String companyId;
    private String desc;
    private boolean forNavigation;
    private String icon;
    private String name;
    private Integer order;
    private String parentId;
    private String systemId;
    private String url;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "COMPANY_ID_")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name = "DESC_")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(name = "FOR_NAVIGATION_")
    public boolean isForNavigation() {
        return forNavigation;
    }

    public void setForNavigation(boolean forNavigation) {
        this.forNavigation = forNavigation;
    }

    @Column(name = "ICON_")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "NAME_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ORDER_")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Column(name = "PARENT_ID_")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "SYSTEM_ID_")
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Column(name = "URL_")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
