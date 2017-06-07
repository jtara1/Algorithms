#ifndef DIVIDED_DIFFERENCE_H
#define DIVIDED_DIFFERENCE_H

#include <vector>

#include "polynomial_iterpolation.h"
#include "polynomial.h"

class DividedDifference : public PolynomialIterpolation {
private:
    /// coefficients generated from the interpolation needed to form the new polynomial
    std::vector<RationalNumber> coefficients;

    int tableFormatWidth;

    /// a vector containing the columns (vectors of the values at each row)
    std::vector<std::vector<RationalNumber>> tableValues;

    /// the polynomial that's interpolated
    Polynomial simplifiedPolynomial;

    void interpolate();

    void printNewtonsForm();

    /// build the simplified
    void printLagrangesFormAndBuildSimplePolynomial();

    /// print header for divided diff table
    void printHeader();

    /// returns true if tableValues[i][j] does not throw out of bounds err
    bool inRange(int i, int j) const;

public:
    DividedDifference(std::string fileName = "input.txt");

    void printTable();
};
#endif // DIVIDED_DIFFERENCE_H
