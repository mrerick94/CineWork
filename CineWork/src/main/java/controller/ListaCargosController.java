package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import dao.CargoDao;
import dao.ComboDao;
import dao.FuncionarioDao;
import dto.DadosCargoDTO;
import model.Cargo;
import model.Combo;
import model.Empresa;

@ViewScoped
@ManagedBean
public class ListaCargosController implements Serializable {

	private static final long serialVersionUID = 4741876431950964005L;

	private FuncionarioDao funcionarioDao;
	private List<DadosCargoDTO> dados;
	private Empresa empresa;
	private Cargo cargo;
	private CargoDao cargoDao;
	private Combo combo;
	private ComboDao comboDao;
	private List<Combo> combos;
	private String fieldVar;
	
	@PostConstruct
	private void init() {
		funcionarioDao = new FuncionarioDao();
		cargoDao = new CargoDao();
		comboDao = new ComboDao();
		combos = comboDao.buscarTodos();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresaLogada");
		dados = funcionarioDao.buscaDadosListagemCargos(empresa);
	}
	
	public String listarColaboradoresDoCargo() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("filtroCargoListagemFuncionarios", cargo);
		return "/app/listafuncionarios.xhtml?faces-redirect=true";
	}
	
	public void botarComboNoCargoSelecionado() {
		
		FacesMessage mensagem = new FacesMessage();
		if (combo == null) {
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			mensagem.setSummary("Você deve selecionar um combo para o Cargo!");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return;
		}
		cargo.setCombo(combo);
		cargoDao.alterar(cargo);
		mensagem.setSeverity(FacesMessage.SEVERITY_INFO);
		mensagem.setSummary("Combo contratado para o Cargo selecionado. Obrigado!");
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		PrimeFaces.current().executeScript("PF('dialogEscolheCombo').hide();");
	}

	public List<DadosCargoDTO> getDados() {
		return dados;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public List<Combo> getCombos() {
		return combos;
	}

	public String getFieldVar() {
		return fieldVar;
	}

	public void setFieldVar(String fieldVar) {
		this.fieldVar = fieldVar;
	}
}
