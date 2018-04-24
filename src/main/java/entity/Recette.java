package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Recette {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRecette;
	
	@Column
	private String nomRecette;
	
	@Column
	private Double minerauxParPortion;
	
	@Column
	private Double vitaminesParPortion;
	
	@Column
	private Integer tempsPreparation;

	public Long getIdRecette() {
		return idRecette;
	}

	public void setIdRecette(Long idRecette) {
		this.idRecette = idRecette;
	}

	public String getNomRecette() {
		return nomRecette;
	}

	public void setNomRecette(String nomRecette) {
		this.nomRecette = nomRecette;
	}

	public Double getMinerauxParPortion() {
		return minerauxParPortion;
	}

	public void setMinerauxParPortion(Double minerauxParPortion) {
		this.minerauxParPortion = minerauxParPortion;
	}

	public Double getVitaminesParPortion() {
		return vitaminesParPortion;
	}

	public void setVitaminesParPortion(Double vitaminesParPortion) {
		this.vitaminesParPortion = vitaminesParPortion;
	}

	public Integer getTempsPreparation() {
		return tempsPreparation;
	}

	public void setTempsPreparation(Integer tempsPreparation) {
		this.tempsPreparation = tempsPreparation;
	}
	
	
}
