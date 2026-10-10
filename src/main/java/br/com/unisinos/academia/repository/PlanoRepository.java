package br.com.unisinos.academia.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unisinos.academia.model.Plano;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, UUID> {
}
