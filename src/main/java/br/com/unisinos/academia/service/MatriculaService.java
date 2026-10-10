package br.com.unisinos.academia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.unisinos.academia.dto.MatriculaRequest;
import br.com.unisinos.academia.exception.RegraNegocioException;
import br.com.unisinos.academia.model.Aluno;
import br.com.unisinos.academia.model.Matricula;
import br.com.unisinos.academia.model.Plano;
import br.com.unisinos.academia.model.StatusMatricula;
import br.com.unisinos.academia.pagamento.PagamentoFactory;
import br.com.unisinos.academia.pagamento.PagamentoStrategy;
import br.com.unisinos.academia.repository.MatriculaRepository;

@Service
public class MatriculaService {

	@Autowired
	private MatriculaRepository matriculaRepository;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private PlanoService planoService;

	public List<Matricula> getAll(StatusMatricula status) {
		if (status != null)
			return matriculaRepository.findByStatus(status);
		return matriculaRepository.findAll(Sort.by(Sort.Direction.DESC, "dataInicio"));
	}

	public Matricula getOne(UUID id) {
		return matriculaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
	}

	public List<Matricula> getByAluno(UUID alunoId) {
		alunoService.getOne(alunoId);
		return matriculaRepository.findByAlunoId(alunoId);
	}

	public Matricula save(MatriculaRequest request) {
		if (request.alunoId() == null || request.planoId() == null || request.formaPagamento() == null)
			throw new RegraNegocioException("alunoId, planoId e formaPagamento são obrigatórios");

		Aluno aluno = alunoService.getOne(request.alunoId());
		Plano plano = planoService.getOne(request.planoId());

		// um aluno só pode ter uma matrícula "em aberto" por vez
		boolean jaTemMatricula = matriculaRepository.existsByAlunoIdAndStatusIn(aluno.getId(),
				List.of(StatusMatricula.ATIVA, StatusMatricula.TRANCADA));
		if (jaTemMatricula)
			throw new RegraNegocioException("Aluno já possui uma matrícula ativa ou trancada");

		// Pattern Strategy: o cálculo muda conforme a forma de pagamento
		PagamentoStrategy pagamento = PagamentoFactory.create(request.formaPagamento());

		Matricula matricula = new Matricula();
		matricula.setAluno(aluno);
		matricula.setPlano(plano);
		matricula.setFormaPagamento(request.formaPagamento());
		matricula.setValorTotal(pagamento.calcular(plano.calcularValorBruto()));
		matricula.setDataInicio(LocalDate.now());
		matricula.setDataFim(LocalDate.now().plusMonths(plano.getDuracaoMeses()));
		matricula.setStatus(StatusMatricula.ATIVA);
		return matriculaRepository.save(matricula);
	}

	public Matricula alterarStatus(UUID id, String action) {
		Matricula matricula = getOne(id);
		switch (action == null ? "" : action.toLowerCase()) {
			case "trancar": matricula.trancar(); break;
			case "reativar": matricula.reativar(); break;
			case "cancelar": matricula.cancelar(); break;
			default: throw new RegraNegocioException("Ação inválida. Use: trancar, reativar ou cancelar");
		}
		return matriculaRepository.save(matricula);
	}

}
