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
import org.primefaces.context.PrimeFacesContext;

import dao.EmpresaDao;
import dao.EstadoDao;
import model.Empresa;
import model.Estado;

@ViewScoped
@ManagedBean
public class ConfiguracoesController implements Serializable {

	private static final long serialVersionUID = -1147317493337407346L;

	private Empresa empresa;
	private EmpresaDao empresaDao;
	private List<Estado> estados;
	private EstadoDao estadoDao;
	private String novaSenha;
	private String senhaAntiga;
	private String confirmacaoNovaSenha;
	
	@PostConstruct
	private void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		empresa = (Empresa) session.getAttribute("empresaLogada");
		empresaDao = new EmpresaDao();
		estadoDao = new EstadoDao();
		estados = estadoDao.buscarTodos();
	}
	
	public void trocarSenha() {
		FacesMessage mensagem = new FacesMessage();
		if (!senhaAntiga.equals(empresa.getSenha())) {
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			mensagem.setSummary("A Senha antiga não está correta.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return;
		}
		
		if (!novaSenha.equals(confirmacaoNovaSenha)) {
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			mensagem.setSummary("A nova senha e a confirmação não estão iguais.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return;
		}
		
		empresa.setSenha(novaSenha);
		mensagem.setSeverity(FacesMessage.SEVERITY_INFO);
		mensagem.setSummary("Senha trocada. Clique em Salvar para surtir efeito.");
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		PrimeFaces.current().executeScript("PF('dialogTrocarSenha').hide();");
	}
	
	public void limparDialog() {
		senhaAntiga = "";
		novaSenha = "";
		confirmacaoNovaSenha = "";
	}
	
	public void salvar() {
		empresaDao.alterar(empresa);
		FacesMessage mensagem = new FacesMessage();
		mensagem.setSeverity(FacesMessage.SEVERITY_INFO);
		mensagem.setSummary("Dados alterados com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
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

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}

	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}
}
