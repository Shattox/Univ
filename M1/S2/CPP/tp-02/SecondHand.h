#pragma once

#include "MinuteHand.h"

#include <iostream>

class SecondHand {
public:
    SecondHand(MinuteHand& other)
        : _min_hand { other } {}

    int  get_seconds() const { return _seconds; }
    void advance() {
        _seconds++;
        if (_seconds == 60) {
            _seconds = 0;
            _min_hand.advance();
        }
    }

private:
    int         _seconds = 0;
    MinuteHand& _min_hand;
};