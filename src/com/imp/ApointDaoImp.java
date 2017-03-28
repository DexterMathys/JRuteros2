package com.imp;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dao.ApointDao;
import com.model.Apoint;
import com.model.Travel;
import com.util.HibernateUtil;



public class ApointDaoImp implements ApointDao{
	
	private Session s;

	@Override
	public List<Apoint> listar() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Apoint> points = null;
		
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Apoint";
		try {
			points = s.createQuery(hql).list();
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
		return points;
	}

	@Override
	public void nuevo(Apoint point) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(point);
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
	public void editar(Apoint point) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(point);
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
	public void eliminar(Apoint point) {
		// TODO Auto-generated method stub
		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(point);
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
	public boolean existe(Apoint point) {
		// TODO Auto-generated method stub
		boolean ex = false;
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Apoint where id = :id ";
		try {
			ex = (s.createQuery(hql).setString("id",point.getId().toString()).uniqueResult() != null);
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
	public Apoint obtener(Long id) {
		// TODO Auto-generated method stub
		Apoint point;
		s = HibernateUtil.sessionFactory.openSession();
		point  = (Apoint) s.get(Apoint.class, id);
		s.close();
		return point;
	}

	@Override
	public List<Apoint> recuperarTodos(Travel travel) {
		// TODO Auto-generated method stub
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM Apoint where travel_id = :id ";
		List<Apoint> points = null;
		try {
			points = s.createQuery(hql).setString("id",travel.getId().toString()).list();
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
		return points;
			
	}

	@Override
	public void borrarTodosPorTravel(Travel travel) {
		// TODO Auto-generated method stub
		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "DELETE FROM Apoint where travel_id = :id ";
		try {
			s.createQuery(hql).setString("id",travel.getId().toString()).executeUpdate();
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
		
	}

}
