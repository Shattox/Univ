#pragma once

#include "MinuteHand.h"
#include "SecondHand.h"

#include <iostream>

class Clock {
public:
    void tick() { _sec_hand.advance(); }

private:
    MinuteHand _min_hand;
    SecondHand _sec_hand { _min_hand };
};