package com.dao;

import java.util.List;

import com.model.Activity;

public interface ActivityDao {
	public void nuevo(Activity activity);
	public void editar(Activity activity);
	public void eliminar(Activity activity);
	public List<Activity> listar();
	boolean existe(Activity activity);

}
