package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import model.Empresa;

public class EmpresaDao extends BaseDao<Empresa> {

	public Empresa verificaLogin(String email, String senha) {
		EntityManager manager = JPAUtil.getEntityManager();
		String jpql = "select e from Empresa e " + "where email = :email and senha = :senha";
		TypedQuery<Empresa> query = manager.createQuery(jpql, Empresa.class);
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			manager.close();
		}
	}
}
