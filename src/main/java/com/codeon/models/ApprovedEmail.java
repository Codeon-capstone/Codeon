package com.codeon.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name="approved_emails")
public class ApprovedEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="approved_emails_security_roles",
            joinColumns={@JoinColumn(name="approved_email_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")}
    )
    private List<SecurityRole> roleList;
    public ApprovedEmail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SecurityRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SecurityRole> roleList) {
        this.roleList = roleList;
    }
}
