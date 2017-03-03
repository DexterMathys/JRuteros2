package com.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.ActivityDao;
import com.model.Activity;
import com.model.Route;
import com.util.HibernateUtil;

public class ActivityDaoImp implements ActivityDao{
	
	private Session s;

	@Override
	public void nuevo(Activity activity) {
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(activity);
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
	public void editar(Activity activity) {
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(activity);
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
	public void eliminar(Activity activity) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(activity);
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
	public List<Activity> listar() {
		// TODO Auto-generated method stub
		List<Activity> activities = null;
		
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Activity";
		try {
			activities = s.createQuery(hql).list();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return activities;
	}

	@Override
	public boolean existe(Activity activity) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Activity where name = :aname ";
		try {
			ex = (s.createQuery(hql).setString("aname",activity.getName()).uniqueResult() != null);
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return ex;
	}

	@Override
	public List<Route> rutas(Activity activity) {
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
	public List<Activity> listarHabilitadas() {
		// TODO Auto-generated method stub
		List<Activity> activities = null;
		
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Activity WHERE active = true";
		try {
			activities = s.createQuery(hql).list();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return activities;
	}


}
