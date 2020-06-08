package dto;

import java.io.Serializable;

import model.Cargo;
import model.Combo;

public class DadosCargoDTO implements Serializable {

	private static final long serialVersionUID = -7642150873558622052L;
	private Cargo cargo;
	private String nomeCargo;
	private Long quantidadeFuncionarios;
	private Long quantidadeFuncionariosAtivos;
	private Double somaQuantidadeGasto;
	private Combo combo;
	
	public DadosCargoDTO(Cargo cargo, String nomeCargo, Long quantidadeFuncionarios, Long quantidadeFuncionariosAtivos,
			Double somaQuantidadeGasto, Combo combo) {
		this.cargo = cargo;
		this.nomeCargo = nomeCargo;
		this.quantidadeFuncionarios = quantidadeFuncionarios;
		this.quantidadeFuncionariosAtivos = quantidadeFuncionariosAtivos;
		this.somaQuantidadeGasto = somaQuantidadeGasto;
		this.combo = combo;
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public String getNomeCargo() {
		return nomeCargo;
	}
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}
	public Long getQuantidadeFuncionarios() {
		return quantidadeFuncionarios;
	}
	public void setQuantidadeFuncionarios(Long quantidadeFuncionarios) {
		this.quantidadeFuncionarios = quantidadeFuncionarios;
	}
	public Long getQuantidadeFuncionariosAtivos() {
		return quantidadeFuncionariosAtivos;
	}
	public void setQuantidadeFuncionariosAtivos(Long quantidadeFuncionariosAtivos) {
		this.quantidadeFuncionariosAtivos = quantidadeFuncionariosAtivos;
	}
	public Double getSomaQuantidadeGasto() {
		return somaQuantidadeGasto;
	}
	public void setSomaQuantidadeGasto(Double somaQuantidadeGasto) {
		this.somaQuantidadeGasto = somaQuantidadeGasto;
	}
	public Combo getCombo() {
		return combo;
	}
	public void setCombo(Combo combo) {
		this.combo = combo;
	}
	
	
}
