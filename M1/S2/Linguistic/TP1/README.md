# TP1 : Prise en main d’Unitex

## Exercice 1. Prétraitement du texte

### 1.1

On observe que pour le graphe **replace**, il contient pour
chaque noeud des sous-graphes permettant de résoudre certaines
contractions ou élisions.
Pour le graphe **sentence** on remarque qu'il gère plus
tout ce que est ponctuation.

### 1.2

L'automate du texte ne contient pas de boucle. Il est donc acyclique.

## Exercice 2. Recherche de motifs : expressions régulières

### 2.1

|  Requête | Résultat |                       Description                        |
| -------: | :------: | :------------------------------------------------------: |
|   <!DIC> |  4 233   | Les mots ne figurant pas dans les dictionnaires du texte |
| <TOKEN\> |  95 315  |         Unités lexicales trouvées dans le texte          |
| <UPPER\> |  1 028   |              Unités lexicales en majuscule               |
|     <.V> |  17 588  |         Les entrées ayant le code grammatical V          |

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

Avec l'expression <NB\> ou <NB\><NB\>\* on remarque que tous les
nombres du texte ne sont pas seléctionné.

## 3.2

![](horaire.png)
