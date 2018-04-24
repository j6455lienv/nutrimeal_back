package entity;

import javax.persistence.Column;

public class RecetteIngredient {

	@Column
	private Long idRecette;
	
	@Column
	private Long idIngredient;
	
	@Column
	private Double quantite;
	
}
