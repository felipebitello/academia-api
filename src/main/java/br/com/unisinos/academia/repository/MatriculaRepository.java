package br.com.unisinos.academia.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unisinos.academia.model.Matricula;
import br.com.unisinos.academia.model.StatusMatricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, UUID> {
	List<Matricula> findByStatus(StatusMatricula status);
	List<Matricula> findByAlunoId(UUID alunoId);
	boolean existsByAlunoIdAndStatusIn(UUID alunoId, List<StatusMatricula> status);
	boolean existsByAlunoIdAndStatusAndDataFimGreaterThanEqual(UUID alunoId, StatusMatricula status, LocalDate data);
}
