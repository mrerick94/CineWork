package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import dao.EmpresaDao;
import dao.EstadoDao;
import model.Empresa;
import model.Endereco;
import model.Estado;

@ViewScoped
@ManagedBean
public class CadastroEmpresaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120854438289536748L;

	private Empresa empresa;
	private EmpresaDao empresaDao;
	private List<Estado> estados;
	private EstadoDao estadoDao;
	private String confirmacaoSenha;
	private boolean aceitaTermos;

	@PostConstruct
	public void init() {
		empresa = new Empresa();
		empresa.setEndereco(new Endereco());
		empresaDao = new EmpresaDao();
		estadoDao = new EstadoDao();
		estados = estadoDao.buscarTodos();
		
	}

	public String cadastrar() {
		if (verificarSenha()) {
			if (!aceitaTermos) {
				FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_WARN, "Você precisa aceitar o Contrato de Serviços da CineWork para poder usar nosso Serviço.",
						null);
				FacesContext.getCurrentInstance().addMessage(null, mensagem);
				return null;
			}
			empresaDao.salvar(empresa);
			FacesMessage mensagem = new FacesMessage();
			mensagem.setSeverity(FacesMessage.SEVERITY_INFO);
			mensagem.setSummary("Empresa salva com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "/login.xhtml?faces-redirect=true";
	}
	
	public void confirmarSenha(AjaxBehaviorEvent e) {
		if (!confirmacaoSenha.equals(empresa.getSenha())) {
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os campos Senha e Confirmação de Senha estão diferentes!",
					null);
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
	}
	
	private boolean verificarSenha() {
		if (!confirmacaoSenha.equals(empresa.getSenha())) {
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os campos Senha e Confirmação de Senha estão diferentes!",
					null);
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return false;
		}
		return true;
	}
	
	public String voltar() {
		return "/login.xhtml?faces-redirect=true";
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public boolean isAceitaTermos() {
		return aceitaTermos;
	}

	public void setAceitaTermos(boolean aceitaTermos) {
		this.aceitaTermos = aceitaTermos;
	}
}
