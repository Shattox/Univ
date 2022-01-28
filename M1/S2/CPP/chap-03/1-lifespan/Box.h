#pragma once

#include <iostream>
#include <string>

using namespace std;

struct Content {
    Content(const std::string& name)
        : name { name } {
        cout << "Content created" << endl;
    }

    ~Content() { cout << "Content destroyed" << endl; }

    std::string name;
};

class Box {
public:
    Box(const std::string& name)
        : _content { name } {
        cout << "Box created" << endl;
    }

    ~Box() { cout << "Box destroyed" << endl; }

    Content& get_content() { return _content; }

private:
    Content _content;
};
