package com.dao;

import java.util.List;

import com.model.Routescore;

public interface RoutescoreDao {
	public void nuevo(Routescore score);
	public void editar(Routescore score);
	public void eliminar(Routescore score);
	public List<Routescore> listar();
	boolean existe(Routescore score);
	public Routescore obtener(Long id);

}
