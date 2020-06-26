package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import model.Estado;

public class EstadoDao extends BaseDao<Estado> {

	public Estado getEstadoFromUF(String uf) {
		EntityManager manager = JPAUtil.getEntityManager();
		String jpql = "select e from Estado e " + "where uf = :uf";
		TypedQuery<Estado> query = manager.createQuery(jpql, Estado.class);
		query.setParameter("uf", uf);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			manager.close();
		}
	}
}
