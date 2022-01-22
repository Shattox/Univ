#include "Histogram.h"

/* void Histogram::analyze(const string& str) {
} */

void Histogram::print() const {
    for (int value : this->_count) {
        cout << value << endl;
    }
}

int main() {
    Histogram h;

    h.print();

    return 0;
}
