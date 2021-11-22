package com.cliff.cliffbackend1.controllers;

import com.cliff.cliffbackend1.dto.PolicyModel;
import com.cliff.cliffbackend1.exceptions.CustomException;
import com.cliff.cliffbackend1.exceptions.ResourceAlreadyExistsException;
import com.cliff.cliffbackend1.service.PolicyService;
import com.cliff.cliffbackend1.dto.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;


/**
 * This class is a controller that handles request mapped at /policy
 *
 * {@link ResponseModel} with 200-OK if success
 * {@link ResponseModel} with custom error code if fails
 */
@Controller
@RequestMapping(path = "/policy")
public class PolicyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyController.class);

    @Autowired
    private PolicyService policyService;

    /**
     * Parameters -
     * {@param policyModel}
     *
     * @return {@link ResponseEntity<ResponseModel<PolicyModel>>} the persisted Policy Model ,
     * @throws ResourceAlreadyExistsException - if policy with same id exists
     * @throws CustomException                - if some other exception occurs
     */
    @PostMapping(path = "/add")
    @Transactional
    public ResponseEntity<ResponseModel<Object>> addPolicy(@RequestBody @Valid PolicyModel policyModel) throws Exception {
        ResponseModel<Object> responseModel;
        if (policyModel == null) {
            responseModel = new ResponseModel<>("failure", "requestBody is incomplete Try Again!");
            return ResponseEntity.status(500).body(responseModel);
        }
        PolicyModel persistedPolicy = policyService.addPolicy(policyModel);
        responseModel = new ResponseModel<>("success", "policy is successfully added", persistedPolicy);
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param id} takes the id of the policy as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<PolicyModel>>} the existing policy Model ,
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if policy with the id does not exist
     * @throws CustomException                                              - if some other exception occurs
     */
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<ResponseModel<Object>> getPolicy(@PathVariable String id) throws Exception {
        PolicyModel existingPolicy = policyService.getPolicyById(id);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "policy retrieved successfully", existingPolicy);
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param id} takes the id of the job as the Path Parameter
     * {@param PolicyModel} takes the new policyModel as the request body
     *
     * @return {@link ResponseEntity<ResponseModel<PolicyModel>>} the updated policyModel
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if no entity with that id is found
     * @throws CustomException                                              - if some other exception occurs
     */
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResponseModel<Object>> updatePolicy(@PathVariable String id, @Valid @RequestBody PolicyModel policyModel) throws CustomException {
        if (policyModel.getId()==null||!policyModel.getId().equals(id))
            throw new CustomException("Path Variable id and request body id not matching", 400);
        PolicyModel updatedPolicy = policyService.updatePolicy(policyModel);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "policy updated successfully", updatedPolicy);
        return ResponseEntity.status(200).body(responseModel);
    }

    /**
     * Parameters -
     * {@param id} takes the id of the policy as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<String>>} the wrapper with status
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if no entity with that policyId is found
     * @throws CustomException                                              - if some other exception occurs
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ResponseModel<Object>> deletePolicy(@PathVariable String id) throws Exception {
        policyService.deletePolicy(id);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "policy deleted successfully");
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param pageNumber} takes the pageNumber as Path Parameter
     * {@param pageSize} takes the pageSize as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<List<PolicyModel>>>} the wrapper with status , and the page view
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if no entity exists in that page
     * @throws CustomException                                              - if some other exception occurs
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ResponseModel<Object>> listAllPolicy(@PathParam("pageNumber") Integer pageNumber, @PathParam("pageSize") Integer pageSize) throws Exception {
        List<PolicyModel> policyList = policyService.getAll(pageNumber,pageSize);
        ResponseModel<Object> responseModel = new ResponseModel<>("success", "policies retrieved successfully" , policyList);
        return ResponseEntity.status(200).body(responseModel);
    }


    /**
     * Parameters -
     * {@param id} takes the id for any of the child jobs as the Path Parameter
     *
     * @return {@link ResponseEntity<ResponseModel<PolicyModel>>} the existing policyModel associated with that job,
     * @throws com.cliff.cliffbackend1.exceptions.ResourceNotFoundException - if job with the id does not exist
     * @throws CustomException                                              - if some other exception occurs
     */
    @GetMapping(path = "/get/by")
    public ResponseEntity<ResponseModel<Object>> getJobByPolicyId(@PathParam("jobId") String jobId) throws Exception {
        ResponseModel<Object> responseModel = null;
        if(jobId==null)
            responseModel = new ResponseModel<>("failure", "requestBody is incomplete Try Again!");
        PolicyModel existingPolicy = policyService.getPolicyByJobId(jobId);
        System.out.println(existingPolicy.toString());
        responseModel = new ResponseModel<>("success", "policies retrieved successfully" , existingPolicy);
        return ResponseEntity.status(200).body(responseModel);
    }
}
