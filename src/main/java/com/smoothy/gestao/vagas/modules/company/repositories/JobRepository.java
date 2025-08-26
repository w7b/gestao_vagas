package com.smoothy.gestao.vagas.modules.company.repositories;

import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findByDescriptionContainingIgnoreCase (String description);

}
