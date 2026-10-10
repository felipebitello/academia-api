package br.com.unisinos.academia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.unisinos.academia.exception.RegraNegocioException;
import br.com.unisinos.academia.model.Aluno;
import br.com.unisinos.academia.repository.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;

	public List<Aluno> getAll(String nome) {
		if (nome != null)
			return alunoRepository.findByNomeContainingIgnoreCaseOrderByNome(nome);
		return alunoRepository.findAll(Sort.by("nome"));
	}

	public Aluno getOne(UUID id) {
		return alunoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
	}

	public Aluno save(Aluno aluno) {
		validar(aluno);
		if (alunoRepository.existsByCpf(aluno.getCpf()))
			throw new RegraNegocioException("Já existe um aluno com esse CPF");
		aluno.setId(null);
		return alunoRepository.save(aluno);
	}

	public Aluno update(UUID id, Aluno dados) {
		validar(dados);
		Aluno aluno = getOne(id);
		if (!aluno.getCpf().equals(dados.getCpf()) && alunoRepository.existsByCpf(dados.getCpf()))
			throw new RegraNegocioException("Já existe um aluno com esse CPF");

		aluno.setNome(dados.getNome());
		aluno.setCpf(dados.getCpf());
		aluno.setEmail(dados.getEmail());
		aluno.setTelefone(dados.getTelefone());
		aluno.setDataNascimento(dados.getDataNascimento());
		return alunoRepository.save(aluno);
	}

	public void delete(UUID id) {
		alunoRepository.delete(getOne(id));
	}

	private void validar(Aluno aluno) {
		if (aluno.getNome() == null || aluno.getNome().isBlank())
			throw new RegraNegocioException("Nome é obrigatório");
		if (aluno.getCpf() == null || !aluno.getCpf().matches("\\d{11}"))
			throw new RegraNegocioException("CPF deve ter 11 números");
	}

}
