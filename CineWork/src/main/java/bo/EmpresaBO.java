package bo;

import java.util.InputMismatchException;

import com.google.gson.Gson;

import dto.EmpresaDTO;
import dto.EnderecoDTO;
import model.Empresa;
import service.HttpService;

public class EmpresaBO {

	public static boolean cnpjValidator(String cnpj) {
		if (cnpj.matches("(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)")) {
			StringBuffer sb = new StringBuffer();
			String cnpjNumeros;
			for (Character c : cnpj.toCharArray()) {
				if (Character.isDigit(c)) {
					sb.append(c);
				}
			}
			cnpjNumeros = sb.toString();
			System.out.println("----------------------- CNPJ somente numeros: " + cnpjNumeros);
			if (cnpjNumeros.equals("00000000000000") || cnpjNumeros.equals("11111111111111")
					|| cnpjNumeros.equals("22222222222222") || cnpjNumeros.equals("33333333333333")
					|| cnpjNumeros.equals("44444444444444") || cnpjNumeros.equals("55555555555555")
					|| cnpjNumeros.equals("66666666666666") || cnpjNumeros.equals("77777777777777")
					|| cnpjNumeros.equals("88888888888888") || cnpjNumeros.equals("99999999999999")
					|| (cnpjNumeros.length() != 14))
				return (false);

			char dig13, dig14;
			int sm, i, r, num, peso;

			try {
				// Calculo do 1o. Digito Verificador
				sm = 0;
				peso = 2;
				for (i = 11; i >= 0; i--) {
					// converte o i-ésimo caractere do CNPJ em um número:
					// por exemplo, transforma o caractere '0' no inteiro 0
					// (48 eh a posição de '0' na tabela ASCII)
					num = (int) (cnpjNumeros.charAt(i) - 48);
					sm = sm + (num * peso);
					peso = peso + 1;
					if (peso == 10)
						peso = 2;
				}

				r = sm % 11;
				if ((r == 0) || (r == 1))
					dig13 = '0';
				else
					dig13 = (char) ((11 - r) + 48);

				// Calculo do 2o. Digito Verificador
				sm = 0;
				peso = 2;
				for (i = 12; i >= 0; i--) {
					num = (int) (cnpjNumeros.charAt(i) - 48);
					sm = sm + (num * peso);
					peso = peso + 1;
					if (peso == 10)
						peso = 2;
				}

				r = sm % 11;
				if ((r == 0) || (r == 1))
					dig14 = '0';
				else
					dig14 = (char) ((11 - r) + 48);

				// Verifica se os dígitos calculados conferem com os dígitos informados.
				if ((dig13 == cnpjNumeros.charAt(12)) && (dig14 == cnpjNumeros.charAt(13))) {
					System.out.println("Passou no calculo dos digitos");
					return (true);
				}
				else {
					System.out.println("CNPJ falhou no calculo");
					return (false);
				}
			} catch (InputMismatchException erro) {
				System.out.println("Erro no calculo dos digitos");
				return (false);
			}
		} else {
			System.out.println("CNPJ Falhou no regex");
			return false;
		}
	}
	
	public static Empresa consultarCNPJ(Empresa empresa) {
		Gson gson = new Gson();
		EmpresaDTO dto = gson.fromJson(HttpService.getCNPJ(cnpjNumeros(empresa.getCnpj())), EmpresaDTO.class);
		System.out.println("----------------------- Consulta deu certo? " + dto != null);
		if (dto.getError() == 0) {
			parseEmpresaDTO(empresa, dto);
			System.out.println(empresa.getEmail());
			return empresa;
		} else {
			return null;
		}
	}
	
	private static String cnpjNumeros(String cnpj) {
		StringBuffer sb = new StringBuffer();
		for (Character c : cnpj.toCharArray()) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private static void parseEmpresaDTO(Empresa empresa, EmpresaDTO dto) {
		Gson gson = new Gson();
		if (dto.getEmail() !=null) {
			empresa.setEmail(dto.getEmail());
		}
		if (dto.getPhone() !=null) {
			empresa.setTelefone(dto.getPhone().trim());
		}
		empresa.setNome(dto.getName());
		System.out.println("---------------- " + empresa.getNome());
		EnderecoDTO enderecoDTO = gson.fromJson(HttpService.getCEP(dto.getAddress().getZip().replace("-", "")), EnderecoDTO.class);
		empresa.setEndereco(EnderecoBO.parseEnderecoDTO(enderecoDTO));
		System.out.println(empresa.getEndereco().getCep());
	}
	
	public static boolean validarEmpresa(Empresa empresa) {
		if (empresa.getNome() == null || empresa.getEndereco().getCep() == null || empresa.getTelefone() == null
				|| empresa.getEndereco().getLogradouro() == null || empresa.getEndereco().getCidade() == null
				|| empresa.getEndereco().getEstado() == null || empresa.getSenha() == null) {
			return false;
		}
		if (empresa.getCnpj() == null) {
			return false;
		} else if (!cnpjValidator(empresa.getCnpj())) {
			return false;
		}
		if (empresa.getEmail() == null) {
			return false;
		} else if (!empresa.getEmail().matches("(^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$)")) {
			return false;
		}
		return true;
	}
}
