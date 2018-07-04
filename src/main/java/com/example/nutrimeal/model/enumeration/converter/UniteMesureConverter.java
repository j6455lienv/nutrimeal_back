package com.example.nutrimeal.model.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.example.nutrimeal.model.enumeration.UniteMesure;

public class UniteMesureConverter implements AttributeConverter<UniteMesure, String> {

	@Override
	public String convertToDatabaseColumn(UniteMesure attribute) {
		return attribute.getLabel();
	}

	@Override
	public UniteMesure convertToEntityAttribute(String dbData) {
		return UniteMesure.findByLabel(dbData);
	}
}
