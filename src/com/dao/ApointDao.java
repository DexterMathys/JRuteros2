package com.dao;

import java.util.List;

import com.model.Apoint;
import com.model.Travel;

public interface ApointDao {
	public void nuevo(Apoint point);
	public void editar(Apoint point);
	public void eliminar(Apoint point);
	public void borrarTodosPorTravel(Travel travel);
	public List<Apoint> listar();
	boolean existe(Apoint point);
	public Apoint obtener(Long id);
	public List<Apoint> recuperarTodos(Travel travel);
	

}
