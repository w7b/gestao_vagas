package com.smoothy.gestao.vagas.modules.company.services;

import com.smoothy.gestao.vagas.exceptions.UserFoundException;
import com.smoothy.gestao.vagas.modules.company.entity.CompanyEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(CompanyRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.repository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.repository.save(companyEntity);
    }

}
