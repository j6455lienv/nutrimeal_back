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
public class RecetteRestController {

  private final RecetteService recetteService;

  @Autowired
  public RecetteRestController(RecetteService pRecetteService) {
    this.recetteService = pRecetteService;
  }

  /**
   * Renvoi une liste de recette dont le nom contient une chaine de caractère.
   *
   * @param string la string à rechercher
   * @return une réponse HTTP avec la liste des recettes persistées contenant la chaine de caractère dans leur libellé
   */
  @GetMapping(path = "/search")
  public ResponseEntity<Page<Recette>> searchRecette(@RequestParam String string, Pageable pageable) {
    Page<Recette> result = recetteService.findRecetteContaining(string, pageable);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * Renvoi la recette persistée demandée par une requête.
   *
   * @param id l'id de la recette
   * @return une réponse HTTP avec la recette serialisée
   * @throws Exception exception
   */
  @GetMapping(path = "/{id}")
  public ResponseEntity<Recette> findThisRecette(@PathVariable("id") Long id) throws Exception {
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
