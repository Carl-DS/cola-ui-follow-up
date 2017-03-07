package com.colaui.example.model;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name = "COLA_MESSAGE_TEMPLATE")
public class ColaMessageTemplate {
    private String id;
    private String companyId;
    private String content;
    private String name;
    private String type;

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

    @Column(name = "CONTENT_")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "NAME_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TYPE_")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
