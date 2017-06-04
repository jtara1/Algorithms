#ifndef DIVIDED_DIFFERENCE_H
#define DIVIDED_DIFFERENCE_H

#include <vector>
#include "polynomial_iterpolation.h"

class DividedDifference : public PolynomialIterpolation {
private:
    std::vector<RationalNumber> coefficients;

    int tableFormatWidth;

    /// a vector containing the columns (vectors of the values at each row)
    std::vector<std::vector<RationalNumber>> tableValues;

    void interpolate();

    void printNewtonsForm();
    void printLagrangesForm();

    /// print header for divided diff table
    void printHeader();

    /// returns true if tableValues[i][j] does not throw out of bounds err
    bool inRange(int i, int j) const;

public:
    DividedDifference(std::string fileName = "input.txt");

    void printTable();
};
#endif // DIVIDED_DIFFERENCE_H
