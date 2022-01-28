#pragma once

#include "PC.h"
#include "Pokeball.h"

#include <vector>

// A person that capture Pokemons and make them fight.
class Trainer {
public:
    Trainer(const std::string& name, const PC& pc)
        : _name { name }
        , _pc { pc } {}
    std::string name() const { return _name; }

    /* std::vector<Pokeball&> pokeballs() { return _pokeballs; } */

private:
    std::string             _name;
    const PC&               _pc;
    std::array<Pokeball, 6> _pokeballs;
};
