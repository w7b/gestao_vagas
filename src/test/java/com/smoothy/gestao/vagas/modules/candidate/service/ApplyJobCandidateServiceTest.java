package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.exceptions.JobNotFoundException;
import com.smoothy.gestao.vagas.exceptions.UserNotFoundException;
import com.smoothy.gestao.vagas.modules.candidate.entity.ApplyJobEntity;
import com.smoothy.gestao.vagas.modules.candidate.entity.CandidateEntity;
import com.smoothy.gestao.vagas.modules.candidate.repository.ApplyJobRepository;
import com.smoothy.gestao.vagas.modules.candidate.repository.CandidateRepository;
import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateServiceTest {

    @InjectMocks
    private ApplyJobCandidateService applyJobCandidateService;

    @Mock
    private JobRepository jobRepository;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try{
            applyJobCandidateService.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }

    }


    @Test
    public void should_be_able_to_apply_job_with_job_not_found() {
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        try {
            applyJobCandidateService.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    public void shoud_be_able_to_create_new_apply_job() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();


        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateService.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
