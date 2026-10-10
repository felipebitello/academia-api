package br.com.unisinos.academia.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unisinos.academia.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
	List<Aluno> findByNomeContainingIgnoreCaseOrderByNome(String nome);
	boolean existsByCpf(String cpf);
}
