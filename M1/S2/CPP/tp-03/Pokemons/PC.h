#pragma once

#include "Pokemon.h"

#include <memory>
#include <vector>

// A PC is the place where Pokemons get automagically sent when the trainer's pockets are full.
// When a Pokemon is transferred to the PC, this one takes ownership.
class PC {
public:
    std::vector<std::unique_ptr<Pokemon>>& pokemons();
    void                                   receive(const std::unique_ptr<Pokemon>& pokemon);

private:
    std::vector<std::unique_ptr<Pokemon>> _pc;
};
