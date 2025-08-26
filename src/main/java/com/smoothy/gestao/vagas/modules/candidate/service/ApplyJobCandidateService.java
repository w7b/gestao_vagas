package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.exceptions.JobNotFoundException;
import com.smoothy.gestao.vagas.exceptions.UserNotFoundException;
import com.smoothy.gestao.vagas.modules.candidate.entity.ApplyJobEntity;
import com.smoothy.gestao.vagas.modules.candidate.repository.ApplyJobRepository;
import com.smoothy.gestao.vagas.modules.candidate.repository.CandidateRepository;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateService {


    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;


    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        //Validar se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        //Validar se a vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });
        //Candidado se inscrever na vaga
        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob).build();
        applyJob = this.applyJobRepository.save(applyJob);
        return applyJob;
    }
}
