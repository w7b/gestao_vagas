package com.smoothy.gestao.vagas.modules.candidate.service;

import com.smoothy.gestao.vagas.exceptions.UserFoundException;
import com.smoothy.gestao.vagas.modules.candidate.entity.CandidateEntity;
import com.smoothy.gestao.vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CandidateRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CandidateService(PasswordEncoder passwordEncoder, CandidateRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public CandidateEntity execute (CandidateEntity candidateEntity) {
        this.repository.findByUsernameOrEmail
                        (candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.repository.save(candidateEntity);
    }
}
