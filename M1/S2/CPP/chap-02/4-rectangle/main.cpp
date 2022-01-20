#include "Rectangle.h"

int main() {
    Rectangle rect(2.f, 4.f);
    std::cout << rect << std::endl;

    rect.scale(3);
    std::cout << rect << std::endl;

    /* some instruction saying that next squares's size will be 3.f */
    Rectangle::set_default_size(3.f);
    Rectangle s1;
    Rectangle s2;

    std::cout << s1 << std::endl;
    std::cout << s2 << std::endl;

    /* some instruction saying that next squares's size will now be 5.f */
    Rectangle::set_default_size(5.f);
    Rectangle s3;
    Rectangle s4;

    std::cout << s3 << std::endl;
    std::cout << s4 << std::endl;

    Rectangle::s return 0;
}
