#include "PC.h"

std::vector<std::unique_ptr<Pokemon>>& PC::pokemons() {
    return _pc;
}

void PC::receive(const std::unique_ptr<Pokemon>& pokemon) {
    _pc.emplace_back(std::make_unique<Pokemon>(*pokemon));
}