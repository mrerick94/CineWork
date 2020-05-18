package model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table (name = "combo")
public class Combo implements IBaseModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7865655658291325306L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@Column(name = "imagepath", nullable = false)
	private String imagePath;
	
	@OneToMany(mappedBy = "combo", targetEntity = BeneficioCombo.class)
	private List<BeneficioCombo> beneficios;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public List<BeneficioCombo> getBeneficios() {
		return beneficios;
	}
	public void setBeneficios(List<BeneficioCombo> beneficios) {
		this.beneficios = beneficios;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combo other = (Combo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
