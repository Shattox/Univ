#include "Cat.h"

#include <memory>

using namespace std;

int main() {
    unique_ptr<Cat> cat = make_unique<Cat>("Felix");

    cout << *cat << endl;

    unique_ptr<Cat> cat2;

    if (cat2 == nullptr) {
        cout << "Cat 2 is nullptr" << endl;
    }

    cat2 = make_unique<Cat>("Felinx");

    std::cout << "cat == cat2 ? " << (cat == cat2) << std::endl;
    std::cout << "*cat == *cat2 ? " << (*cat == *cat2) << std::endl;

    cat.reset();
    if (cat == nullptr) {
        std::cout << "cat is nullptr." << std::endl;
    }

    return 0;
}