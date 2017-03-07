package com.colaui.example.model;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "COLA_URL_COMPONENT")
public class ColaUrlComponent {
    private String id;
    private String authorityType;
    private String roleId;
    private String urlId;
    private String componentId;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "AUTHORITY_TYPE_")
    public String getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(String authorityType) {
        this.authorityType = authorityType;
    }

    @Column(name = "ROLE_ID_")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Column(name = "URL_ID_")
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Column(name = "COMPONENT_ID_")
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

}
