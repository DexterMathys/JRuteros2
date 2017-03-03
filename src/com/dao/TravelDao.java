package com.dao;

import java.util.List;

import com.model.Travel;

public interface TravelDao {
	public void nuevo(Travel travel);
	public void editar(Travel travel);
	public void eliminar(Travel travel);
	public List<Travel> listar();
	boolean existe(Travel travel);
	public Travel obtener(Long id);

}
