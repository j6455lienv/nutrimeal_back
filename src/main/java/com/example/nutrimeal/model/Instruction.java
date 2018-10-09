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
public class Instruction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_instruction")
  private Long id;

  @Column
  private Long chrono;

  @Column
  private String libelle;

  @Column(name = "ID_RECETTE")
  private Long recetteId;
}

