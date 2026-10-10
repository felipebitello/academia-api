package br.com.unisinos.academia.model;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private UUID id;

	@Column(nullable = false)
	@Schema(description = "Nome completo do aluno", example = "Felipe Bitello")
	private String nome;

	@Column(nullable = false, unique = true, length = 11)
	@Schema(description = "CPF somente números", example = "12345678900")
	private String cpf;

	@Schema(example = "felipe@email.com")
	private String email;

	@Schema(example = "51999998888")
	private String telefone;

	@Schema(example = "2005-03-15")
	private LocalDate dataNascimento;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private LocalDate dataCadastro;

	@PrePersist
	public void prePersist() {
		dataCadastro = LocalDate.now();
	}

}
