#include <iostream>

#include "rational_number.h"

RationalNumber::RationalNumber() {
    num = 1;
    den = 1;
}

RationalNumber::RationalNumber(int n, int d) {
    if (d == 0)
        throw std::invalid_argument("denominator can not be 0");

    num = n;
    den = d;
}

RationalNumber RationalNumber::simplify() {
    bool denominatorIsLarger = num <= den ? true : false;
    int divisor = denominatorIsLarger ? num : den;
    int dividend = denominatorIsLarger ? den: num;

    int prevDivisor = 0;
    while (prevDivisor < divisor) {
        prevDivisor++;
        if (divisor % prevDivisor != 0)
            continue;

        int thisDivisor = divisor / prevDivisor;
        if (dividend % thisDivisor == 0) {
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
    return *this;
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

RationalNumber RationalNumber::operator=(const RationalNumber &op) {
    num = op.num;
    den = op.den;
    return *this;
}
