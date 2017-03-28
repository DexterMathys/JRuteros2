package com.imp;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.RouteDao;
import com.model.Activity;
import com.model.Route;
import com.model.UserRoute;
import com.model.UserRouteId;
import com.util.HibernateUtil;

public class RouteDaoImp implements RouteDao {

	private Session s;

	@Override
	public List<Route> rutasActividad(Activity activity) {
		// TODO Auto-generated method stub
		List<Route> rutas = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Route where activity_id = :id";
		try {
			rutas = s.createQuery(hql).setString("id", activity.getId().toString()).list();
			t.commit();
			//s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return rutas;
	}

	@Override
	public void nuevo(Route route) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(route);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}

	}

	@Override
	public void editar(Route route) {
		// TODO Auto-generated method stub
		try {
			if (s != null) {
				s.close();
				s = null;
			}
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(route);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}

	}

	@Override
	public void eliminar(Route route) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(route);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}

	}

	@Override
	public boolean existe(Route route) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Route where id = :id ";
		try {
			ex = (s.createQuery(hql).setString("id", route.getId().toString()).uniqueResult() != null);
			t.commit();
			//s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return ex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> listar() {
		List<Route> rutas = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Route r INNER JOIN r.activity";
		try {
			rutas = s.createQuery(hql).list();
			t.commit();
			//s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return rutas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> listarPublicas() {
		List<Route> rutas = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Route r INNER JOIN r.activity WHERE isPublic = 1";
		try {
			rutas = s.createQuery(hql).list();
			t.commit();
			//s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return rutas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Route> listar(long idUser) {
		List<Route> rutas = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Route r INNER JOIN r.activity WHERE user_id = " + idUser;
		try {
			rutas = s.createQuery(hql).list();
			t.commit();
			//s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return rutas;
	}

	@Override
	public Route obtener(Long id) {
		// TODO Auto-generated method stub
		Route route = null;
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			route = (Route) s.get(Route.class, id);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return route;
		
		
	}

	@Override
	public void nuevo(UserRoute user_route) {
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(user_route);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public UserRoute obtener(UserRouteId user_route_id) {
		UserRoute user_route = null;
		s = HibernateUtil.sessionFactory.openSession();
		user_route = (UserRoute) s.get(UserRoute.class, user_route_id);
		return user_route;
	}

	@Override
	public long countUsers(long route_id) {
		long cant = 0;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "SELECT COUNT(*) FROM UserRoute WHERE traveledRoutes_id = " + route_id;
		try {
			cant = (long) s.createQuery(hql).uniqueResult();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return cant;

	}

}
