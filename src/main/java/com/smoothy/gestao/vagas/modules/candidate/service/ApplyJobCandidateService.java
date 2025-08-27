package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.exceptions.JobNotFoundException;
import com.smoothy.gestao.vagas.exceptions.UserNotFoundException;
import com.smoothy.gestao.vagas.modules.candidate.entity.ApplyJobEntity;
import com.smoothy.gestao.vagas.modules.candidate.repository.ApplyJobRepository;
import com.smoothy.gestao.vagas.modules.candidate.repository.CandidateRepository;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateService {


    private final CandidateRepository candidateRepository;

    private final JobRepository jobRepository;

    private final ApplyJobRepository applyJobRepository;

    public ApplyJobCandidateService(CandidateRepository candidateRepository, JobRepository jobRepository, ApplyJobRepository applyJobRepository) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.applyJobRepository = applyJobRepository;
    }


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
