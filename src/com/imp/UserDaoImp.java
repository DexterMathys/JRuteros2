package com.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.dao.UserDao;
import com.model.User;
import com.util.HibernateUtil;

public class UserDaoImp implements UserDao {

	private Session s;
	//= HibernateUtil.sessionFactory.openSession();

	@Override
	public void nuevoUser(User user) {

		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.save(user);
			s.getTransaction().commit();
		} catch (Exception e) {
			s.getTransaction().rollback();
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public void editarUser(User user) {

		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.update(user);
			s.getTransaction().commit();
		} catch (Exception e) {
			s.getTransaction().rollback();
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@Override
	public void eliminarUser(User user) {

		try {
			s = HibernateUtil.sessionFactory.openSession();
			s.beginTransaction();
			s.delete(user);
			s.getTransaction().commit();
		} catch (Exception e) {
			s.getTransaction().rollback();
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listarUsers() {
		List<User> users = null;

		s = HibernateUtil.sessionFactory.openSession();
		Transaction t = s.beginTransaction();
		String hql = "FROM User";
		try {
			users = s.createQuery(hql).list();
			t.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			t.rollback();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return users;
	}

	@Override
	public User verificarDatos(User user) {
		User us = null;

		try {
			s = HibernateUtil.getSessionfactory().openSession();
			String hql = "FROM User WHERE userName = '" + user.getUserName() + "' AND password = '" + user.getPassword()
					+ "'";
			Query query = s.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (User) query.list().get(0);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return us;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listarOrdenado() {
		List<User> users = null;

		try {
			s = HibernateUtil.getSessionfactory().openSession();
			Criteria cri = s.createCriteria(User.class);
			cri.addOrder(Order.asc("userName"));
			users = cri.list();
		} catch (Exception e) {
			throw e;
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return users;
	}

	@Override
	public boolean existUsername(String username) {
		boolean result = false;

		try {
			s = HibernateUtil.getSessionfactory().openSession();
			String hql = "FROM User WHERE userName = '" + username + "'";
			Query query = s.createQuery(hql);

			if (!query.list().isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return result;
	}

}
