package com.example.nutrimeal.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.service.RecetteService;

@RestController
@RequestMapping(value = "/recette")
public class RecetteController {

  private final RecetteService recetteService;

  @Autowired
  public RecetteController(RecetteService pRecetteService) {
    this.recetteService = pRecetteService;
  }

  /**
   * Renvoi une liste de recette dont le nom contient une chaine de caractère.
   *
   * @param string   String, String to search
   * @param pageable Pageable
   * @return ResponseEntity_Page_Recette, recette list persisted
   */
  @GetMapping(path = "/search")
  public ResponseEntity<Page<Recette>> searchRecette(@RequestParam String string, Pageable pageable) {
    Page<Recette> result = recetteService.findRecetteContaining(string, pageable);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * Renvoi la recette persistée demandée par une requête.
   *
   * @param id Long recette id
   * @return ResponseEntity_Recette_, recette persisted
   */
  @GetMapping(path = "/{id}")
  public ResponseEntity<Recette> findThisRecette(@PathVariable("id") Long id) {
    Optional<Recette> recette = recetteService.get(id);
    if (recette.isPresent()) {
      Recette foundRecette = recette.get();
      foundRecette = recetteService.computeValues(foundRecette);
      return new ResponseEntity<>(foundRecette, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Renvoi un ensemble aléatoire de recettes qui ont une image. Pour le carroussel.
   *
   * @return une réponse HTTP avec les recette tirées au hasard
   */
  @GetMapping(value = "/withimages")
  public ResponseEntity<Set<Recette>> findRecettesWithImages() {
    Set<Recette> recettes = recetteService.findRandomRecettesWithImages();
    return new ResponseEntity<>(recettes, HttpStatus.OK);
  }
}
