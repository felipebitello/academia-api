package br.com.unisinos.academia.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.unisinos.academia.state.MatriculaStateFactory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "plano_id", nullable = false)
	private Plano plano;

	private LocalDate dataInicio;
	private LocalDate dataFim;

	@Column(precision = 10, scale = 2)
	private BigDecimal valorTotal;

	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;

	@Enumerated(EnumType.STRING)
	private StatusMatricula status;

	// Pattern State: quem decide se a troca de status pode acontecer é o estado atual
	public void trancar() {
		MatriculaStateFactory.create(status).trancar(this);
	}

	public void reativar() {
		MatriculaStateFactory.create(status).reativar(this);
	}

	public void cancelar() {
		MatriculaStateFactory.create(status).cancelar(this);
	}

}
