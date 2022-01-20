// Implement Rectangle functions here.
#include "Rectangle.h"

float Rectangle::_default_size = 0.f;

Rectangle::Rectangle(float length, float width)
    : _length { length }
    , _width { width } {
}

Rectangle::Rectangle(float size)
    : Rectangle { size, size } {
}

Rectangle::Rectangle()
    : Rectangle { _default_size } {
}

void Rectangle::set_default_size(float size) {
    _default_size = size;
}

void Rectangle::scale(float ratio) {
    _length *= ratio;
    _width *= ratio;
}

std::ostream& operator<<(std::ostream& stream, const Rectangle& rect) {
    return stream << "{ L: " << rect.get_length() << ", W: " << rect.get_width() << " }";
}