#include <iostream>
#include <string>

using namespace std;

class Person {
private:
    string _name;
    int    _age;

public:
    void set_name(const string& name) { _name = name; }

    void set_age(const int& age) { _age = age; }

    string get_name() { return _name; }

    int get_age() { return _age; }
};

int main() {
    Person p;

    p.set_name("Batman");
    p.set_age(23);

    cout << "Person named '" << p.get_name() << "' is " << p.get_age() << " years old." << endl;

    return 0;
}
