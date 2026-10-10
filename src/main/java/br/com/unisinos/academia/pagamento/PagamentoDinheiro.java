package br.com.unisinos.academia.pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Dinheiro tem 5% de desconto
public class PagamentoDinheiro implements PagamentoStrategy {

	@Override
	public BigDecimal calcular(BigDecimal valor) {
		return valor.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
	}

}
