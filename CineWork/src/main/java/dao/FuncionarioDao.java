package dao;

import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import dto.DadosCargoDTO;
import filter.FuncionarioFilter;
import model.Cargo;
import model.Empresa;
import model.Funcionario;

public class FuncionarioDao extends BaseDao<Funcionario> {
	
	public List<DadosCargoDTO> buscaDadosListagemCargos(Empresa empresa) {
		EntityManager manager = JPAUtil.getEntityManager();
		
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append("select new dto.DadosCargoDTO( ");
		jpqlBuilder.append("  c, ");
		jpqlBuilder.append("  c.nome, ");
		jpqlBuilder.append("  count(f) as all_count, ");
		jpqlBuilder.append("  sum(case when f.ativo=1 then 1 else 0 end) as ativo_count, ");
		jpqlBuilder.append("  sum(case when f.ativo=1 then (select combo.valor from Combo combo where combo=c.combo) else 0 end) as soma_gasto, ");
		jpqlBuilder.append("  c.combo ");
		jpqlBuilder.append(") ");
		jpqlBuilder.append("from Funcionario f ");
		jpqlBuilder.append("right join Cargo c on c=f.cargo where c.empresa= :empresa ");
		jpqlBuilder.append("group by c.nome ");
		
		
		TypedQuery<DadosCargoDTO> query = manager
				.createQuery(jpqlBuilder.toString(), DadosCargoDTO.class);
		
		query.setParameter("empresa", empresa);
		List<DadosCargoDTO> resultado = query.getResultList();
		
		manager.close();
		
		return resultado;
	}

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
	
	public int totalPorCargo(Cargo cargo) {
		EntityManager manager = JPAUtil.getEntityManager();

		try {
			String jpql = "select count(f) from Funcionario f where f.cargo= :cargo";
			TypedQuery<Long> query = manager.createQuery(jpql, Long.class);
			query.setParameter("cargo", cargo);
			return query.getSingleResult().intValue();
		} finally {
			manager.close();
		}
	}
	
	public int totalAtivosPorCargo(Cargo cargo) {
		EntityManager manager = JPAUtil.getEntityManager();

		try {
			String jpql = "select count(f) from Funcionario f where f.cargo= :cargo and f.ativo=1";
			TypedQuery<Long> query = manager.createQuery(jpql, Long.class);
			query.setParameter("cargo", cargo);
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

			//cargo.combo.descricao
			if (filtro.getPropriedadeOrdenacao() != null) {
				jpqlBuilder.append(" order by ").append(filtro.getPropriedadeOrdenacao());

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
