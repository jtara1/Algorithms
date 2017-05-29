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

    /// the rational number representation of zero
    const static RationalNumber ZERO;

    /// flag used to toggle between printing RationalNumber in fraction or decimal form
    static bool PRINT_AS_FRACTION;

    RationalNumber operator+(const RationalNumber &);
    RationalNumber operator-(const RationalNumber &);
    RationalNumber operator*(const RationalNumber &);
    RationalNumber operator*(const int &);
    bool operator<(const RationalNumber &);
    bool operator>(const RationalNumber &);
    RationalNumber operator=(const RationalNumber &);
    friend std::ostream& operator<<(std::ostream &, const RationalNumber &);
};
#endif // RATIONAL_NUMBER_H
