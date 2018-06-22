
drop table if exists Recette;
drop table if exists recette_ingredient;
drop table if exists ingredient;


create table Recette;
alter table RECETTE add column ID_RECETTE numeric(11,0);
alter table RECETTE add column NOM_RECETTE VARCHAR2(255);


create table recette_ingredient;
alter table recette_ingredient add column ID_RECETTE numeric(11,0);
alter table recette_ingredient add column ID_INGREDIENT numeric(11,0);
alter table recette_ingredient add column  QUANTITE numeric(3,0);

create table ingredient;
alter table ingredient add column ID_INGREDIENT numeric(11,0);
alter table ingredient add column LIBELLE VARCHAR2(255);
alter table ingredient add column VITAMINES numeric(11,3);
alter table ingredient add column MINERAUX numeric(11,3);
alter table ingredient add column UNITE_MESURE VARCHAR2(5);




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

