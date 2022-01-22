#pragma once

#include "Event.h"
#include "MinuteHand.h"
#include "SecondHand.h"

#include <iostream>
#include <ostream>
#include <vector>

class Clock {
public:
    void tick() {
        _sec_hand.advance();
        for (size_t i = 0; i < _events.size(); i++) {
            if (_events[i].notify_time(_min_hand.get_minutes(), _sec_hand.get_seconds())) {
                _events.erase(_events.begin() + i);
            }
        }
    }
    std::vector<Event> get_events() { return _events; }
    void               add_event(const Event& event) { _events.emplace_back(event); }
    MinuteHand         get_minute_hand() const { return _min_hand; }
    SecondHand         get_second_hand() const { return _sec_hand; }

private:
    MinuteHand         _min_hand;
    SecondHand         _sec_hand { _min_hand };
    std::vector<Event> _events;
};

std::ostream& operator<<(std::ostream& stream, const Clock& clock);
