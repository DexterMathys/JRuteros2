package com.dao;

import java.util.List;

import com.model.Activity;
import com.model.Route;
import com.model.UserRoute;
import com.model.UserRouteId;

public interface RouteDao {
	public void nuevo(Route route);

	public void editar(Route route);

	public void eliminar(Route route);

	public List<Route> listar();

	boolean existe(Route route);

	public List<Route> rutasActividad(Activity activity);

	public Route obtener(Long id);

	public List<Route> listar(long idUser);

	public List<Route> listarPublicas();

	public void nuevo(UserRoute user_route);

	public UserRoute obtener(UserRouteId user_route_id);

	public long countUsers(long route_id);

	public List<Route> listarSinActivity();

	public List<Route> listarSinActividad(long idUser);

}
