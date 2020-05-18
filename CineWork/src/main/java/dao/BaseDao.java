package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dao.util.JPAUtil;
import model.IBaseModel;



@SuppressWarnings("unchecked")
public abstract class BaseDao<T extends IBaseModel> 
	implements IBaseDao<T> {
	
	private Class<T> persistenceClass;
	
	public BaseDao() {
		persistenceClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	
	@Override
	public void salvar(T model) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(model);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public void alterar(T model) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(model);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public void excluir(T model) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.remove(manager.find(model.getClass(), model.getId()));
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public T buscarPorID(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		T resultado = manager.find(persistenceClass, id);
		manager.close();
		return resultado;
	}
	
	@Override
	public List<T> buscarTodos() {
		EntityManager manager = JPAUtil.getEntityManager();
		
		TypedQuery<T> query = manager
				.createQuery("from " + persistenceClass.getName(), persistenceClass);
		
		List<T> resultado = query.getResultList();
		manager.close();
		
		return resultado;
	}
	
}
