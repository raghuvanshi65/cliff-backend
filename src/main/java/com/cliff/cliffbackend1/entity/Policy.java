package com.cliff.cliffbackend1.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Parameters needed to configure the object
 * {@param id}
 * {@param name}
 * {@param defination}
 * {@param createdAt}
 * {@param updatedAt}
 * {@param status}
 * {@param List<Job>} List of Child Entities
 * This class represents the Job Entity
 * */
@Entity(name = "Policy")
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "policy_name")
    @NotEmpty
    private String name;

    @Column(name = "defination")
    private String defination;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @NotNull
    @Column(name = "policy_status")
    private boolean status;

    @OneToMany(mappedBy = "policy", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH}, orphanRemoval = true)
    private List<Job> jobsList;


    public Policy() {
    }

    public Policy(String id, @NotEmpty String name, String defination, Timestamp createdAt, Timestamp updatedAt, boolean status) {
        this.id = id;
        this.name = name;
        this.defination = defination;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        jobsList = new ArrayList<>();
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

    public boolean addJob(Job job) {
        if (job == null)
            return false;
        job.setPolicy(this);
        this.jobsList.add(job);
        return false;
    }

//    public void addAllJobs(List<Job> jobs) {
//        if (jobs != null && !jobs.isEmpty()) {
//            for (Job job : jobs)
//                job.setPolicy(this);
//        }
//        this.jobsList.addAll(jobs == null ? new ArrayList<>() : jobs);
//    }

    public List<Job> getJobsList() {
        return jobsList;
    }

    public void setJobsList(List<Job> jobsList) {
        if (this.jobsList != null)
            this.jobsList.clear();
        else
            this.jobsList = new ArrayList<>();
        this.jobsList.addAll(jobsList);
    }

    public boolean removeJob(Job job) {
        return this.jobsList.remove(job);
    }

    public void updatePolicy(Policy policy) {
        this.id = policy.id;
        this.name = policy.name;
        this.defination = policy.defination;
        this.createdAt = policy.createdAt;
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.status = policy.status;
        setJobsList(policy.getJobsList());
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", defination='" + defination + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", jobsList=" + jobsList +
                '}';
    }
}
