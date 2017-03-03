package com.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.TravelDao;
import com.model.Route;
import com.model.Travel;
import com.util.HibernateUtil;

public class TravelDaoImp implements TravelDao{
	
	private Session s;

	@Override
	public List<Travel> listar() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Travel> travels = null;
		
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Travel";
		try {
			travels = s.createQuery(hql).list();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return travels;
	}

	@Override
	public void nuevo(Travel travel) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(travel);
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
	public void editar(Travel travel) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(travel);
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
	public void eliminar(Travel travel) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(travel);
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
	public boolean existe(Travel travel) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Travel where id = :id ";
		try {
			ex = (s.createQuery(hql).setString("id",travel.getId().toString()).uniqueResult() != null);
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return ex;
	}

	@Override
	public Travel obtener(Long id) {
		// TODO Auto-generated method stub
		Travel travel;
		s = HibernateUtil.sessionFactory.openSession();
		travel  = (Travel) s.get(Travel.class, id);
		return travel;
	}


}
