#ifndef DIVIDED_DIFFERENCE_H
#define DIVIDED_DIFFERENCE_H

#include "polynomial_iterpolation.h"

class DividedDifference : public PolynomialIterpolation {
private:
    void interpolate();
public:
    DividedDifference(std::string fileName = "input.txt");
};
#endif // DIVIDED_DIFFERENCE_H
