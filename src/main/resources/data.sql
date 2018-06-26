insert into recette(ID_RECETTE, NOM_RECETTE)
values(1, 'Salade césar');

insert into recette(ID_RECETTE, NOM_RECETTE)
values(2, 'Steak frite');


insert into ingredient(ID_INGREDIENT, LIBELLE, VITAMINES, MINERAUX, UNITE_MESURE)
values (1, 'Carottes',405,438, 'unite');

insert into ingredient(ID_INGREDIENT, LIBELLE, VITAMINES, MINERAUX, UNITE_MESURE)
values (2, 'Pommes de terre',410,39, 'g');

insert into ingredient(ID_INGREDIENT, LIBELLE, VITAMINES, MINERAUX, UNITE_MESURE)
values (3, 'Steak',90,48, 'g');

insert into ingredient(ID_INGREDIENT, LIBELLE, VITAMINES, MINERAUX, UNITE_MESURE)
values (4, 'Sauce tomate',10,28, 'cL');

insert into ingredient(ID_INGREDIENT, LIBELLE, VITAMINES, MINERAUX, UNITE_MESURE)
values (5, 'Sel',403,484, 'mg');

insert into ingredient(ID_INGREDIENT, LIBELLE, VITAMINES, MINERAUX, UNITE_MESURE)
values (6, 'Tomate',450,408, 'unite');


insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (1,2,34);

insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (1,1,2);

insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (1,6,5);

insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (2,5,6);

insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (2,4,1);

insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (2,1,3);

insert into recette_ingredient(ID_RECETTE, ID_INGREDIENT, QUANTITE)
values (2,2,7);

