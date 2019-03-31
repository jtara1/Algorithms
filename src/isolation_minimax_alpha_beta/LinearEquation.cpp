#include <limits>
#include "headers/LinearEquation.h"

LinearEquation::LinearEquation(float x1, float y1, float x2, float y2) {
    float delta_x = x2 - x1;

    if (delta_x == 0.0) slope = std::numeric_limits<float>::infinity();
    else slope = (y2 - y1) / delta_x;

    this->x_intercept = x1;
    this->y_intercept = y1;
}

bool LinearEquation::IsQueenAttackPath(float x, float y) {
    bool valid_slope = slope == -1.0 || slope == 1.0 || slope == 0.0 || slope == std::numeric_limits<float>::infinity();

    if (slope == std::numeric_limits<float>::infinity()) return x == x_intercept;
    return y == Output(x);
}

/* gets points in range (f(x_intercept), f(x)] */
std::vector<std::tuple<float, float>> LinearEquation::GetPointsBetween(float x, float y) {
    std::vector<std::tuple<float, float>> points = std::vector<std::tuple<float, float>>();
    float start = x < x_intercept ? x : x_intercept;
    float end = x <= x_intercept ? x_intercept : x;

    for (float i = start + 1.0; i < end; ++i) {
        points.emplace_back(std::tuple<float, float>(i, Output(i)));
    }

    points.emplace_back(std::tuple<float, float>(x, Output(x)));

    return points;
}

float LinearEquation::Output(float x) {
    if (slope == std::numeric_limits<float>::infinity()) return x;
    return slope * (x - x_intercept) + y_intercept;
}
