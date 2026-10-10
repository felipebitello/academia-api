package br.com.unisinos.academia.pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Cartão paga o valor cheio
public class PagamentoCartao implements PagamentoStrategy {

	@Override
	public BigDecimal calcular(BigDecimal valor) {
		return valor.setScale(2, RoundingMode.HALF_UP);
	}

}
