drop table if exists Recette;
drop table if exists recette_ingredient;
drop table if exists ingredient;

create table Recette;
alter table RECETTE add column ID_RECETTE numeric(11,0);
alter table RECETTE add column NOM_RECETTE VARCHAR2(255);
alter table RECETTE add column TEMPS_PREPARATION numeric (4,0);

create table recette_ingredient;
alter table recette_ingredient add column ID_RECETTE numeric(11,0);
alter table recette_ingredient add column ID_INGREDIENT numeric(11,0);
alter table recette_ingredient add column QUANTITE numeric(3,0);

create table ingredient;
alter table ingredient add column ID_INGREDIENT numeric(11,0);
alter table ingredient add column LIBELLE VARCHAR2(255);
alter table ingredient add column VITAMINES numeric(11,3);
alter table ingredient add column MINERAUX numeric(11,3);
alter table ingredient add column UNITE_MESURE VARCHAR2(5);

create table instruction;
alter table ingredient add column ID_INSTRUCTION numeric(11,0);
alter table instruction add column CONTENU VARCHAR2(255);
alter table ingredient add column ID_RECETTE numeric(11,0);