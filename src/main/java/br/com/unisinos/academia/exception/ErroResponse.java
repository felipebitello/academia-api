package br.com.unisinos.academia.exception;

import java.time.LocalDateTime;

public record ErroResponse(int status, String mensagem, LocalDateTime dataHora) {

	public ErroResponse(int status, String mensagem) {
		this(status, mensagem, LocalDateTime.now());
	}

}
