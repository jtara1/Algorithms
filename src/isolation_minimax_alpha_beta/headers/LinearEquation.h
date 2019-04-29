#ifndef TEST_BUILD_LINEAREQUATION_H
#define TEST_BUILD_LINEAREQUATION_H

#include <tuple>
#include <vector>

class LinearEquation {
public:
    LinearEquation(int x1, int y1, int x2, int y2);

    int Output(int x);

    bool IsQueenAttackPath(int x, int y);

    std::vector<std::tuple<int, int>> GetPointsBetween(int x, int y);

protected:
    float slope;
    int x_intercept;
    int y_intercept;
    bool is_vertical = false;
};

#endif //TEST_BUILD_LINEAREQUATION_H
