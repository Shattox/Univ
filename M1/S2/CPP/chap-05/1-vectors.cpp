#include <array>
#include <iostream>
#include <vector>

int main() {
    /*int array[] = { 0, 1, 2, 3, 4, 5 };

    std::vector<int> part(&array[2], &array[5]);
    for (const auto v : part) {
        std::cout << v << std::endl;
    }

    std::vector<int> full(array, array + 6);
    for (const auto v : full) {
        std::cout << v << std::endl;
    }*/

    /*for (auto value : array) {
        std::cout << value << std::endl;
    }*/

    std::vector<int> tab(100,2);  //Un tableau de 100 entiers valant 2

    for (auto it = tab.begin(); it != tab.end(); it++) {
        int value = *it;
        std::cout << value << std::endl;
    }
    return 0;
}