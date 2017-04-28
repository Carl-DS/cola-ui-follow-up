package com.colaui.system.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Entity
@Table(name="COLA_URL_COMPONENT")
public class ColaUrlComponent implements java.io.Serializable{
    private static final long serialVersionUID = 1784126739852822273L;

    @Id
    @Column(name="ID_",length=60)
    private String id;

    @Column(name="URL_ID_",length=60)
    private String urlId;

    @Column(name="ROLE_ID_",length=60)
    private String roleId;

    @Column(name="AUTHORITY_TYPE_",length=10,nullable=false)
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @ManyToOne(cascade=CascadeType.ALL,targetEntity=ColaComponent.class,fetch=FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="COMPONENT_ID_")
    private ColaComponent component;

    //@Column(name="COMPONENT_ID_",length=60)
    //private String componentId;

    @Transient
    private ColaUrl url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public ColaComponent getComponent() {
        return component;
    }

    public void setComponent(ColaComponent component) {
        this.component = component;
    }

    public ColaUrl getUrl() {
        return url;
    }

    public void setUrl(ColaUrl url) {
        this.url = url;
    }
}
