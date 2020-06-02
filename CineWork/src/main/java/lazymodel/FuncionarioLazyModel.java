package lazymodel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import dao.FuncionarioDao;
import filter.FuncionarioFilter;
import model.Funcionario;

public class FuncionarioLazyModel extends LazyDataModel<Funcionario> implements Serializable {

	private static final long serialVersionUID = -1853785612911969612L;

	private FuncionarioFilter filtro;
	private FuncionarioDao funcionarioDao;
	
	public FuncionarioLazyModel(FuncionarioDao dao) {
		funcionarioDao = dao;
		filtro = new FuncionarioFilter();
	}
	
	@Override
	public List<Funcionario> load(int first, int pageSize, String sortField, 
			SortOrder sortOrder, Map<String, Object> filters) {
		
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setPropriedadeOrdenacao(sortField);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setFiltros(filters);
		
		setRowCount(funcionarioDao.total());
		
		return funcionarioDao.buscar(filtro);
	}
}
