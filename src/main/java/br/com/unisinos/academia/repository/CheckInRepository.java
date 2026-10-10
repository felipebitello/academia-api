package br.com.unisinos.academia.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unisinos.academia.model.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, UUID> {
	List<CheckIn> findByAlunoIdOrderByDataHoraDesc(UUID alunoId);
}
