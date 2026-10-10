package br.com.unisinos.academia.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisinos.academia.model.Aluno;
import br.com.unisinos.academia.model.CheckIn;
import br.com.unisinos.academia.model.Matricula;
import br.com.unisinos.academia.service.AlunoService;
import br.com.unisinos.academia.service.CheckInService;
import br.com.unisinos.academia.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Cadastro de alunos, suas matrículas e check-ins")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private MatriculaService matriculaService;

	@Autowired
	private CheckInService checkInService;

	@GetMapping
	@Operation(summary = "Listar alunos", description = "Lista os alunos em ordem alfabética, podendo filtrar por parte do nome")
	@Parameters({
		@Parameter(name = "nome", description = "Parte do nome do aluno", example = "felipe", required = false)
	})
	@ApiResponse(responseCode = "200", description = "Lista de alunos")
	public List<Aluno> getAll(@RequestParam(required = false) String nome) {
		return alunoService.getAll(nome);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar aluno por id")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Aluno encontrado"),
		@ApiResponse(responseCode = "404", description = "Aluno não encontrado")
	})
	public Aluno getOne(@PathVariable UUID id) {
		return alunoService.getOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Cadastrar aluno")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Aluno cadastrado"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF já cadastrado")
	})
	public Aluno post(@RequestBody Aluno aluno) {
		return alunoService.save(aluno);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar aluno")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Aluno atualizado"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "404", description = "Aluno não encontrado")
	})
	public Aluno put(@PathVariable UUID id, @RequestBody Aluno aluno) {
		return alunoService.update(id, aluno);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Excluir aluno")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Aluno excluído"),
		@ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
		@ApiResponse(responseCode = "409", description = "Aluno possui matrículas ou check-ins")
	})
	public void delete(@PathVariable UUID id) {
		alunoService.delete(id);
	}

	@GetMapping("/{id}/matriculas")
	@Operation(summary = "Histórico de matrículas do aluno")
	@ApiResponse(responseCode = "200", description = "Lista de matrículas do aluno")
	public List<Matricula> getMatriculas(@PathVariable UUID id) {
		return matriculaService.getByAluno(id);
	}

	@GetMapping("/{id}/check-ins")
	@Operation(summary = "Listar check-ins do aluno", description = "Do mais recente para o mais antigo")
	@ApiResponse(responseCode = "200", description = "Lista de check-ins")
	public List<CheckIn> getCheckIns(@PathVariable UUID id) {
		return checkInService.getAll(id);
	}

	@PostMapping("/{id}/check-ins")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Registrar entrada do aluno na academia", description = "Só permite se o aluno tiver matrícula ATIVA e dentro da validade")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Check-in registrado"),
		@ApiResponse(responseCode = "400", description = "Aluno sem matrícula ativa"),
		@ApiResponse(responseCode = "404", description = "Aluno não encontrado")
	})
	public CheckIn postCheckIn(@PathVariable UUID id) {
		return checkInService.save(id);
	}

}
