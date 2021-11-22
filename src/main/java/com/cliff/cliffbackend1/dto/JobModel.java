package com.cliff.cliffbackend1.dto;

import com.cliff.cliffbackend1.entity.Policy;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Basic POJO class that is used as a data transfer object
 * uses {@link javax.validation} for validating the requests and responses
 * */
public class JobModel {

    private String id;

    @NotEmpty
    private String jobName;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @NotNull
    private String policyId;

    public JobModel() {
    }

    public JobModel(String id, String jobName, Timestamp createdAt, Timestamp updatedAt, String policyId) {
        this.id = id;
        this.jobName = jobName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.policyId = policyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
}
