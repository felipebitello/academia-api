package br.com.unisinos.academia.state;

import br.com.unisinos.academia.exception.RegraNegocioException;
import br.com.unisinos.academia.model.Matricula;

public class CanceladaState implements MatriculaState {

	private static final String MENSAGEM = "Matrícula cancelada não pode mais ser alterada";

	@Override
	public void trancar(Matricula matricula) {
		throw new RegraNegocioException(MENSAGEM);
	}

	@Override
	public void reativar(Matricula matricula) {
		throw new RegraNegocioException(MENSAGEM);
	}

	@Override
	public void cancelar(Matricula matricula) {
		throw new RegraNegocioException(MENSAGEM);
	}

}
