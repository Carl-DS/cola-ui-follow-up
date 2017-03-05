package com.colaui.example.model;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "cola_user_dept")
public class ColaUserDept {
    private String id;
    private String deptId;
    private String username;

    @Id
    @Column(name = "ID_")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "DEPT_ID_")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Column(name = "USERNAME_")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
