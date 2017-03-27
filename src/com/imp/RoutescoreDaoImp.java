package com.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.RoutescoreDao;
import com.model.Routescore;
import com.util.HibernateUtil;

public class RoutescoreDaoImp implements RoutescoreDao {

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
			//s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
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
		} finally {
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
		} finally {
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
		} finally {
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
			ex = (s.createQuery(hql).setString("id", score.getId().toString()).uniqueResult() != null);
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

	@Override
	public boolean existe(long idRoute, long idUser) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Routescore WHERE route_id = :idRoute AND user_id = :idUser";
		try {
			Query q = s.createQuery(hql);
			q.setParameter("idRoute", idRoute);
			q.setParameter("idUser", idUser);
			ex = (q.uniqueResult() != null);
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

	@Override
	public List<Routescore> listar(long idRoute) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Routescore> scores = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Routescore WHERE route_id = :idRoute";
		try {
			Query q = s.createQuery(hql);
			q.setParameter("idRoute", idRoute);
			scores = q.list();
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
		return scores;
	}

	@Override
	public Routescore obtener(Long id) {
		// TODO Auto-generated method stub
		Routescore score;
		s = HibernateUtil.sessionFactory.openSession();
		score = (Routescore) s.get(Routescore.class, id);
		s.close();
		return score;
	}

	@Override
	public Routescore obtener(long idRoute, long idUser) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Routescore WHERE route_id = :idRoute AND user_id = :idUser";
		Routescore score = null;
		try {
			Query q = s.createQuery(hql);
			q.setParameter("idRoute", idRoute);
			q.setParameter("idUser", idUser);
			score = (Routescore) q.uniqueResult();
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
		return score;
	}

}
