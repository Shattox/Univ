#include <iostream>
#include <string>
#include <vector>

using namespace std;

int add(const vector<int>& values) {
    int result = 0;
    for (auto v : values) {
        result += v;
    }
    return result;
}

int multiply(const vector<int>& values) {
    auto result = 1;
    for (auto v : values) {
        result *= v;
    }
    return result;
}

int sub(const vector<int>& values) {
    auto result = values[0];
    for (size_t i = 1; i < values.size(); ++i) {
        result -= values[i];
    }
    return result;
}

void display_result(int result) {
    cout << "Result is " << result << endl;
}

int compute_result(char op, const vector<int>& values) {
    switch (op) {
    case '+':
        return add(values);
    case '*':
        return multiply(values);
    case '-':
        return sub(values);
    default:
        return 0;
    }
}

bool parse_params(char& op, vector<int>& values, int argc, char** argv) {
    if (argc < 2) {
        cerr << "Expected operator as first argument." << endl;
        return false;
    }

    string op_str = argv[1];
    if (op_str != "+" && op_str != "*" && op_str != "-") {
        cerr << "Expected operator to be '+', '*' or '-'." << endl;
        return false;
    }

    op = op_str[0];

    for (auto arg_i = 2; arg_i < argc; ++arg_i) {
        auto value = stoi(argv[arg_i]);
        values.emplace_back(value);
    }

    if (op == '-' && values.empty()) {
        cerr << "Operator '-' expects at least one operand to substract from." << endl;
        return -1;
    }

    return true;
}

int main(int argc, char** argv) {
    char        op = '?';
    vector<int> values;

    if (!parse_params(op, values, argc, argv)) {
        return -1;
    }

    auto result = compute_result(op, values);
    display_result(result);

    return 0;
}
