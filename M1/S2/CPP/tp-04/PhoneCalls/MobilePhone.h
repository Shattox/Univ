#pragma once

#include "Person.h"
#include "Phone.h"

class MobilePhone : public Phone
{
public:
    MobilePhone(const Person& person)
        : Phone { person }
    {}

    void ring() const override {}

private:
};