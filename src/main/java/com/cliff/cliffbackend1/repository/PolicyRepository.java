package com.cliff.cliffbackend1.repository;

import com.cliff.cliffbackend1.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {

    @Query("select p from Policy p inner join Job j on p.id = j.policy.id where j.id= :jobId")
    Policy getPolicyByChildId(@Param("jobId") String jobId);
}
