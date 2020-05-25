package service;

import dao.EmpresaDao;
import model.Empresa;

public class LoginService {

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
