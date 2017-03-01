package com.colaui.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by carl.li on 2017/2/14.
 */
@Entity
@Table(name="COLA_AREA")
public class ColaArea {
    private String id;
    private String areaName;
    private  String level;
    private String parentId;

    public ColaArea(){}

    public ColaArea(String id, String areaName, String level, String parentId) {
        this.id = id;
        this.areaName = areaName;
        this.level = level;
        this.parentId = parentId;
    }

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "AREA_NAME_")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Column(name = "LEVEL_")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "PARENT_ID_")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
