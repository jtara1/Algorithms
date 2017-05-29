#include <iostream>
#include <cstdlib>
//#include <cmath>

#include "rational_number.h"

const RationalNumber RationalNumber::ZERO = RationalNumber(0, 1);
bool RationalNumber::PRINT_AS_FRACTION = false;

RationalNumber::RationalNumber() {
    num = 1;
    den = 1;
}

RationalNumber::RationalNumber(int n, int d) {
    if (d == 0)
        throw std::invalid_argument("denominator can not be 0 in a rational number");

    // only permit the numerator to be a negative number
    if (d < 0) {
        num = n * -1;
        den = d * -1;
    } else {
        num = n;
        den = d;
    }
    simplify();
}

RationalNumber RationalNumber::simplify() {
    bool denominatorIsLarger = abs(num) <= abs(den) ? true : false;
    bool isNegative = num < 0 ? true : false;
    int divisor = denominatorIsLarger ? num : den;
    int dividend = denominatorIsLarger ? den: num;
    divisor *= (isNegative ? -1 : 1); // needs to be positive for while condition

    int prevDivisor = 0;
    while (prevDivisor < divisor) {
        prevDivisor++;
        if (divisor % prevDivisor != 0)
            continue;

        int thisDivisor = divisor / prevDivisor;
        if (dividend % thisDivisor == 0 || dividend % thisDivisor == -0) {
            divisor = prevDivisor;
            dividend /= thisDivisor;
        }
    }

    if (denominatorIsLarger) {
        num = divisor;
        den = dividend;
    } else {
        num = dividend;
        den = divisor;
    }
    num *= (isNegative ? -1 : 1); // update to original sign
    return *this ;
}

RationalNumber RationalNumber::operator+(const RationalNumber &op) {
    RationalNumber numb;
    numb.num = num * op.den + op.num * den;
    numb.den = den * op.den;
    return numb.simplify();
}

RationalNumber RationalNumber::operator-(const RationalNumber &op) {
    RationalNumber numb;
    numb.num = num * op.den - op.num * den;
    numb.den = den * op.den;
    return numb.simplify();
}

RationalNumber RationalNumber::operator*(const RationalNumber &op) {
    return RationalNumber(num * op.num, den * op.den).simplify();
}

RationalNumber RationalNumber::operator*(const int &op) {
    return RationalNumber(num * op, den).simplify();
}

bool RationalNumber::operator<(const RationalNumber &op) {
    return num < op.num;
}

bool RationalNumber::operator>(const RationalNumber &op) {
    return num > op.num;
}

RationalNumber RationalNumber::operator=(const RationalNumber &op) {
    num = op.num;
    den = op.den;
    return *this;
}

std::ostream& operator<<(std::ostream &os, const RationalNumber &rationalNumber) {
    if (RationalNumber::PRINT_AS_FRACTION)
        os << rationalNumber.num << "/" << rationalNumber.den;
    else
        os << rationalNumber.num / (double)rationalNumber.den;
    return os;
}
