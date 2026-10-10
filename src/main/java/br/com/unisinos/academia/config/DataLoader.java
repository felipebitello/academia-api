package br.com.unisinos.academia.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.unisinos.academia.model.Plano;
import br.com.unisinos.academia.repository.PlanoRepository;

// Cadastra alguns planos na primeira vez que a aplicação sobe, pra facilitar os testes
@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private PlanoRepository planoRepository;

	@Override
	public void run(String... args) {
		if (planoRepository.count() > 0)
			return;

		// Builder (via Lombok @Builder)
		planoRepository.save(Plano.builder()
				.nome("Mensal")
				.descricao("Musculação livre")
				.valorMensal(new BigDecimal("119.90"))
				.duracaoMeses(1)
				.build());

		planoRepository.save(Plano.builder()
				.nome("Trimestral")
				.descricao("Musculação livre + avaliação física")
				.valorMensal(new BigDecimal("99.90"))
				.duracaoMeses(3)
				.build());

		planoRepository.save(Plano.builder()
				.nome("Anual")
				.descricao("Musculação, aulas coletivas e avaliação física")
				.valorMensal(new BigDecimal("79.90"))
				.duracaoMeses(12)
				.build());
	}

}
