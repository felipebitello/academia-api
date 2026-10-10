package br.com.unisinos.academia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unisinos.academia.exception.RegraNegocioException;
import br.com.unisinos.academia.model.Aluno;
import br.com.unisinos.academia.model.CheckIn;
import br.com.unisinos.academia.model.StatusMatricula;
import br.com.unisinos.academia.repository.CheckInRepository;
import br.com.unisinos.academia.repository.MatriculaRepository;

@Service
public class CheckInService {

	@Autowired
	private CheckInRepository checkInRepository;

	@Autowired
	private MatriculaRepository matriculaRepository;

	@Autowired
	private AlunoService alunoService;

	public List<CheckIn> getAll(UUID alunoId) {
		alunoService.getOne(alunoId);
		return checkInRepository.findByAlunoIdOrderByDataHoraDesc(alunoId);
	}

	public CheckIn save(UUID alunoId) {
		Aluno aluno = alunoService.getOne(alunoId);

		boolean matriculaValida = matriculaRepository.existsByAlunoIdAndStatusAndDataFimGreaterThanEqual(
				alunoId, StatusMatricula.ATIVA, LocalDate.now());
		if (!matriculaValida)
			throw new RegraNegocioException("Aluno não possui matrícula ativa. Entrada bloqueada.");

		return checkInRepository.save(new CheckIn(aluno));
	}

}
