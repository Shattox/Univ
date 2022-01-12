# TP1 : Prise en main d’Unitex

## Exercice 1. Prétraitement du texte

### 1.1

On observe que pour un merge,

### 1.2

L'automate du texte ne contient pas de boucle. Il est donc acyclique.

### 1.3

## Exercice 2. Recherche de motifs : expressions régulières

### 2.1

| Requête |       Résultat        |
| ------: | :-------------------: |
|    être |         être          |
|  <être> | suis, était, été, fut |

On observe qu'avec les masques lexicaux, on obtient toutes les
occurences du mot.
Tandis qu'avec un token le résultat obtenu correspond seulement
au token recherché.

### 2.2

|    Requête |           Résultat           |                               Description                               |
| ---------: | :--------------------------: | :---------------------------------------------------------------------: |
|       <A\> | à cheval, à l'heure, à l'eau |                           Tous les adjectifs                            |
|    pouvoir |           pouvoir            |               Toutes les occurrences de la forme pouvoir                |
|     savons |            savons            |                toutes les occurrences de la forme savons                |
| <pouvoir\> |     pût, pouvait, pu...      | Toutes les formes fléchies dont la forme canonique est le verbe pouvoir |

## Exercice 3. Recherche de motifs : grammaires locales

## 3.1

## 3.2
