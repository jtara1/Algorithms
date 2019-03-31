#ifndef TEST_BUILD_LINEAREQUATION_H
#define TEST_BUILD_LINEAREQUATION_H

#include <tuple>
#include <vector>

class LinearEquation {
public:
    LinearEquation(float x1, float y1, float x2, float y2);

    float Output(float x);

    bool IsQueenAttackPath(float x, float y);

    std::vector<std::tuple<float, float>> GetPointsBetween(float x, float y);

protected:
    float slope;
    float x_intercept;
    float y_intercept;
    bool is_vertical = false;
};

#endif //TEST_BUILD_LINEAREQUATION_H
