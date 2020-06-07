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
import model.BeneficioCombo;
import model.Cargo;
import model.Combo;
import model.Empresa;

@ViewScoped
@ManagedBean
public class ListaPacotesBeneficiosController implements Serializable {

	private static final long serialVersionUID = -4999236877412154413L;

	private Combo combo;
	private ComboDao comboDao;
	private List<Combo> combos;
	private BeneficioCombo beneficioCombo;
	private List<Cargo> cargos;
	private CargoDao cargoDao;
	private Empresa empresa;
	private Cargo cargo;
	
	@PostConstruct
	private void init() {
		combo = new Combo();
		comboDao = new ComboDao();
		combos = comboDao.buscarTodos();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresaLogada");
		cargoDao = new CargoDao();
		cargos = cargoDao.listarPorEmpresa(empresa);
	}
	
	public void botarComboNoCargoSelecionado() {
		FacesMessage mensagem = new FacesMessage();
		if (cargo == null) {
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			mensagem.setSummary("Você deve selecionar um cargo para contratar o combo!");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return;
		}
		cargo.setCombo(combo);
		cargoDao.alterar(cargo);
		mensagem.setSeverity(FacesMessage.SEVERITY_INFO);
		mensagem.setSummary("Combo contratado para o Cargo selecionado. Obrigado!");
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		PrimeFaces.current().executeScript("PF('dialogEscolheCargo').hide();");
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

	public BeneficioCombo getBeneficioCombo() {
		return beneficioCombo;
	}

	public void setBeneficioCombo(BeneficioCombo beneficioCombo) {
		this.beneficioCombo = beneficioCombo;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
}
