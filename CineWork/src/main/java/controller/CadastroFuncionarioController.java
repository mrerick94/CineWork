package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.CargoDao;
import dao.EstadoDao;
import dao.FuncionarioDao;
import model.Cargo;
import model.Empresa;
import model.Endereco;
import model.Estado;
import model.Funcionario;

@ViewScoped
@ManagedBean
public class CadastroFuncionarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8248611163623911406L;
	private Funcionario funcionario;
	private FuncionarioDao funcionarioDao;
	private List<Estado> estados;
	private EstadoDao estadoDao;
	private List<Cargo> cargos;
	private CargoDao cargoDao;

	public CadastroFuncionarioController() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session.getAttribute("funcionario") == null) {
			funcionario = new Funcionario();
			funcionario.setEndereco(new Endereco());
			funcionario.setEmpresa((Empresa) session.getAttribute("empresaLogada"));
		} else {
			funcionario = (Funcionario) session.getAttribute("funcionario");
		}
		session.removeAttribute("funcionario");
		funcionarioDao = new FuncionarioDao();
		estadoDao = new EstadoDao();
		estados = estadoDao.buscarTodos();
		cargoDao = new CargoDao();
		cargos = cargoDao.listarPorEmpresa(funcionario.getEmpresa());
	}

	public String cadastrar() {
		FacesMessage mensagem = new FacesMessage();
		mensagem.setSeverity(FacesMessage.SEVERITY_INFO);
		if (funcionario.getId() != null) {
			funcionarioDao.alterar(funcionario);
			mensagem.setSummary("Colaborador alterado com sucesso.");
		} else {
			funcionarioDao.salvar(funcionario);
			mensagem.setSummary("Colaborador salvo com sucesso.");
		}
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		return "/app/listafuncionarios.xhtml?faces-redirect=true";
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

}
