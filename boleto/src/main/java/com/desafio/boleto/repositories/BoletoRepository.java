package com.desafio.boleto.repositories;

import com.desafio.boleto.models.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, String> {
    Optional<Boleto> findByUuidAssociado(UUID uuidAssociado);
    @Query(value = "SELECT b FROM Boleto b WHERE b.id = ?1 AND b.valor = ?2 AND b.documentoPagador = ?3")
    Optional<Boleto> findByPagamentoBoleto(String id, Float valor, String documentoPagador);
}
