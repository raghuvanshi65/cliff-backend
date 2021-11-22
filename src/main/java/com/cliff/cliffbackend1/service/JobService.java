package com.cliff.cliffbackend1.service;

import com.cliff.cliffbackend1.dto.JobModel;
import com.cliff.cliffbackend1.entity.Job;
import com.cliff.cliffbackend1.entity.Policy;
import com.cliff.cliffbackend1.exceptions.CustomException;
import com.cliff.cliffbackend1.exceptions.ResourceAlreadyExistsException;
import com.cliff.cliffbackend1.exceptions.ResourceNotFoundException;
import com.cliff.cliffbackend1.mapper.JobMapper;
import com.cliff.cliffbackend1.repository.JobRepository;
import com.cliff.cliffbackend1.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PolicyRepository policyRepository;

    /**
     * Parameters -
     * {@param jobModel}
     *
     * @return {@link JobModel} , the persisted job
     * @throws ResourceAlreadyExistsException - if job with same id exists
     */
    public JobModel addJob(JobModel jobModel) throws CustomException {
        if (jobModel.getId() != null && jobRepository.existsById(jobModel.getId()))
            throw new ResourceAlreadyExistsException("The Entity with id: " + jobModel.getId() + " already exists", 409, Job.class);
        Policy policyEntity = policyRepository.getById(jobModel.getPolicyId());
        return JobMapper.jobEntityToModel(jobRepository.save(JobMapper.JobModelToEntity(jobModel, policyEntity)));
    }


    /**
     * Parameters -
     * {@param id}
     *
     * @return {@link JobModel} , the existing Job
     * @throws ResourceNotFoundException - if job with id does not exist
     */
    public JobModel getJob(String id) throws CustomException {
        try {
            Job existingJob = jobRepository.getById(id);
            return JobMapper.jobEntityToModel(existingJob);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("The Entity with id: " + id + " does not exists", 404, Job.class);
        }
    }


    /**
     * Parameters -
     * {@param jobModel}
     *
     * @return {@link JobModel} , the existing job
     * @throws ResourceAlreadyExistsException - if job with id already exist
     */
    public JobModel updateJob(JobModel jobModel) throws CustomException {
        Job existingJob = null;
        try {
            existingJob = jobRepository.getById(jobModel.getId());
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("The Entity with id: " + jobModel.getId() + " does not exists", 404, Job.class);
        }
        Policy parentPolicy = policyRepository.getById(jobModel.getPolicyId());
        existingJob.updateJob(JobMapper.JobModelToEntity(jobModel, parentPolicy));
        jobRepository.save(existingJob);
        return JobMapper.jobEntityToModel(existingJob);
    }


    /**
     * Parameters -
     * {@param id}
     *
     * @throws ResourceNotFoundException - if job with id does not exist
     */
    public void deleteJob(String id) throws ResourceNotFoundException {
        try {
            jobRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage(), 404, Job.class);
        }
    }


    /**
     * Parameters -
     * {@param pageNumber}
     * {@param pageSize}
     *
     * @return {@link List<JobModel>} , contains list of all jobs in that page
     * @throws ResourceNotFoundException - if job with id does not exist
     */
    public List<JobModel> getAll(int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Job> policyPage = jobRepository.findAll(pageable);
        if (policyPage.getContent().isEmpty())
            throw new ResourceNotFoundException("No Entity exists", 404, Job.class);
        return policyPage.getContent().stream().map(JobMapper::jobEntityToModel).collect(Collectors.toList());
    }

    /**
     * Parameters -
     * {@param id} - the Parent Policy id
     *
     * @return {@link List<JobModel>} , the associated child jobs
     * @throws ResourceNotFoundException - if job with id does not exist
     * @throws CustomException           - if some other Exception occurs
     */
    public List<JobModel> getJobsByPolicyId(String id) throws Exception {
        try {
            List<Job> jobList = jobRepository.getJobByParentId(id);
            if(jobList.isEmpty())
                throw new ResourceNotFoundException("No Entity exists", 404, Policy.class);
            return jobList.stream().map(JobMapper::jobEntityToModel).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new CustomException(exception.getMessage(), 500);
        }
    }
}
