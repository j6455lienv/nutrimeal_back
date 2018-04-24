package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ingredient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIngredient;
	
	@Column
	private String libelle;

	@Column
	private String uniteMesure;
	
	@Column
	private Double vitamines;
	
	@Column
	private Double mineraux;

	public Long getIdIngredient() {
		return idIngredient;
	}

	public void setIdIngredient(Long idIngredient) {
		this.idIngredient = idIngredient;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getUniteMesure() {
		return uniteMesure;
	}

	public void setUniteMesure(String uniteMesure) {
		this.uniteMesure = uniteMesure;
	}

	public Double getVitamines() {
		return vitamines;
	}

	public void setVitamines(Double vitamines) {
		this.vitamines = vitamines;
	}

	public Double getMineraux() {
		return mineraux;
	}

	public void setMineraux(Double mineraux) {
		this.mineraux = mineraux;
	}
	
}
