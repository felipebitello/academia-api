package br.com.unisinos.academia.pagamento;

import br.com.unisinos.academia.model.FormaPagamento;

public class PagamentoFactory {

	public static PagamentoStrategy create(FormaPagamento formaPagamento) {
		switch (formaPagamento) {
			case PIX: return new PagamentoPix();
			case DINHEIRO: return new PagamentoDinheiro();
			case CARTAO_CREDITO: return new PagamentoCartao();
			default: throw new IllegalArgumentException("Forma de pagamento inválida: " + formaPagamento);
		}
	}

}
