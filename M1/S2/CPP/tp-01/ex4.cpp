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