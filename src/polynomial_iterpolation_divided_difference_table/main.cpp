#include <iostream>

#include "polynomial.h"
#include "rational_number.h"
#include "divided_difference.h"
#include "unit_test.h"


using namespace std;

/**
 * The input should be a file containing one row of x-values
 * and a second row of y-values
 * Each value needs to be an integer or ratio (e.g.: -3, 4/22, etc.)
 */
int main() {
    // the data is loaded from the file, polynomial is interpolated, & output printed

    // sample input provided by Professor
    DividedDifference divDiff;
    // 5 random value pairs pre-generated
    DividedDifference divDiff2("input (1).txt");
    // 10 pairs incrementing
    DividedDifference divDiff3("input (2).txt");
    // 50 pairs pre-generated from quadratic function y = x^2
    DividedDifference divDiff4("input2.txt");
    // 50 pairs pre-generated from polynomial y = x^6
    DividedDifference divDiff5("input2 (1).txt");

//    UnitTest::testRationalNumber();
//    UnitTest::testPolynomial();
    return 0;
}
