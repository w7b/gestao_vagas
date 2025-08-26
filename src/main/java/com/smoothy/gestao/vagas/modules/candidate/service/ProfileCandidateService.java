package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.exceptions.UserFoundException;
import com.smoothy.gestao.vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.smoothy.gestao.vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserFoundException();
                });

        var candidateDTO = ProfileCandidateResponseDTO.builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .name(candidate.getName())
        .email(candidate.getEmail())
        .id(candidate.getId())
        .build();

        return candidateDTO;
    }

}
