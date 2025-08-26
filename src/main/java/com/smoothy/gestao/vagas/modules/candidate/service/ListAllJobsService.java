package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class ListAllJobsService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute (String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
