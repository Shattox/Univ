#include "Clock.h"
#include "MinuteHand.h"
#include "SecondHand.h"

#include <iostream>
#include <ostream>

ostream& operator<<(ostream& stream, const Clock& clock) {
    stream << clock._min_hand.get_minutes() << "min" << clock._sec_hand.get_seconds() << "sec";
}

int main() {
    MinuteHand mh;
    SecondHand sh { mh };
    Clock      clock;

    /* for (int i = 0; i <= 120; i++) {
    sh.advance();
    std::cout << sh.get_seconds() << std::endl;
} */

    /* for (int i; i < 20; i++) {
        std::cout << mh.get_minutes() << std::endl;
        mh.advance();
    } */

    return 0;
}
