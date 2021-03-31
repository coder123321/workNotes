package com.sh.entity;


import java.util.Date;

/**
 * Created by Administrator on 2020/12/28.
 */

public class BaseUser {
    private String id;
    private String name;
    private String password;
    private String status;
    private Date createdt;
    private Double pagecount;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public void setCreatedt(Date createdt) {
        this.createdt = createdt;
    }

    public Double getPagecount() {
        return pagecount;
    }

    public void setPagecount(Double pagecount) {
        this.pagecount = pagecount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
