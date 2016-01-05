## Pour importer le projet, voici la démarche à suivre :

* Créer un répertoire BlogWS2015 (! important respecter cette convention de nommage !)
* Cloner le contenu du git dans ce répertoire
* Créer un répertoire dans lequel les images envoyées seront stockées
* Importer le projet dans netbeans (version 8.0.2 / glassfish 4.1)
* Supprimez la persitence unit présente et créez une nouvelle data source
* Renommez votre fichier glassfish-resources.xml (dans le répertoire Server Ressources) en sun-resources.xml
* Modifiez la valeur de la constante SERVER_UPLOAD_LOCATION_FOLDER pour la faire pointer sur le répertoire de stockage des images créé précedemment
* Run it

It works !

## Fonctionnalités du projet :

* Création d'un article avec :
  - Un titre (required)
  - Un contenu (required)
  - Import d'image : 
    * avec une zone de drag'n drop d'image avec changement d'apparence au survol & dépôt
    * avec un input de type file (fa-icon paysage)
    * gestion des doublons
  - Prévisualisation des images envoyées
  - Affichage d'une map avec géolocalisation (marqueur emplacement courant)
  
* Modification d'un article
* Suppression d'un article
