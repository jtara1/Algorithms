#ifndef RATIONAL_NUMBER_H
#define RATIONAL_NUMBER_H

#include <ostream>

class RationalNumber {
private:
    /// numerator
    int num;

    /// denominator
    int den;

    /**
     * Mutate this RationalNumber to simplify
     * and return the simplified version
     */
    RationalNumber simplify();

public:
    RationalNumber();
    RationalNumber(int, int);

    RationalNumber operator+(const RationalNumber &);
    RationalNumber operator-(const RationalNumber &);
    RationalNumber operator*(const RationalNumber &);
    RationalNumber operator=(const RationalNumber &);
    friend std::ostream& operator<<(std::ostream &, const RationalNumber &);
};
#endif // RATIONAL_NUMBER_H
