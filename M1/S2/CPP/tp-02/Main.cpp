#include "Clock.h"
#include "MinuteHand.h"
#include "Parsing.h"
#include "SecondHand.h"

#include <iostream>
#include <ostream>

using namespace std;

ostream& operator<<(ostream& stream, const Clock& clock) {
    return stream << clock.get_minute_hand().get_minutes() << " min " << clock.get_second_hand().get_seconds()
                  << " sec";
}
