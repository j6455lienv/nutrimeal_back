package com.example.nutrimeal.model.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.EnumValues;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum UniteMesure {
	@JsonProperty("unité")
	UNITE("unite"),
	@JsonProperty("g")
	GRAMME("g"),
	@JsonProperty("cl")
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
