package com.example.nutrimeal.service;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.model.RecetteIngredient;
import com.example.nutrimeal.repository.RecetteRepository;

import utils.Constantes;
import utils.HandleNumbers;

@Service
public class RecetteService {

  private final RecetteRepository recetteRepository;

  @Autowired
  private RecetteService(RecetteRepository pRecetteRepository) {
    this.recetteRepository = pRecetteRepository;
  }

  /**
   * Méthode qui retourne une recette lorsque l'on donne une idRecette
   *
   * @param id id de la recette à récupérer
   * @return Un objet Recette
   */
  public Recette getRecetteById(Long id) {
    return recetteRepository.getOne(id);
  }

  /**
   * Méthode qui extrait la liste des recettes au format clé / valeur
   *
   * @return Liste_Paire, utilisee pour alimenter les listes de recettes
   */
  public Map<Long, String> alimentationListesRecettes() {

    List<Recette> listeRecettes = recetteRepository.findAll();

    Map<Long, String> listePaireIdRecetteNomRecette = new HashMap<>();

    for (Recette recette : listeRecettes) {
      String value = recette.getNomRecette();
      Long key = recette.getId();
      listePaireIdRecetteNomRecette.put(key, value);
    }

    return listePaireIdRecetteNomRecette;

  }

  /**
   * Récupère une recette persistée à partir de son id.
   *
   * @param id l'id de la recette
   * @return la recette persistée
   */
  public Optional<Recette> get(Long id) {
    return recetteRepository.findById(id);
  }

  /**
   * Récupère une page de résultat de recette contenant la chaine recherchée.
   *
   * @param string   la chaine recherchée
   * @param pageable les paramètres de pagination
   * @return la liste des recettes correspondant à la recherche
   */
  public Page<Recette> findRecetteContaining(String string, Pageable pageable) {
    return recetteRepository.findByNomRecetteContainsIgnoreCaseOrderByNomRecette(pageable, string);
  }

  /**
   * Récupère aléatoirement des recettes avec images en base pour le carroussel de la page d'accueil.
   *
   * @return la liste des recettes pris aléatoirement en base
   */
  public Set<Recette> findRandomRecettesWithImages() {
    int elementsPerPage = 20;
    Set<Recette> result = new HashSet<>();

    Page<Recette> recetteListe = recetteRepository.findByBase64ImageCodeNotNull(PageRequest.of(0, elementsPerPage));

    if (recetteListe.hasContent()) {
      long totalElementsNumber = recetteListe.getTotalElements();
      int i = 0;
      int randomRecetteIndex;
      int askedPage;
      int elementIndexInPage;
      List<Recette> pageContent;

      while (i < Constantes.CARROUSSEL_RECETTE_NUMBER) {
        Random r = new Random();
        randomRecetteIndex = r.nextInt((int) totalElementsNumber);
        askedPage = r.nextInt((int) (randomRecetteIndex / (double) elementsPerPage));

        recetteListe = recetteRepository.findByBase64ImageCodeNotNull(PageRequest.of(askedPage, elementsPerPage));

        pageContent = recetteListe.getContent();
        elementIndexInPage = randomRecetteIndex % elementsPerPage;

        result.add(pageContent.get(elementIndexInPage));
        i++;
      }
    }
    return result;
  }

  /**
   * Permet de calculer les apports en vitamines et en minéraux d'une recette.
   *
   * @param recette la recette dont on veut calculer les apports
   * @return la recette mise à jour avec ses apports
   */
  public Recette computeValues(Recette recette) {

    List<Double> apports = calculNutrimentsParRecette(recette);

    recette.setSodiumParPortion(HandleNumbers.get(apports.get(0) * 100d / Constantes.SODIUM));
    recette.setFerParPortion(HandleNumbers.get(apports.get(1) * 100d / Constantes.FER));
    recette.setVitamineCParPortion(HandleNumbers.get(apports.get(2) * 100d / Constantes.VITAMINE_C));
    recette.setVitamineDParPortion(HandleNumbers.get(apports.get(3) * 100d / Constantes.VITAMINE_D));
    recette.setVitamineB12ParPortion(HandleNumbers.get(apports.get(4) * 100d / Constantes.VITAMINE_B12));

    return recette;
  }

  /**
   * La méthode renvoie une liste de valeurs nutritionnelles
   *
   * @param recette Un objet Recette
   * @return vitaminesTotales total en nutriments de la recette,List_Double So, Fe, VC,, VD, VB12)
   */
  public List<Double> calculNutrimentsParRecette(Recette recette) {

    double sodium = 0d;
    double fer = 0d;
    double vitamineC = 0d;
    double vitamineD = 0d;
    double vitamineB12 = 0d;
    // Les ingrédients sont récupérés
    Set<RecetteIngredient> ingredients = recette.getRecetteIngredients();
    List<Double> apports = new ArrayList<>();
    // Pour chaque ingrédient, la quantité est multipliée par les vitamines par ingrédient.
    for (RecetteIngredient ingredient : ingredients) {
      sodium += ingredient.getQuantite() * ingredient.getIngredients().getSodium();
      fer += ingredient.getQuantite() * ingredient.getIngredients().getFer();
      vitamineC += ingredient.getQuantite() * ingredient.getIngredients().getVitamineC();
      vitamineD += ingredient.getQuantite() * ingredient.getIngredients().getVitamineD();
      vitamineB12 += ingredient.getQuantite() * ingredient.getIngredients().getVitamineB12();
    }
    apports.add(sodium);
    apports.add(fer);
    apports.add(vitamineC);
    apports.add(vitamineD);
    apports.add(vitamineB12);
    return apports;
  }
}
