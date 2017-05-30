#ifndef RATIONAL_NUMBER_H
#define RATIONAL_NUMBER_H

#include <ostream>

class RationalNumber {
private:
    /// numerator
    float num;

    /// denominator
    float den;

    /**
     * Mutate this RationalNumber to simplify
     * and return the simplified version
     */
    RationalNumber simplify();

    /**
     * Attempts to find a repeating part of the decimal
     * of the num or den, and converts it into a rational
     * number if there is a repeating part
     */
    void rationalize(float numb = this->num, bool isNumerator = true, int maxDen = 10000);

public:
    RationalNumber();
    RationalNumber(float, float);

    /// the rational number representation of zero
    const static RationalNumber ZERO;

    /// the limit to how many digits are in a repeating set after the decimal point when rationalizing
    static int RATIONALIZE_REPEATING_LIMIT;

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
