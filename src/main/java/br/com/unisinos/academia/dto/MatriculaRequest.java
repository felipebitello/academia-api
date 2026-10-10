package br.com.unisinos.academia.dto;

import java.util.UUID;

import br.com.unisinos.academia.model.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;

public record MatriculaRequest(

		@Schema(description = "Id do aluno", example = "d506994e-2e08-4f59-b33e-00f79ae84bb7")
		UUID alunoId,

		@Schema(description = "Id do plano", example = "a1b2c3d4-0000-0000-0000-000000000000")
		UUID planoId,

		@Schema(description = "PIX (10% desconto), DINHEIRO (5% desconto) ou CARTAO_CREDITO", example = "PIX")
		FormaPagamento formaPagamento) {
}
