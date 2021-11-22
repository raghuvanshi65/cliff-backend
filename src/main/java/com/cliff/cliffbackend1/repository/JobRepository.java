package com.cliff.cliffbackend1.repository;

import com.cliff.cliffbackend1.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    @Query("select j from Job j where j.id in :ids")
    List<Job> findByJobIds(@Param("ids") List<String> jobIds);

    @Query("select j from Job j where j.policy.id = :id")
    List<Job> getJobByParentId(@Param("id") String id);
}
