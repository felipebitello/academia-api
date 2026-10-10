package br.com.unisinos.academia.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErroResponse> handleStatus(ResponseStatusException e) {
		return ResponseEntity.status(e.getStatusCode())
				.body(new ErroResponse(e.getStatusCode().value(), e.getReason()));
	}

	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<ErroResponse> handleRegraNegocio(RegraNegocioException e) {
		return ResponseEntity.badRequest()
				.body(new ErroResponse(400, e.getMessage()));
	}

	// ex.: tentar excluir um aluno que já tem matrícula ou check-in
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErroResponse> handleIntegridade(DataIntegrityViolationException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErroResponse(409, "Registro está vinculado a outros dados e não pode ser removido/alterado"));
	}

}
