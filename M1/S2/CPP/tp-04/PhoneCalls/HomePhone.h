#pragma once

#include "Person.h"
#include "Phone.h"

class HomePhone : public Phone
{
public:
    HomePhone(const Person& person, int position)
        : Phone { person }
        , _position { position }
    {}

    void ring() const override
    {
        if (_position != _owner.get_position())
        {
            std::cout << "This is the voicemail of " << _owner.get_name() << " Please leave a message."
                      << std::endl;
        }
        else
        {
            _owner.answer_phone();
        }
    }

private:
    int _position = 0;
};
