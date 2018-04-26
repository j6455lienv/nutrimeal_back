-- RECETTES
INSERT INTO recette(id_recette, nom_recette, min_par_por, vit_par_por, temps_prepa)
VALUES (1, 'Lasagnes', 50, 37, 120);

INSERT INTO recette(id_recette, nom_recette, min_par_por, vit_par_por, temps_prepa)
VALUES (2, 'Sandwich à la fraise', 60, 80, 110);

INSERT INTO recette(id_recette, nom_recette, min_par_por, vit_par_por, temps_prepa)
VALUES (3, 'Banane au miel', 80, 90, 120);

-- INGREDIENTS
INSERT INTO ingredient(id_ingredient, libelle, unite_mesure, vitamines, mineraux)
VALUES (1, 'Pomme de terre', 'g', 150.46, 20.45);

INSERT INTO ingredient(id_ingredient, libelle, unite_mesure, vitamines, mineraux)
VALUES (2, 'Crème fraîche', 'cl', 150.46, 20.45);

INSERT INTO ingredient(id_ingredient, libelle, unite_mesure, vitamines, mineraux)
VALUES (3, 'fraise', 'g', 150.46, 20.45);

INSERT INTO ingredient(id_ingredient, libelle, unite_mesure, vitamines, mineraux)
VALUES (4, 'pate à tarte', 'g', 1.2, 65.4);

-- RECETTE INGREDIENT
INSERT INTO recette_ingredient(id_recette, id_ingredient, quantite)
VALUES (1, 1, 50);

INSERT INTO recette_ingredient(id_recette, id_ingredient, quantite)
VALUES (1, 2, 35);

INSERT INTO recette_ingredient(id_recette, id_ingredient, quantite)
VALUES (2, 3, 70);

INSERT INTO recette_ingredient(id_recette, id_ingredient, quantite)
VALUES (2, 4, 40);

-- INSTRUCTIONS 

INSERT INTO instruction(id_instruction, chrono, libelle, id_recette)
VALUES (1, 1, 'Prends tes patates et fous les tois dans le ***', 1);

INSERT INTO instruction(id_instruction, chrono, libelle, id_recette)
VALUES (2, 2, 'Et maintenant manges tes lasagnes', 1);

INSERT INTO instruction(id_instruction, chrono, libelle, id_recette)
VALUES (3, 1, 'prend les fraises, jette les', 2);

INSERT INTO instruction(id_instruction, chrono, libelle, id_recette)
VALUES (4, 2, 'vas acheter un vrai sandwich à la fraise', 2);