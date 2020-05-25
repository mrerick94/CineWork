package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import model.Cargo;
import model.Empresa;

public class CargoDao extends BaseDao<Cargo> {

	public List<Cargo> listarPorEmpresa(Empresa empresa) {
		EntityManager manager = JPAUtil.getEntityManager();
		String jpql = "select c from Cargo c " + "where empresa_id = :empresa";
		TypedQuery<Cargo> query = manager.createQuery(jpql, Cargo.class);
		query.setParameter("empresa", empresa.getId());
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			manager.close();
		}
	}
}
