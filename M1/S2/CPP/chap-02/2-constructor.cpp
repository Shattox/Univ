#include <iostream>
#include <string>

using namespace std;

class Person {
private:
    const string _name;
    const string _surname;
    unsigned int _age = 0u;

public:
    Person(const string& name, const string& surname)
        : _name { name }
        , _surname { surname } {}

    Person() {}

    void wait(unsigned int year) { _age += year; }

    string get_full_name() const { return _name + " " + _surname; }

    int get_age() const { return _age; }
};

int main() {
    Person batman { "Batman", "Wayne" };
    Person unknow;

    batman.wait(23);

    cout << "Person named '" << batman.get_full_name() << "' is " << batman.get_age() << " years old."
         << endl;

    return 0;
}
