package com.example.nutrimeal.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Ingredient;

@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {
}
