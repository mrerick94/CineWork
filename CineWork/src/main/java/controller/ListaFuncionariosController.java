package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import dao.CargoDao;
import dao.FuncionarioDao;
import lazymodel.FuncionarioLazyModel;
import model.Cargo;
import model.Funcionario;

@ViewScoped
@ManagedBean
public class ListaFuncionariosController implements Serializable {

	private static final long serialVersionUID = 3790461869640196571L;

	private Funcionario funcionario;
	private List<Funcionario> funcionariosFiltro;
	private FuncionarioDao funcionarioDao;
	private LazyDataModel<Funcionario> model;
	private List<Cargo> cargos;
	private CargoDao cargoDao;
	private Cargo[] cargosSelecionados;
	
	@PostConstruct
	public void init() {
		funcionario = new Funcionario();
		funcionarioDao = new FuncionarioDao();
		cargoDao = new CargoDao();
		cargos = cargoDao.buscarTodos();
		buscar();
		
	}
	
	public void buscar() {
		model = new FuncionarioLazyModel(funcionarioDao);
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionariosFiltro() {
		return funcionariosFiltro;
	}

	public void setFuncionariosFiltro(List<Funcionario> funcionariosFiltro) {
		this.funcionariosFiltro = funcionariosFiltro;
	}

	public LazyDataModel<Funcionario> getModel() {
		return model;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public Cargo[] getCargosSelecionados() {
		return cargosSelecionados;
	}

	public void setCargosSelecionados(Cargo[] cargosSelecionados) {
		this.cargosSelecionados = cargosSelecionados;
	}
	
}
