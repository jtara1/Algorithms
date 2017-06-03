#ifndef POLYNOMIAL_ITERPOLATION_H
#define POLYNOMIAL_ITERPOLATION_H

#include <vector>
#include <string>
#include "rational_number.h"

class PolynomialIterpolation {
protected:
    std::vector<RationalNumber> xValues;
    std::vector<RationalNumber> yValues;
    void loadDataFromFile(std::string);

public:
    PolynomialIterpolation();

};
#endif // POLYNOMIAL_ITERPOLATION_H
