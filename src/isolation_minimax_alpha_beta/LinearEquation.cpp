#include <limits>
#include "headers/LinearEquation.h"

LinearEquation::LinearEquation(float x1, float y1, float x2, float y2) {
    float delta_x = x2 - x1;

    if (delta_x == 0.0) {
        this->is_vertical = true;
        slope = std::numeric_limits<float>::infinity();
    }
    else slope = (y2 - y1) / delta_x;

    this->x_intercept = x1;
    this->y_intercept = y1;
}

bool LinearEquation::IsQueenAttackPath(float x, float y) {
    bool valid_slope = slope == -1 || slope == 1 || slope == 0 || (is_vertical && x == x_intercept);
    if (!valid_slope) return false;

    if (is_vertical) return x == x_intercept;
    return y == Output(x);
}

/* gets points in range (f(x_intercept), f(x)] */
std::vector<std::tuple<float, float>> LinearEquation::GetPointsBetween(float x, float y) {
    std::vector<std::tuple<float, float>> points = std::vector<std::tuple<float, float>>();
    float start = x < x_intercept ? x : x_intercept;
    float end = x <= x_intercept ? x_intercept : x;

    if (is_vertical) {
        start = y < y_intercept ? y : y_intercept;
        end = y <= y_intercept ? y_intercept : y;

        for (float i = start + 1.0; i < end; ++i) {
            points.push_back(std::tuple<float, float>(Output(i), i));
        }

        points.push_back(std::tuple<float, float>(Output(y), y));
    } else {
        for (float i = start + 1.0; i < end; ++i) {
            points.push_back(std::tuple<float, float>(i, Output(i)));
        }

        points.push_back(std::tuple<float, float>(x, Output(x)));
    }

    return points;
}

float LinearEquation::Output(float x) {
    if (is_vertical) return x_intercept;
    return slope * (x - x_intercept) + y_intercept;
}
