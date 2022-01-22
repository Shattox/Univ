#pragma once

#include <iostream>
#include <vector>

using namespace std;

class Histogram {
public:
    Histogram()
        : _count(26) {}

    /* Histogram(const Histogram& other)
        : _count { other._count } {} */

    void analyze(const string&); // analyse la string passée en paramètre
    void print() const;          // affiche l'histogramme

private:
    // tableau contenant le nombre d'occurrences de chaque lettre entre 'a' et 'z'
    vector<unsigned int> _count;
};