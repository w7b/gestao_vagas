package com.smoothy.gestao.vagas.modules.company.services;

import com.smoothy.gestao.vagas.exceptions.CompanyNotFoundException;
import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.CompanyRepository;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(
                () -> new CompanyNotFoundException());
        return this.jobRepository.save(jobEntity);
    }

}
