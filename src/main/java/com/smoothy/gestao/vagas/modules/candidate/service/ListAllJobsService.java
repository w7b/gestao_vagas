package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListAllJobsService {

    @Autowired
    private JobRepository jobRepository;

    public Page<JobEntity> execute (String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter, Pageable.unpaged());
    }

    public Page<JobEntity> getPageable (String filter, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter, pageable);
    }
}
