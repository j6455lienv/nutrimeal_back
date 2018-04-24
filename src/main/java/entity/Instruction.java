package entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Instruction {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInstruction;
	
	@Column
	private Long chrono;
	
	@Column
	private String libelle;
	
	@Column
	private Long idRecette;

	public Long getIdInstruction() {
		return idInstruction;
	}

	public void setIdInstruction(Long idInstruction) {
		this.idInstruction = idInstruction;
	}

	public Long getChrono() {
		return chrono;
	}

	public void setChrono(Long chrono) {
		this.chrono = chrono;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getIdRecette() {
		return idRecette;
	}

	public void setIdRecette(Long idRecette) {
		this.idRecette = idRecette;
	}
	
	
}
