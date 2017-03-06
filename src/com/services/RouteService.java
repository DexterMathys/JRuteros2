package com.services;

import java.util.ArrayList;
import java.util.List;

import com.imp.ApointDaoImp;
import com.imp.RouteDaoImp;
import com.imp.TravelDaoImp;
import com.model.Apoint;
import com.model.Route;
import com.model.Travel;

public class RouteService {
	ApointDaoImp pointDAO;
	RouteDaoImp routeDAO;
	TravelDaoImp travelDAO;
	Route route ;
	
	public RouteService() {
		super();
		pointDAO = new ApointDaoImp();
		routeDAO = new RouteDaoImp();
		travelDAO = new TravelDaoImp();
	}
	
	public List<Apoint> getApoints(Long idRoute) {
		// TODO Auto-generated method stub
		route = routeDAO.obtener(idRoute);
		//return route.getTravel().getPointsList();
		List<Apoint> pointList = new ArrayList<Apoint>();
		pointList.addAll(pointDAO.recuperarTodos(route.getTravel()));
		return pointList;
		//return pointDAO.recuperarTodos(route.getTravel().getId());
	}

	public Apoint getApoint(String id) {
		// TODO Auto-generated method stub
		return pointDAO.obtener(new Long(id));
	}
	
	public void createApoint(Apoint point) {
		// TODO Auto-generated method stub
		pointDAO.nuevo(point);
		
	}
	
	public Travel getTravel(Long id) {
		// TODO Auto-generated method stub
		return routeDAO.obtener(new Long(id)).getTravel();
	}
	
	public void deleteApoint(Long id) {
		// TODO Auto-generated method stub
		Apoint point = pointDAO.obtener(new Long(id));
		pointDAO.eliminar(point);
		
	}

	public void deleteAllPoints(Long id) {
		// TODO Auto-generated method stub
		Route route = routeDAO.obtener(new Long(id));
		pointDAO.borrarTodosPorTravel(route.getTravel());
	}
	
}
