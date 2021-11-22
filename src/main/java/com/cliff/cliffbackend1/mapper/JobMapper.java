package com.cliff.cliffbackend1.mapper;

import com.cliff.cliffbackend1.dto.JobModel;
import com.cliff.cliffbackend1.entity.Job;
import com.cliff.cliffbackend1.entity.Policy;

import java.sql.Timestamp;

/**
 * This is basically a mapper class
 * that is used for object conversions between
 * {@link JobModel}
 * {@link Job}
 * */
public class JobMapper {
    public static Job JobModelToEntity(JobModel jobModel , Policy policy){
        Job jobEntity = new Job();
        jobEntity.setId(jobModel.getId()!=null ? jobModel.getId() : null);
        jobEntity.setJobName(jobModel.getJobName());
        jobEntity.setCreatedAt(jobModel.getCreatedAt()!=null ? jobModel.getCreatedAt() : new Timestamp(System.currentTimeMillis()));
        jobEntity.setUpdatedAt(jobModel.getUpdatedAt()!=null ? jobModel.getUpdatedAt() : new Timestamp(System.currentTimeMillis()));
        jobEntity.setPolicy(policy);
        return jobEntity;
    }

    public static JobModel jobEntityToModel(Job job){
        JobModel jobModel = new JobModel();
        jobModel.setId(job.getId());
        jobModel.setJobName(job.getJobName());
        jobModel.setCreatedAt(job.getCreatedAt());
        jobModel.setUpdatedAt(job.getUpdatedAt());
        jobModel.setPolicyId(job.getPolicy().getId());
        return jobModel;
    }
}
