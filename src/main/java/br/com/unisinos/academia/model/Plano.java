package br.com.unisinos.academia.model;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plano {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private UUID id;

	@Column(nullable = false)
	@Schema(example = "Trimestral")
	private String nome;

	@Schema(example = "Musculação livre, 3 meses")
	private String descricao;

	@Column(nullable = false, precision = 10, scale = 2)
	@Schema(description = "Valor cobrado por mês", example = "99.90")
	private BigDecimal valorMensal;

	@Column(nullable = false)
	@Schema(description = "Duração do plano em meses", example = "3")
	private Integer duracaoMeses;

	public BigDecimal calcularValorBruto() {
		return valorMensal.multiply(BigDecimal.valueOf(duracaoMeses));
	}

}
