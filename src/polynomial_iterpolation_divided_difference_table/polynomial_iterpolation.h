#ifndef POLYNOMIAL_ITERPOLATION_H
#define POLYNOMIAL_ITERPOLATION_H

#include <vector>

virtual class PolynomialIterpolation {
protected:
    std::vector<double> xValues;
    std::vector<double> yValues;
    void loadDataFromFile(std::string);

public:
    PolynomialIterpolation();

};
#endif // POLYNOMIAL_ITERPOLATION_H
