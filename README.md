# Projet RYGOLMIC
Il s'agit d'un projet étudiant sur la topologie discrète.
Quelques algorithmes sont implémentés pour manipuler des points colorés.

## Modèles utilisés

### 2D simplifiée
Basé sur les 12 premières pages de [Survey: Digital topology: Introduction and survey][] par Kong, T.Y.  
Les points sont répartis dans un grille avec des coordonnées entières.
Un point est soit noir, soit blanc. Il sont reliés par des adjacences.

[Survey: Digital topology: Introduction and survey]: http://reimsscd.ent.sirsidynix.net.uk/client/fr_FR/default/search/results?qu=&qu=TITLE%3DSurvey%3A+Digital+topology%3A+Introduction+and+survey+&te=1168557465

Fonctionnalitées implémentés :
- [x] point noir ou blanc
  - [x] filtre de couleur
- [x] fond blanc de l'image
- [x] adjacence (4 ou 8)
  - [x] m-adjacence
  - [x] n-adjacence
- [x] voisins
  - [x] voisins selon l'adjacence
  - [x] N(p) : 8-voisins
- [ ] points n-connectés
  - [ ] déterminer si des points donnés sont n-connectés
- [ ] chemin
  - [x] déterminer si un chemin est possible
  - [ ] reconnaitre un chemin
- [x] composants
  - [x] dans la matrice
  - [x] dans une liste de points
- [x] arc
  - [x] reconnaitre si des points donnés forment un arc
- [x] courbe
  - [x] reconnaitre si des points donnés forment une courbe
- [x] point isolé
- [ ] bordures
  - [ ] liste : points sur la bordure entre deux composants
  - [x] point de bordure
  - [ ] point intérieur
- [ ] trou
- [ ] point simple
- [ ] thinning
- [ ] shrinking

## Interface graphique
Pour afficher la matrice de points, la bibliothèque graphique `info.graphics` est utilisée.  
Une migration vers JavaFX est prévue.
