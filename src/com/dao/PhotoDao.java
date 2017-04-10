package com.dao;

import java.util.List;

import com.model.Photo;

public interface PhotoDao {
	public void nuevo(Photo photo);

	public void editar(Photo photo);

	public void eliminar(Photo photo);

	public List<Photo> listar();

	boolean existe(Photo photo);

	public Photo obtener(Long id);

	public List<Photo> listar(long route_id);

}
