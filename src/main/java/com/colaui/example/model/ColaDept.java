package com.colaui.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "COLA_DEPT")
public class ColaDept {
    private String id;
    private String companyId;
    private Timestamp createDate;
    private String desc;
    private String name;
    private String parentId;
    private String icon;
    private Integer order;

    private ColaDept parent;
    private Collection<ColaDept> depts;

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

    @Column(name = "CREATE_DATE_")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Column(name = "DESC_")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(name = "NAME_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PARENT_ID_")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "ICON_")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "ORDER_")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID_", updatable = false, insertable = false)
    @JsonIgnore
    public ColaDept getParent() {
        return parent;
    }

    public void setParent(ColaDept parent) {
        this.parent = parent;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID_", updatable = false, insertable = false)
    public Collection<ColaDept> getDepts() {
        return depts;
    }

    public void setDepts(Collection<ColaDept> depts) {
        this.depts = depts;
    }
}
