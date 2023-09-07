package com.desafio.associado.repositories;

import com.desafio.associado.models.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, UUID> {
    Optional<Associado> findByDocumento(String documento);
}
