package com.colaui.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "COLA_URL")
public class ColaUrl {
    private String id;
    private String companyId;
    private String desc;
    private boolean forNavigation;
    private String icon;
    private String label;
    private Integer order;
    private String parentId;
    private String systemId;
    private String path;
    private boolean closeable;

    private ColaUrl parent;
    private Collection<ColaUrl> menus;

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

    @Column(name = "LABEL_")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    @Column(name = "PATH_")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "CLOSEABLE_")
    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID_", updatable = false, insertable = false)
    @JsonIgnore
    public ColaUrl getParent() {
        return parent;
    }

    public void setParent(ColaUrl parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID_", insertable = false, updatable = false)
    public Collection<ColaUrl> getMenus() {
        return menus;
    }

    public void setMenus(Collection<ColaUrl> menus) {
        this.menus = menus;
    }
}
