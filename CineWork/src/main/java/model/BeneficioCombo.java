package model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table (name = "beneficio_combo")
public class BeneficioCombo implements IBaseModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2581581161948938256L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(targetEntity = Beneficio.class, optional = false)
	@JoinColumn(name = "beneficio_id", referencedColumnName = "id")
	private Beneficio beneficio;
	
	@ManyToOne(targetEntity = Combo.class, optional = false)
	@JoinColumn(name = "combo_id", referencedColumnName = "id")
	private Combo combo;
	
	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Beneficio getBeneficio() {
		return beneficio;
	}
	public void setBeneficio(Beneficio beneficio) {
		this.beneficio = beneficio;
	}
	public Combo getCombo() {
		return combo;
	}
	public void setCombo(Combo combo) {
		this.combo = combo;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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
		BeneficioCombo other = (BeneficioCombo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
