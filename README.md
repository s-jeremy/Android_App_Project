<center><img 
    width="280" 
    height="100"
    src="https://eseo.fr/images/2018/logo-eseo-couleur-v2.png">
</center>

# Projet Android Avancés 📱

###### tags: `Projet(fini)`

> *Dernière mise à jour le Jeudi 17 Juin 2021*

Dans le cadre de notre **cursus d'ingénieur ESEO**, nous avons du développer une application Android au cours de la matière **Développement des applications client / serveur sous Android**.

> Développeurs : `Jérémy SELO et Mehdi RECHID`

## :memo: Application Client / Serveur

## Captures d'écran
      
      
<p float="left">
  <img src="/ScreenLoginMenu.png" width="200" />
  <img src="/ScreenMainMenu.png" width="200" /> 
</p>


## Serveur

Nous utilisons comme système d'hébergement et de déploiement d'un serveur, la plateforme d'application nommé Heroku.
<img  width="70" 
      height="25"
      src="/Heroku_logo.png"
      style="float: left; margin-right: 1px;" />

## Cahier des charges

:warning: La partie *Cahier des charges* est un copier coller du [sujet du projet](https://cours.brosseau.ovh/tp/android/app-avance-android.html) de **Valentin Brosseau**. :warning:

### :pushpin: Objectifs

L'objectif de cette application va être la mise en pratique de la communication Client et Serveur d'une application, mais également les à côté de celle-ci (Serveur, Base de données, etc.). Cette application couvrira l'ensemble des compétences vu ensemble :

- La conception (et l'organisation d'une application, package, MVVM, Fragment si nécessaire)
- La communication via le réseau
- La modélisation d'un projet « mobile ».
- Base de données
  - Authentification d'un client » (au minimum reconnaissance de celui-ci)
  - La qualité de code (indentation, organisation).
- La mise en place de commentaires (pour faciliter la compréhension).
- L'aspect « propre » des différents écrans de votre application.

### :pushpin: Introduction

- Créer une application qui sera constituée de deux types de clients :

  - Les clients qui affichent des informations.
  - Les clients qui collectent de l'information.

- Construire un « serveur », ce serveur aura pour but :

  - Identifier les clients (via un ID, ou un UUID, ou un TOKEN).
  - La création du client sera faite via une vue de l'application. Celui-ci devra fournir « Son ID / UUID / TOKEN » et également un nom.
  - Les clients seront identifiés lors des appels réseau par l'UI / UUID ou Token on fonction du choix.
  - Stocker les informations collectées par « les clients » (avec historiques). **L'information doit être horodatée**.

### :pushpin: L'application

L'application de collecte et d'affichage est unique. Celle-ci est découpée en deux parties :

- La collecte.
- L'affichage des données d'un client précis (via saisie / flashage de son UUID).

### :pushpin: Informations collectées

- [x] La collecte devra être faite à interval régulier (~60secondes). 

Celle-ci a pour but de collecter un maximum d'informations depuis les capteurs du smartphone :

- [x] La luminosité ambiante.
- [x] Le niveau de batterie.
- [x] La pression.
- [x] La température.
- [x] La position GPS / Réseau du téléphone (Lat, Long).

### :pushpin: Affichage client

L'affichage des informations sera réalisé dans un autre écran de l'application. Celui-ci devra afficher :

- [x] Les données d'un client choisi (via saisie ou flash QRCode du code)
- [ ] L'affichage des données du client devra indiquer l'horodatage de la dernière collecte.
- [x] Un bouton d'actualisation.
- [x] Possibilités d'affichage (implémentation au choix) :
- Via une Recyclerview simple (mais avec des noms des capteurs dans la langue du client).
- Via un Layout dédié avec des icônes en fonction du type de capteurs (une carte OpenStreetMap peut-être utilisée pour la position GPS).
- [ ] Bonus : la vue peut-être découpée en deux tabs afin d'avoir une vue listant l'ensemble des collecteurs de données connues par le serveur. Chaque ligne contiendra une action permettant d'afficher un « client précis » (donc sans connaitre son code préalablement).
- [x] Bonus : affichage des données historiques d'un client.

### :pushpin: Le serveur

Le serveur est à implémenter dans le langage de votre choix. Celui-ci doit être capable de :

- [x] Stocker l'information dans une base de données.
- [x] Créer un client.
- [x] Récupérer les dernières données d'un client via son UUID / ID / TOKEN.
- [x] Bonus : liste de l'ensemble des clients présents en base de données.
- [x] Bonus : obtention des données historiques d'un client via son UUID / ID / TOKEN.
