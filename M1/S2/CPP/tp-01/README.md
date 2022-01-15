# TP1 - Introduction au C++

## Exercice 1 - Compilation et exécution

1. L'avantage d'un langage compilé est qu'il est plus rapide qu'un
   langage interprété à exécuter. Il est aussi directement exécuté par
   le système d'exploitation.

2.

3. L'expression "undefined behavior" signifie que si certaines règles du
   langage sont pas respecté, alors le programme n'a pas de sens.
   Oui c'est possible de compilé du code classifié UB. Ils peuvent produire
   des résultats innatendu.

## Exercice 2 - Primitives et initialisation

1.  Les lignes suivantes déclenches des erreurs de compilation :

```cpp
// On déclare s1 comme une constante mais sans initialisation.
const short s1;
// Il faut initialiser s1.
const short s1 = 5;
```

```cpp
// i1 étant initialiser à 2 on ne peut donc pas le convertir en bool.
bool b3{i1};
// Il faut intialiser b3 à 1 ou 0 directement ou i1 à 1 ou 0.
bool b3{0};
bool b3{1};
```

```cpp
// c8 étant de type char on ne peut pas l'initialiser avec un const char*.
char c8{"a"};
// Il faut l'initialiser avec un char simple cote.
char c8{'a'};
```

```cpp
// Une variable déclaré comme référence doit faire référence à une autre
// variable.
double& d12;
// On doit l'initialiser avec une variable du même type que d12.
double& d12 = d10;
```

```cpp
// On ne peut pas assigné à une variable déclaré comme référence une valeur
// constante.
int& i15 = i1;
// Il faut initialiser i15 avec i14 par exemple.
int& i15 = i14;
```

```cpp
// La variable assignée doit être de même type que i16.
int& i16 = b2;
// Il faut lui assigné une variable de type int.
int& i16 = i14;
```

2. **s0** = Undefined Behaviour,
   **ul7** = 65542

## Exercice 3 - Les fonctions et leurs paramètres

1.

```cpp
#include <iostream>

int add(int a, int b) {
  return a + b;
}

void add_to(int& a, int b) {
  a += b;
}

int main() {
  const int x{10};
  int y = add(x, x);
  add_to(y, 22);
  std::cout << y << std::endl;
  return 0;
}
```

2. Le passage par valeur permet de modifier les variables que dans la
   fonction ou ces variables sont passé en paramètres, elle est utilisé
   lorsque l'on a besoin de la valeur de la variable dans une fonction
   mais sans modifier sa valeur en dehors de la fonction.
   Le passage par référence permet de modifier la valeur de la variable
   dans la fonction passé en paramètre, elle est donc utilisé lorsque l'on
   veut garder les modifications faites à une variable dans une fonction.
   Le passage par référence constante permet de passer en paramètre d'une fonction une variable que l'on ne veut pas modifier mais dont on veut utiliser ce qu'elle contient par exemple pour un vector cela permet de ne pas réallouer de la mémoire.

3.

```cpp
int count_a_occurrences(const std::string& s);
void update_loop(const float& dt, const std::string& errors_out);
bool are_all_positives(const std::vector<int>& values, int& negative_indices_out[], size_t& negative_count_out);
std::string concatenate(const char& str1, const char& str2);
```

## Exercice 4 - std::string et std::vector

1.

```cpp
#include <iostream>
#include <vector>

using namespace std;

vector<unsigned int> count_lower(const string& str) {
    vector<unsigned int> vect;

    for (int c = 'a'; c <= 'z'; c++) {
        unsigned int counter = 0;
        for (char letter : str) {
            if (letter == c) {
                counter++;
            }
        }
        vect.emplace_back(counter);
    }

    return vect;
}

void display_lower_occ(const vector<unsigned int>& vect) {
    for (int i = 0; i < 26; i++) {
        char c = i + 97;
        cout << c << " : " << vect[i] << endl;
    }
}

int main() {
    string str;

    cin >> str;

    vector<unsigned int> vect = count_lower(str);

    display_lower_occ(vect);

    return 0;
}
```

2.

```cpp
int main() {
    string         str;
    vector<string> strings;

    while (true) {
        cin >> str;
        if (str.compare("QUIT") != 0) {
            strings.emplace_back(str);
        } else {
            break;
        }
    }
    str = "";

    for (string value : strings) {
        str += value;
    }

    vector<unsigned int> vect = count_lower(str);

    display_lower_occ(vect);

    cout << str << endl;

    return 0;
}
```

## Exercice 5 - Traducteur malade

1.
