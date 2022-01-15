#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main(int argc, char** argv) {
    int         array_1[] = { 0, 1, 2, 3 };
    int         array_2[50];
    vector<int> vect;
    int         length = 0;

    for (int i = 0; i < 4; ++i) {
        cout << array_1[i] << std::endl;
    }

    for (int value : array_1) {
        cout << value << std::endl;
    }

    for (int i = 1; i <= 50; i++) {
        array_2[i] = i;
    }

    for (int value : array_2) {
        cout << value << std::endl;
    }

    if (argc != 2) {
        cerr << "Array length is not specified" << endl;
    }

    length = stoi(argv[1]);
    if (length <= 0) {
        cerr << "Array length need to be >= 0" << endl;
    }

    cout << "array length is : " << argv[1] << endl;

    for (int i = 0; i < 5; i++) {
        vect.emplace_back(i);
    }

    for (int value : vect) {
        cout << value << endl;
    }

    return 0;
}
