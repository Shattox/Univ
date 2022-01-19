#pragma once

#include <utility>
#include <vector>

using Vertex = std::pair<int, int>;

class Polygon {
private:
    std::vector<Vertex> _vertices;
public:
    void add_vertex(int x, int y);
};