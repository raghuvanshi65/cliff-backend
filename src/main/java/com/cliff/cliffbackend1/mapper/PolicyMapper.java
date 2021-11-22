package com.cliff.cliffbackend1.mapper;

import com.cliff.cliffbackend1.dto.JobModel;
import com.cliff.cliffbackend1.dto.PolicyModel;
import com.cliff.cliffbackend1.entity.Job;
import com.cliff.cliffbackend1.entity.Policy;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is basically a mapper class
 * that is used for object conversions between
 * {@link Policy}
 * {@link PolicyModel}
 * */
public class PolicyMapper {
    public static PolicyModel policyEntityToModel(Policy policyEntity) {
        PolicyModel policyModel = new PolicyModel();
        policyModel.setId(policyEntity.getId());
        policyModel.setName(policyEntity.getName());
        if (policyEntity.getDefination() != null)
            policyModel.setDefination(policyEntity.getDefination());

        policyModel.setCreatedAt(policyEntity.getCreatedAt());
        policyModel.setUpdatedAt(policyEntity.getUpdatedAt());
        policyModel.setStatus(policyEntity.isStatus());

        List<String> JobIdList = policyEntity.getJobsList().stream().map(Job::getId).collect(Collectors.toList());
        policyModel.setJobsList(JobIdList);
        return policyModel;
    }


    public static Policy policyModelToEntity(PolicyModel policyModel , List<Job> jobList) {
        Policy policy = new Policy();
        policy.setId(policyModel.getId() != null ? policyModel.getId() : null);
        policy.setName(policyModel.getName());
        policy.setDefination(policyModel.getDefination() != null ? policyModel.getDefination() : null);
        policy.setCreatedAt(policyModel.getCreatedAt() != null ? policyModel.getCreatedAt() : new Timestamp(System.currentTimeMillis()));
        policy.setUpdatedAt(policyModel.getUpdatedAt() != null ? policyModel.getUpdatedAt() : new Timestamp(System.currentTimeMillis()));
        policy.setStatus(policyModel.isStatus());
        policy.setJobsList(jobList);
        return policy;
    }
}
