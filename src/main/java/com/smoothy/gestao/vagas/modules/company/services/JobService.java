package com.smoothy.gestao.vagas.modules.company.services;

import com.smoothy.gestao.vagas.exceptions.CompanyNotFoundException;
import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.CompanyRepository;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(
                CompanyNotFoundException::new);
        return this.jobRepository.save(jobEntity);
    }

}
