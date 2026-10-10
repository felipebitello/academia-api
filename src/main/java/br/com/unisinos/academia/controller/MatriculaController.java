package br.com.unisinos.academia.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisinos.academia.dto.MatriculaRequest;
import br.com.unisinos.academia.model.Matricula;
import br.com.unisinos.academia.model.StatusMatricula;
import br.com.unisinos.academia.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/matriculas")
@Tag(name = "Matrículas", description = "Matrícula de alunos em planos e controle de status")
public class MatriculaController {

	@Autowired
	private MatriculaService matriculaService;

	@GetMapping
	@Operation(summary = "Listar matrículas", description = "Lista todas as matrículas, podendo filtrar por status")
	@Parameters({
		@Parameter(name = "status", description = "ATIVA, TRANCADA ou CANCELADA", example = "ATIVA", required = false)
	})
	@ApiResponse(responseCode = "200", description = "Lista de matrículas")
	public List<Matricula> getAll(@RequestParam(required = false) StatusMatricula status) {
		return matriculaService.getAll(status);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar matrícula por id")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Matrícula encontrada"),
		@ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
	})
	public Matricula getOne(@PathVariable UUID id) {
		return matriculaService.getOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Matricular aluno em um plano", description = "Calcula o valor total conforme a forma de pagamento (PIX -10%, DINHEIRO -5%, CARTAO_CREDITO valor cheio)")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Matrícula criada"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos ou aluno já matriculado"),
		@ApiResponse(responseCode = "404", description = "Aluno ou plano não encontrado")
	})
	public Matricula post(@RequestBody MatriculaRequest request) {
		return matriculaService.save(request);
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Alterar status da matrícula", description = "Trancar, reativar ou cancelar. Matrícula cancelada não pode mais ser alterada.")
	@Parameters({
		@Parameter(name = "action", description = "trancar, reativar ou cancelar", example = "trancar", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Status alterado"),
		@ApiResponse(responseCode = "400", description = "Transição de status não permitida"),
		@ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
	})
	public Matricula patch(@PathVariable UUID id, @RequestParam String action) {
		return matriculaService.alterarStatus(id, action);
	}

}
