/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de cr�ation :  07/06/2022 23:13:09                      */
/*==============================================================*/


drop table if exists CLIENT;

drop table if exists COMMANDE;

drop table if exists DATE;

drop table if exists PRODUIT;

/*==============================================================*/
/* Table : CLIENT                                               */
/*==============================================================*/
create table CLIENT
(
   CODE_CLI             int not null,
   NOM_CLI              varchar(25),
   primary key (CODE_CLI)
);

/*==============================================================*/
/* Table : COMMANDE                                            */
/*==============================================================*/
create table COMMANDE
(
   DATE                 date not null,
   CODE_CLI             int not null,
   REF_PROD             int not null,
   QTE_COM              int not null,
   primary key (DATE, CODE_CLI, REF_PROD)
);

/*==============================================================*/
/* Table : DATE                                                 */
/*==============================================================*/
create table DATE
(
   DATE                 date not null,
   primary key (DATE)
);

/*==============================================================*/
/* Table : PRODUIT                                              */
/*==============================================================*/
create table PRODUIT
(
   REF_PROD             int not null,
   DESIGN_PROD          varchar(20),
   STOCK_PROD           int,
   PRIX_UNITAIRE        int,
   primary key (REF_PROD)
);

alter table COMMANDE add constraint FK_COMMANDE foreign key (DATE)
      references DATE (DATE) on delete restrict on update restrict;

alter table COMMANDE add constraint FK_COMMANDE4 foreign key (CODE_CLI)
      references CLIENT (CODE_CLI) on delete restrict on update restrict;

alter table COMMANDE add constraint FK_COMMANDE5 foreign key (REF_PROD)
      references PRODUIT (REF_PROD) on delete restrict on update restrict;

