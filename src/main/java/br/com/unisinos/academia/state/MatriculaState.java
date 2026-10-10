package br.com.unisinos.academia.state;

import br.com.unisinos.academia.model.Matricula;

public interface MatriculaState {
	public void trancar(Matricula matricula);
	public void reativar(Matricula matricula);
	public void cancelar(Matricula matricula);
}
