ALTER TABLE recette
MODIFY COLUMN image_recette VARCHAR(MAX);

-- RECETTES
INSERT INTO recette(id_recette, nom_recette, TEMPS_PREPARATION , image_recette)
VALUES (1, 'Lasagnes', 50, null);



-- INGREDIENTS
INSERT INTO ingredient(id_ingredient, libelle, unite_mesure, sodium, fer, vitamineC, vitamineD, vitamineB12)
VALUES (1, 'Pomme de terre', 'g', 1782, 5, 102, 0.001, 0.0001);

insert into ingredient(ID_INGREDIENT, LIBELLE, UNITE_MESURE, sodium, fer, vitamineC, vitamineD, vitamineB12)
values (7, 'Steak', 'g', 1850, 0.026, 0.005, 0.002, 0.0007);


-- RECETTE INGREDIENT
INSERT INTO recette_ingredient(id_recette, id_ingredient, quantite)
VALUES (1, 1, 150);

INSERT INTO recette_ingredient(id_recette, id_ingredient, quantite)
VALUES (1, 7, 165);

