package com.cliff.cliffbackend1.service;

import com.cliff.cliffbackend1.dto.PolicyModel;
import com.cliff.cliffbackend1.entity.Job;
import com.cliff.cliffbackend1.entity.Policy;
import com.cliff.cliffbackend1.exceptions.CustomException;
import com.cliff.cliffbackend1.exceptions.ResourceAlreadyExistsException;
import com.cliff.cliffbackend1.exceptions.ResourceNotFoundException;
import com.cliff.cliffbackend1.mapper.PolicyMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class basically acts as a service layer class that is used by the contrtoller
 * {@link com.cliff.cliffbackend1.controllers.PolicyController} to use the business logic for the webservices
 */
@Component
public class PolicyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyService.class);

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private JobRepository jobRepository;

    /**
     * Parameters -
     * {@param policyModel}
     *
     * @return {@link PolicyModel} , the persisted Policy
     * @throws ResourceAlreadyExistsException - if policy with same id exists
     */
    public PolicyModel addPolicy(PolicyModel policyModel) throws ResourceAlreadyExistsException {
        if (policyModel.getId() != null && policyRepository.existsById(policyModel.getId()))
            throw new ResourceAlreadyExistsException("The Entity with id: " + policyModel.getId() + " already exists", 409, Policy.class);
        List<Job> jobList = jobRepository.findByJobIds(policyModel.getJobsList() != null ? policyModel.getJobsList() : new ArrayList<>());
        return PolicyMapper.policyEntityToModel(policyRepository.save(PolicyMapper.policyModelToEntity(policyModel, jobList)));
    }

    /**
     * Parameters -
     * {@param id}
     *
     * @return {@link PolicyModel} , the existing Policy
     * @throws ResourceNotFoundException - if policy with id does not exist
     */
    public PolicyModel getPolicyById(String id) throws Exception {
        try {
            Policy existingPolicy = policyRepository.getById(id);
            return PolicyMapper.policyEntityToModel(existingPolicy);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("The Entity with id: " + id + " does not exists", 404, Policy.class);
        }
    }


    /**
     * Parameters -
     * {@param policyModel}
     *
     * @return {@link PolicyModel} , the existing Policy
     * @throws ResourceAlreadyExistsException - if policy with id already exist
     */
    public PolicyModel updatePolicy(PolicyModel policyModel) throws CustomException {
        Policy existingPolicy = null;
        try {
            assert policyModel.getId() != null;
            existingPolicy = policyRepository.getById(policyModel.getId());
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("The Entity with id: " + policyModel.getId() + " does not exists", 404, Policy.class);
        }
        List<Job> jobList = jobRepository.findByJobIds(policyModel.getJobsList() != null ? policyModel.getJobsList() : new ArrayList<>());
        existingPolicy.updatePolicy(PolicyMapper.policyModelToEntity(policyModel, jobList));
        policyRepository.save(existingPolicy);
        return PolicyMapper.policyEntityToModel(existingPolicy);
    }


    /**
     * Parameters -
     * {@param id}
     *
     * @throws ResourceNotFoundException - if policy with id does not exist
     */
    public void deletePolicy(String id) throws Exception {
        try {
            policyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage(), 404, Policy.class);
        }
    }

    /**
     * Parameters -
     * {@param pageNumber}
     * {@param pageSize}
     *
     * @return {@link List<PolicyModel>} , contains list of all policies in that page
     * @throws ResourceNotFoundException - if policy with id does not exist
     */
    public List<PolicyModel> getAll(int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Policy> policyPage = policyRepository.findAll(pageable);
        if (policyPage.getContent().isEmpty())
            throw new ResourceNotFoundException("No Entity exists", 404, Policy.class);
        return policyPage.getContent().stream().map(PolicyMapper::policyEntityToModel).collect(Collectors.toList());
    }

    /**
     * Parameters -
     * {@param id} - the child job id
     *
     * @return PolicyModel , the associated Parent Policy
     * @throws ResourceNotFoundException - if policy with id does not exist
     * @throws CustomException           - if some other Exception occurs
     */
    public PolicyModel getPolicyByJobId(String jobId) throws CustomException {
        try {
            Policy existingPolicy = policyRepository.getPolicyByChildId(jobId);
            if (existingPolicy == null)
                throw new ResourceNotFoundException("No Entity exists", 404, Policy.class);
            LOGGER.info("policy with id: " + existingPolicy.getId() + " is retrieved");
            return PolicyMapper.policyEntityToModel(existingPolicy);
        } catch (Exception exception) {
            throw new CustomException(exception.getMessage(), 500);
        }
    }
}
