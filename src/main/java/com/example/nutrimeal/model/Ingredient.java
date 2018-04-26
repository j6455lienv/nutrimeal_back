package com.example.nutrimeal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ingredient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ingredient")
    private Long id;
	
	@Column
	private String libelle;

	@Column(name = "unite_mesure")
	private String uniteMesure;
	
	@Column
	private Double vitamines;
	
	@Column
	private Double mineraux;
}
