package com.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.RoutescoreDao;
import com.model.Routescore;
import com.util.HibernateUtil;

public class RoutescoreDaoImp implements RoutescoreDao{
	
	private Session s;

	@Override
	public List<Routescore> listar() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Routescore> scores = null;
		
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Routescore";
		try {
			scores = s.createQuery(hql).list();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return scores;
	}

	@Override
	public void nuevo(Routescore score) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(score);
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
	public void editar(Routescore score) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(score);
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
	public void eliminar(Routescore score) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(score);
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
	public boolean existe(Routescore score) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Routescore where id = :id ";
		try {
			ex = (s.createQuery(hql).setString("id",score.getId().toString()).uniqueResult() != null);
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		}
		return ex;
	}

	@Override
	public Routescore obtener(Long id) {
		// TODO Auto-generated method stub
		Routescore score;
		s = HibernateUtil.sessionFactory.openSession();
		score  = (Routescore) s.get(Routescore.class, id);
		return score;
	}


}
