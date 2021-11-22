package com.cliff.cliffbackend1.dto;

import com.cliff.cliffbackend1.entity.Job;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Basic POJO class that is used as a data transfer object
 * uses {@link javax.validation} for validating the requests and responses
 * also acts as a wrapper for our responses
 */
public class PolicyModel {

    private String id;

    @NotEmpty
    private String name;

    private String defination;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @NotNull
    private boolean status;

    private List<String> jobIdList;

    public PolicyModel() {
    }

    public PolicyModel(String id, @NotEmpty String name, String defination, Timestamp createdAt, Timestamp updatedAt, boolean status) {
        this.id = id;
        this.name = name;
        this.defination = defination;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        jobIdList = new ArrayList<>();
    }

    public PolicyModel(String id, String name, String defination, Timestamp createdAt, Timestamp updatedAt, boolean status, List<String> jobIdList) {
        this.id = id;
        this.name = name;
        this.defination = defination;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.jobIdList = jobIdList;
    }

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

    public String getDefination() {
        return defination;
    }

    public void setDefination(String defination) {
        this.defination = defination;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getJobsList() {
        return jobIdList;
    }

    public void setJobsList(List<String> jobIdList) {
        this.jobIdList = jobIdList;
    }

    @Override
    public String toString() {
        return "PolicyModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", defination='" + defination + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", jobIdList=" + jobIdList +
                '}';
    }
}
