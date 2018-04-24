package com.example.nutrimeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
