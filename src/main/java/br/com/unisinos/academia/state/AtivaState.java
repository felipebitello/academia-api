package br.com.unisinos.academia.state;

import br.com.unisinos.academia.exception.RegraNegocioException;
import br.com.unisinos.academia.model.Matricula;
import br.com.unisinos.academia.model.StatusMatricula;

public class AtivaState implements MatriculaState {

	@Override
	public void trancar(Matricula matricula) {
		matricula.setStatus(StatusMatricula.TRANCADA);
	}

	@Override
	public void reativar(Matricula matricula) {
		throw new RegraNegocioException("Matrícula já está ativa");
	}

	@Override
	public void cancelar(Matricula matricula) {
		matricula.setStatus(StatusMatricula.CANCELADA);
	}

}
