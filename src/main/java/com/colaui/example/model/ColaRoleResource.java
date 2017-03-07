package com.colaui.example.model;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "COLA_ROLE_RESOURCE")
public class ColaRoleResource {
    private String id;
    private String packageId;
    private String roleId;
    private String urlId;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PACKAGE_ID_")
    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
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

}
