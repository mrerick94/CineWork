package bo;

import dao.EstadoDao;
import dto.EnderecoDTO;
import model.Endereco;

public class EnderecoBO {

	public static Endereco parseEnderecoDTO(EnderecoDTO dto) {
		EstadoDao ufDao = new EstadoDao();
		Endereco endereco = new Endereco();
		String cep = dto.getCep();
		String cepFormatado = cep.substring(0, 2) + "." + cep.substring(2);
		endereco.setCep(cepFormatado);
		endereco.setCidade(dto.getLocalidade());
		endereco.setEstado(ufDao.getEstadoFromUF(dto.getUf()));
		endereco.setComplemento(dto.getComplemento());
		endereco.setLogradouro(dto.getLogradouro());
		return endereco;
	}
}
