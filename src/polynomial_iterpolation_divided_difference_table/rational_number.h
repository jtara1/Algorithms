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

    void parseString(std::string, std::string);

public:
    RationalNumber();
    RationalNumber(int, int);
    RationalNumber(std::string, std::string);

    /// the rational number representation of zero
    const static RationalNumber ZERO;

    /// flag used to toggle between printing RationalNumber in fraction or decimal form
    static bool PRINT_AS_FRACTION;

    RationalNumber operator+(const RationalNumber &);
    RationalNumber operator-(const RationalNumber &);
    RationalNumber operator*(const RationalNumber &);
    RationalNumber operator*(const int &);
    RationalNumber operator*=(const RationalNumber &);
    RationalNumber operator/(const RationalNumber &);
    bool operator<(const RationalNumber &);
    bool operator>(const RationalNumber &);
    RationalNumber operator=(const RationalNumber &);
    friend std::ostream& operator<<(std::ostream &, const RationalNumber &);

    std::string toString() const;
    float getDecimalValue() const;
};
#endif // RATIONAL_NUMBER_H
