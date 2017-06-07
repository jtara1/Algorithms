#ifndef POLYNOMIAL_H
#define POLYNOMIAL_H

#include <ostream>
#include "rational_number.h"

using namespace std;

class Polynomial {
private:
    RationalNumber *coefficients = nullptr;
    int *exponents = nullptr;
    int terms;

    /// parse a string containing a polynomial
    void parseString(string poly);

    /// parse an individual polynomial term
    void parseTerm(string term, int termIndex);

    /// mutates the arg str by replacing spaces with the empty string " " -> ""
    void removeSpaces(string &str);

public:
    Polynomial();
    Polynomial(int terms);
    Polynomial(string poly);
    Polynomial(const Polynomial &poly);
    ~Polynomial();

    void print() const;

    Polynomial operator+(const Polynomial &op);
    Polynomial operator-(const Polynomial &op);
    Polynomial operator*(const Polynomial &op);
    Polynomial operator*(const int &op);
    Polynomial operator*(const RationalNumber &op);
    Polynomial& operator=(const Polynomial &op);
    friend ostream& operator<<(ostream &os, const Polynomial &op);
};
#endif
