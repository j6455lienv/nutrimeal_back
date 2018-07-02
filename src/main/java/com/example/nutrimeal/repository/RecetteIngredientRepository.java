package com.example.nutrimeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.RecetteIngredient;

@Repository
public interface RecetteIngredientRepository extends JpaRepository<RecetteIngredient, Long>{

}
