package com.example.nutrimeal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.nutrimeal.model.enumeration.UniteMesure;
import com.example.nutrimeal.model.enumeration.converter.UniteMesureConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "INGREDIENT")
@Getter
@Setter
public class Ingredient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_INGREDIENT")
	private Long id;
	
	@OneToMany(mappedBy="ingredients")
	@JsonIgnore
	public Set<RecetteIngredient> listeRecettes = new HashSet<>();

	@Column
	private String libelle;

	@Column(name = "UNITE_MESURE")
	@Convert(converter = UniteMesureConverter.class)
	private UniteMesure uniteMesure;
	
	@Column
	private Double vitamineC;
	
	@Column
	private Double vitamineD;
	
	@Column
	private Double vitamineB12;
	
	@Column
	private Double fer;
	
	@Column
	private Double sodium;
}
