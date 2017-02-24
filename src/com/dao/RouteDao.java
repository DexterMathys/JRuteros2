package com.dao;

import java.util.List;

import com.model.Activity;
import com.model.Route;

public interface RouteDao {
	public void nuevo(Route route);
	public void editar(Route route);
	public void eliminar(Route route);
	public List<Route> listar();
	boolean existe(Route route);
	public List<Route> rutasActividad(Activity activity);

}
