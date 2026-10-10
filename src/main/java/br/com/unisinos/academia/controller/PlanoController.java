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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisinos.academia.model.Plano;
import br.com.unisinos.academia.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/planos")
@Tag(name = "Planos", description = "Planos oferecidos pela academia")
public class PlanoController {

	@Autowired
	private PlanoService planoService;

	@GetMapping
	@Operation(summary = "Listar planos")
	@ApiResponse(responseCode = "200", description = "Lista de planos")
	public List<Plano> getAll() {
		return planoService.getAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar plano por id")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Plano encontrado"),
		@ApiResponse(responseCode = "404", description = "Plano não encontrado")
	})
	public Plano getOne(@PathVariable UUID id) {
		return planoService.getOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Cadastrar plano")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Plano cadastrado"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos")
	})
	public Plano post(@RequestBody Plano plano) {
		return planoService.save(plano);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar plano")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Plano atualizado"),
		@ApiResponse(responseCode = "404", description = "Plano não encontrado")
	})
	public Plano put(@PathVariable UUID id, @RequestBody Plano plano) {
		return planoService.update(id, plano);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Excluir plano")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Plano excluído"),
		@ApiResponse(responseCode = "409", description = "Plano já usado em matrículas")
	})
	public void delete(@PathVariable UUID id) {
		planoService.delete(id);
	}

}
