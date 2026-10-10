package br.com.unisinos.academia.state;

import br.com.unisinos.academia.model.StatusMatricula;

// Factory Method: devolve o objeto de estado certo a partir do status salvo no banco
public class MatriculaStateFactory {

	public static MatriculaState create(StatusMatricula status) {
		switch (status) {
			case ATIVA: return new AtivaState();
			case TRANCADA: return new TrancadaState();
			case CANCELADA: return new CanceladaState();
			default: throw new IllegalArgumentException("Status desconhecido: " + status);
		}
	}

}
