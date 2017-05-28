#ifndef RATIONAL_NUMBER_H
#define RATIONAL_NUMBER_H

public class RationalNumber {
private:
    /// numerator
    int num;

    /// denominator
    int den;

public:
    RationalNumber();
    RationalNumber(int, int);

    /**
     * Mutate this RationalNumber to simplify
     * and return the simplified version
     */
    RationalNumber simplify();

    RationalNumber operator+(const RationalNumber &);
    RationalNumber operator-(const RationalNumber &);
    RationalNumber operator*(const RationalNumber &);
    RationalNumber operator=(const RationalNumber &);
    std::ostream& operator<<(std::ostream &, const RationalNumber &rationalNum);
};
#endif // RATIONAL_NUMBER_H
