package controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Empresa;
import service.LoginService;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -6983839857205389929L;

	private String email;
	private String senha;
	private LoginService service;
	
	public LoginController() {
		service = new LoginService();
	}
	
	public String login() {
		Empresa empresaLogada = service.verificaLogin(email, senha);
		if (empresaLogada == null) {
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ou senha inválido!", null);
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return "/login.xhtml";
		}
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("empresaLogada", empresaLogada);
		return "/app/listafuncionarios.xhtml?faces-redirect=true";
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}
	
	public String getNomeEmpresa() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Empresa empresa = (Empresa) session.getAttribute("empresaLogada");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + empresa.getNome());
		return empresa.getNome();
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
