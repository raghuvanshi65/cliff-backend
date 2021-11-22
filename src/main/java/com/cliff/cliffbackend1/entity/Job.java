package com.cliff.cliffbackend1.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Parameters needed to configure the object
 * {@param id}
 * {@param jobName}
 * {@param createdAt}
 * {@param updatedAt}
 * {@param policy} Entity associated with
 * This class represents the Job Entity
 */
@Entity(name = "Job")
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Policy policy;

    public Job() {
    }

    public Job(String id, String jobName, Timestamp createdAt, Timestamp updatedAt, Policy policy) {
        this.id = id;
        this.jobName = jobName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.policy = policy;
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

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Job)) return false;
        return id != null && id.equals(((Job) obj).getId());
    }

    public void updateJob(Job newJob) {
        this.jobName = newJob.getJobName();
        this.policy = newJob.getPolicy();
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
