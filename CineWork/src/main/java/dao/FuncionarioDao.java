package dao;

import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import filter.FuncionarioFilter;
import model.Funcionario;

public class FuncionarioDao extends BaseDao<Funcionario> {

	public int total() {
		EntityManager manager = JPAUtil.getEntityManager();
		
		try {
			String jpql = "select count(f) from Funcionario f";
			TypedQuery<Long> query = manager.createQuery(jpql, Long.class);
			return query.getSingleResult().intValue();
		} finally {
			manager.close();
		}
	}
	
	public List<Funcionario> buscar(FuncionarioFilter filtro) {
		EntityManager manager = JPAUtil.getEntityManager();
		
		try {
			
			StringBuilder jpqlBuilder = new StringBuilder();
			jpqlBuilder.append("select f from Funcionario f ");
			jpqlBuilder.append("where 1=1 ");

			for (Entry<String, Object> entry : filtro.getFiltros().entrySet()) {
				jpqlBuilder.append(" and f.").append(entry.getKey())
					.append(" like :").append(entry.getKey());
			}
			
			if (filtro.getPropriedadeOrdenacao() != null) {
				jpqlBuilder.append("order by ").append(filtro.getPropriedadeOrdenacao());
				
				if (!filtro.isAscendente()) {
					jpqlBuilder.append(" desc");
				}
			}
			
			TypedQuery<Funcionario> query = manager.createQuery(jpqlBuilder.toString(), Funcionario.class);

			for (Entry<String, Object> entry : filtro.getFiltros().entrySet()) {
				query.setParameter(entry.getKey(), "%"+entry.getValue()+"%");
			}
			
			query.setFirstResult(filtro.getPrimeiroRegistro());
			query.setMaxResults(filtro.getQuantidadeRegistros());
			
			return query.getResultList();
		
		} finally {
			manager.close();
		}
	}
}
