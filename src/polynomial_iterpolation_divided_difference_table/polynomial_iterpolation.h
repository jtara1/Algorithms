#ifndef POLYNOMIAL_ITERPOLATION_H
#define POLYNOMIAL_ITERPOLATION_H

#include <vector>
#include <string>
#include "rational_number.h"

class PolynomialIterpolation {
protected:
    std::vector<RationalNumber> xValues;
    std::vector<RationalNumber> yValues;

    /**
     * Reads the first line separated with each number
     * separated by spaces as x-values
     * and the second line contains the y-values.
     * Each number needs to be an integer or ratio of numbs (e.g.: 43/22)
     */
    void loadDataFromFile(std::string);

public:
    PolynomialIterpolation();
};
#endif // POLYNOMIAL_ITERPOLATION_H
