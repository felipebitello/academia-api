package br.com.unisinos.academia.pagamento;

import java.math.BigDecimal;

public interface PagamentoStrategy {
	public BigDecimal calcular(BigDecimal valor);
}
