package com.cliff.cliffbackend1.controllers;

import com.cliff.cliffbackend1.dto.JobModel;
import com.cliff.cliffbackend1.dto.JobModel;
import com.cliff.cliffbackend1.dto.PolicyModel;
import com.cliff.cliffbackend1.dto.ResponseModel;
import com.cliff.cliffbackend1.exceptions.CustomException;
import com.cliff.cliffbackend1.exceptions.ResourceAlreadyExistsException;
import com.cliff.cliffbackend1.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;


/**
 * This class is a controller that handles request mapped at /job
 *
 * {@link ResponseModel} with 200-OK if success
 * {@link ResponseModel} with custom error code if fails
 */
@Controller
@RequestMapping(path = "job")
public class JobController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    /**
     * Parameters -
     * {@param jobModel}
     *
     * @return {@link ResponseEntity<ResponseModel<JobModel>>} the persisted job Model ,
     * @throws ResourceAlreadyExistsException - if job with same id exists
     * @throws CustomException                - if some other exception occurs
     */
    @PostMapping(path = {"/add"})
    public ResponseEntity<ResponseModel<Object>> addJob(@Valid @RequestBody JobModel jobModel) throws CustomException {
        ResponseModel<Object> responseModel = null;
        if (jobModel == null) {
            responseModel = new ResponseModel<>("failure", "requestBody is incomplete Try Again!");
            return ResponseEntity.status(500).body(responseModel);
        }
        JobModel persistedJob = jobService.addJob(jobModel);
        responseModel = new ResponseModel<>("success", "policy is successfully added", persistedJob);
        return ResponseEntity.status(200).body(responseModel);
    }

    /**
     * Parameters -
     * {@param id} takes the id of the job as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<JobModel>>} the existing job Model ,
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if job with the id does not exists
     * @throws CustomException                                              - if some other exception occurs
     */
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<ResponseModel<Object>> getJob(@PathVariable("id") String id) throws CustomException {
        JobModel existingJob = jobService.getJob(id);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "job retrieved successfully", existingJob);
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param id} takes the id of the job as the Path Parameter
     * {@param JobModel} takes the new JobModel as the request body
     *
     * @return {@link ResponseEntity<ResponseModel<JobModel>>} the updated job Model
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if no entity with that job is found
     * @throws CustomException                                              - if some other exception occurs
     */
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResponseModel<Object>> updatePolicy(@PathVariable String id, @Valid @RequestBody JobModel jobModel) throws CustomException {
        if (jobModel.getId() == null || !jobModel.getId().equals(id))
            throw new CustomException("Path Variable id and request body id not matching", 400);
        JobModel updatedJob = jobService.updateJob(jobModel);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "job updated successfully", updatedJob);
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param id} takes the id of the job as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<String>>} the wrapper with status
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if no entity with that jobId is found
     * @throws CustomException                                              - if some other exception occurs
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ResponseModel<Object>> deletePolicy(@PathVariable String id) throws Exception {
        jobService.deleteJob(id);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "Job deleted successfully");
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param pageNumber} takes the pageNumber as Path Parameter
     * {@param pageSize} takes the pageSize as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<List<JobModel>>>} the wrapper with status , and the page view
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if no entity exists in that page
     * @throws CustomException                                              - if some other exception occurs
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ResponseModel<Object>> listAllPolicy(@PathParam("pageNumber") Integer pageNumber, @PathParam("pageSize") Integer pageSize) throws Exception {
        List<JobModel> jobModelList = jobService.getAll(pageNumber, pageSize);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "policies retrieved successfully", jobModelList);
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param id} takes the id of the parent policy as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<List<JobModel>>>} the existing list of job Model associated with that policy,
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if policy with the id does not exist
     * @throws CustomException                                              - if some other exception occurs
     */
    @GetMapping(path = "/getby")
    public ResponseEntity<ResponseModel<Object>> getJobByPolicyId(@PathParam("policyId") String policyId) throws Exception {
        ResponseModel<Object> responseModel = null;
        if (policyId == null)
            responseModel = new ResponseModel<>("failure", "requestBody is incomplete Try Again!");
        List<JobModel> existingJobList = jobService.getJobsByPolicyId(policyId);
        responseModel = new ResponseModel<>("success", "jobs retrieved successfully", existingJobList);
        return ResponseEntity.status(200).body(responseModel);
    }
}
