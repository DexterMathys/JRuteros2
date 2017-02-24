package com.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.RouteDao;
import com.model.Activity;
import com.model.Route;
import com.util.HibernateUtil;

public class RouteDaoImp implements RouteDao{
	
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
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
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
		}finally {
			if (s != null) {
				s.close();
			}
		}
		
	}

	@Override
	public void editar(Route route) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(route);
			s.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
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
		}finally {
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
			ex = (s.createQuery(hql).setString("id",route.getId().toString()).uniqueResult() != null);
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return ex;
	}

	@Override
	public List<Route> listar() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Route> rutas = null;
		
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Route";
		try {
			rutas = s.createQuery(hql).list();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return rutas;
	}


}