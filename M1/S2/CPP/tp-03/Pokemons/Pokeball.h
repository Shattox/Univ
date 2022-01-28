#pragma once

#include "Pokemon.h"

#include <memory>
#include <vector>

// A ball where Pokemon sleep.
class Pokeball {
public:
    bool empty() { return _pokeball == nullptr; }
    void store(const std::unique_ptr<Pokemon>& pokemon) { _pokeball = std::make_unique<Pokemon>(*pokemon); }
    Pokemon& pokemon() const { return *_pokeball; }

private:
    std::unique_ptr<Pokemon> _pokeball;
};