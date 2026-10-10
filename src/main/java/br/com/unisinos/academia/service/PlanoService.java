package br.com.unisinos.academia.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.unisinos.academia.exception.RegraNegocioException;
import br.com.unisinos.academia.model.Plano;
import br.com.unisinos.academia.repository.PlanoRepository;

@Service
public class PlanoService {

	@Autowired
	private PlanoRepository planoRepository;

	public List<Plano> getAll() {
		return planoRepository.findAll(Sort.by("duracaoMeses"));
	}

	public Plano getOne(UUID id) {
		return planoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
	}

	public Plano save(Plano plano) {
		validar(plano);
		plano.setId(null);
		return planoRepository.save(plano);
	}

	public Plano update(UUID id, Plano dados) {
		validar(dados);
		Plano plano = getOne(id);
		plano.setNome(dados.getNome());
		plano.setDescricao(dados.getDescricao());
		plano.setValorMensal(dados.getValorMensal());
		plano.setDuracaoMeses(dados.getDuracaoMeses());
		return planoRepository.save(plano);
	}

	public void delete(UUID id) {
		planoRepository.delete(getOne(id));
	}

	private void validar(Plano plano) {
		if (plano.getNome() == null || plano.getNome().isBlank())
			throw new RegraNegocioException("Nome do plano é obrigatório");
		if (plano.getValorMensal() == null || plano.getValorMensal().compareTo(BigDecimal.ZERO) <= 0)
			throw new RegraNegocioException("Valor mensal deve ser maior que zero");
		if (plano.getDuracaoMeses() == null || plano.getDuracaoMeses() < 1)
			throw new RegraNegocioException("Duração deve ser de pelo menos 1 mês");
	}

}
