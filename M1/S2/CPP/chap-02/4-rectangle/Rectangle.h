// ask compiler to not copy in the .cpp file if it's already copied.
#pragma once
#include <iostream>
#include <ostream>

class Rectangle {
public:
    Rectangle(float length, float width);
    Rectangle(float size);
    Rectangle();

    static void set_default_size(float size);

    float       get_length() const { return _length; }
    float       get_width() const { return _width; }
    static void speak();

    void scale(float ratio);

private:
    static float _default_size;
    float        _length;
    float        _width;
};

std::ostream& operator<<(std::ostream& stream, const Rectangle& rect);
