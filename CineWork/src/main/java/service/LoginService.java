package service;

import java.io.Serializable;

import dao.EmpresaDao;
import model.Empresa;

public class LoginService implements Serializable {

	private static final long serialVersionUID = 8036087376439893849L;
	private EmpresaDao dao;
	
	public LoginService() {
		dao = new EmpresaDao();
	}
	
	public Empresa verificaLogin(String email, String senha) {
		Empresa empresa = dao.verificaLogin(email, senha);
		if (empresa != null) {
			return empresa;
		} else {
			return null;
		}
	}
}
