# TP2 : Optimisation minisat

## Chargé de TD : Thapper Johan
## Auteur : Steve Chen / Nicolas Van Heusden

## **1 Prise en main deminisat**

### Exercice 1
On obtient le même résultat que celui donnée en cours : Paulette.
```
SAT
-1 2 -3 0
```

## **2 Sudoku**

### Guide utilisateur

Pour lancer le solveur il faudra lancer le programme **sudoku.py**
suivis du fichier **.txt** correspondant au sudoku à résoudre comme ceci :
```
python3 sudoku.py grid4x4-1.txt
```
Une fois cette commande lancer vous verrez appaître la solution de la maniètre suivante :
```
1 3 2 4
2 4 3 1
3 1 4 2
4 2 1 3
```

### Discussion du problème

Il y a N^3 variables ou N est le taille du sudoku et 3 car on a N lignes, N colonnes et N chiffres.

Le nombre de clauses correspond à la somme des clauses pour :
- la contrainte que pour chaque case (i, j) il y a exactement 1 chiffre k tel que xijk est vraie.
- la contrainte pour chaque lignes du sudoku il y est exactement une fois le même chiffre.
- la contrainte pour chaque colonnes du sudoku il y est exactement une fois le même chiffre.
- la contrainte pour chaque sous-grilles du sudoku il y est exactement une fois le même chiffre.

Le programme a été testé sur toutes les grilles fournis et retourne une solution correcte pour toutes les grilles.

Le programme a aussi été testé sur une grille 4x4 non satisfaisable de base le programme renvoie bien **UNSATISFASABLE**.

- generation clauses :  0.5422768592834473 secondes.

- Pour le temps de résolution le temps varie de
0 secondes pour une grille de 4x4 à 0.078125 secondes pour une grille de 25x25.

Tous est fonctionnelle générations clauses, solveur.
On a appris comment fonctionne un solveur pour résoudre les sudoku.