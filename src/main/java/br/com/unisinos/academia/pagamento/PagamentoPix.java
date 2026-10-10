package br.com.unisinos.academia.pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

// PIX tem 10% de desconto
public class PagamentoPix implements PagamentoStrategy {

	@Override
	public BigDecimal calcular(BigDecimal valor) {
		return valor.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
	}

}
