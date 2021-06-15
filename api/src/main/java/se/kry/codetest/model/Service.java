package se.kry.codetest.model;

import java.util.Date;

public class Service {
    private Integer id;
    private String name;
    private String url;
    private Status status;
    private Date createdAt;

    public Service(String name, String url, Status status) {
        this.name = name;
        this.url = url;
        this.status = status;
    }

    public Service() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
