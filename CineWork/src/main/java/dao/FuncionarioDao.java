package dao;

import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import filter.FuncionarioFilter;
import model.Cargo;
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
				if (entry.getKey().equals("cargo")) {
					System.out.println("++++++++++++++++++++++++++++++++ key: " + entry.getKey());
					jpqlBuilder.append(" and f.").append(entry.getKey()).append("= :").append(entry.getKey() + "0");
					Cargo[] cargosArray = (Cargo[]) entry.getValue();
					for (int i = 1; i < cargosArray.length; i++) {
						jpqlBuilder.append(" or f.").append(entry.getKey()).append("= :").append(entry.getKey() + i);
					}
				} else {
					jpqlBuilder.append(" and f.").append(entry.getKey()).append(" like :").append(entry.getKey());
				}
			}

			if (filtro.getPropriedadeOrdenacao() != null) {
				jpqlBuilder.append("order by ").append(filtro.getPropriedadeOrdenacao());

				if (!filtro.isAscendente()) {
					jpqlBuilder.append(" desc");
				}
			}

			TypedQuery<Funcionario> query = manager.createQuery(jpqlBuilder.toString(), Funcionario.class);

			for (Entry<String, Object> entry : filtro.getFiltros().entrySet()) {
				if (entry.getKey().equals("cargo")) {
					Cargo[] cargosArray = (Cargo[]) entry.getValue();
					int i = 0;
					for (Cargo cargo : cargosArray) {
						query.setParameter(entry.getKey() + i, cargo);
						i++;
					}
				} else {
					query.setParameter(entry.getKey(), "%" + entry.getValue() + "%");
				}
			}

			query.setFirstResult(filtro.getPrimeiroRegistro());
			query.setMaxResults(filtro.getQuantidadeRegistros());

			return query.getResultList();

		} finally {
			manager.close();
		}
	}
}
