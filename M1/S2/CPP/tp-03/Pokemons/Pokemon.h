#pragma once

#include <iostream>
#include <string.h>

// A creature that is cute and can fight other ones.
class Pokemon {
public:
    Pokemon(const std::string& name)
        : _name { name } {}

    std::string name() const { return _name; }

private:
    std::string _name;
};
