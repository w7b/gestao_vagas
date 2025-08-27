package com.smoothy.gestao.vagas.modules.company.repositories;

import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    Page<JobEntity> findByDescriptionContainingIgnoreCase (String description, Pageable pageable);

}
