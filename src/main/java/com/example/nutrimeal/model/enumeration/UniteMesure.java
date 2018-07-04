package com.example.nutrimeal.model.enumeration;

import lombok.Getter;

@Getter
public enum UniteMesure {
	UNITE("unite"),
	GRAMME("g"),
	CENTILITRE("cl");
	
	private UniteMesure(String s) {
		this.label = s;
	}
	
	/** Label de l'unité de mesure. */
	private String label;
	
	public static UniteMesure findByLabel(String dbData) {
		for (UniteMesure u : UniteMesure.values()) {
			if (u.getLabel().equals(dbData)) {
				return u;
			}
		}
		return null;
	}
}
