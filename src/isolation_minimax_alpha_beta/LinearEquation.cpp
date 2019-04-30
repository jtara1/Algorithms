#include <limits>
#include <math.h>
#include "headers/LinearEquation.h"

LinearEquation::LinearEquation(int x1, int y1, int x2, int y2) {
    int delta_x = x2 - x1;

    float slope = 1;

    if (delta_x == 0) this->is_vertical = true;
    else slope = (y2 - y1) / (float)delta_x;

    this->slope = truncf(slope);
    this->x_intercept = x1;
    this->y_intercept = y1;
}

/* is this eqn a queen attack path, and is it on this particular path */
bool LinearEquation::IsQueenAttackPath(int x, int y) {
    bool valid_slope = slope == -1.0 || slope == 1.0 || slope == 0.0 || (is_vertical && x == x_intercept);
    if (!valid_slope) return false;

    if (is_vertical) return x == x_intercept;
    return y == Output(x);
}

/* gets points in range (f(x_intercept), f(x)] */
std::vector<std::tuple<int, int>> LinearEquation::GetPointsBetween(int x, int y) {
    std::vector<std::tuple<int, int>> points = std::vector<std::tuple<int, int>>();
    int start = x < x_intercept ? x : x_intercept;
    int end = x <= x_intercept ? x_intercept : x;

    if (is_vertical) {
        start = y < y_intercept ? y : y_intercept;
        end = y <= y_intercept ? y_intercept : y;

        for (int i = start + 1; i < end; ++i) {
            auto coords = std::tuple<int, int>(Output(i), i);
            points.emplace_back(coords);
//            points.emplace_back(std::tuple<int, int>(i, Output(i)));
        }

        auto coords = std::tuple<int, int>(Output(y), y);
        points.emplace_back(coords);
//        points.emplace_back(std::tuple<int, int>(y, Output(y)));
    } else {
        for (int i = start + 1; i < end; ++i) {
            points.emplace_back(std::tuple<int, int>(i, Output(i)));
        }

        points.emplace_back(std::tuple<int, int>(x, Output(x)));
    }

    return points;
}

int LinearEquation::Output(int x) {
    if (is_vertical) return x_intercept;
    return (int)slope * (x - x_intercept) + y_intercept;
}
