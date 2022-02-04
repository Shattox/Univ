#pragma once

#include "PC.h"
#include "Pokeball.h"
#include <memory>
#include <array>

// A person that capture Pokemons and make them fight.
class Trainer {
public:
    Trainer(const std::string& name, const PC& pc)
        : _name { name }
        , _pc { pc } {}
    std::string name() const {
        return _name;
    }
    std::array<Pokeball, 6>& pokeballs() {
        return _pokeballs;
    }
    void capture(const std::unique_ptr<Pokemon>& pokemon) {
        for (size_t i = 0; i < _pokeballs.size(); i++) {
            if (_pokeballs[i].empty()) {
                _pokeballs[i].store(pokemon);
                break;
            }
        }
    }

private:
    std::string             _name;
    const PC&               _pc;
    std::array<Pokeball, 6> _pokeballs;
};
