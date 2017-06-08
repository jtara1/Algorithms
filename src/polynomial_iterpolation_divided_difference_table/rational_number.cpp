#include <iostream>
#include <cstdlib>
#include <sstream>

#include "rational_number.h"

const RationalNumber RationalNumber::ZERO = RationalNumber(0, 1);
const RationalNumber RationalNumber::ONE = RationalNumber(1, 1);
bool RationalNumber::PRINT_AS_FRACTION = true;
bool RationalNumber::PRINT_SIGN = true;

RationalNumber::RationalNumber() {
    num = 1;
    den = 1;
}

RationalNumber::RationalNumber(std::string n, std::string d) {
    parseString(n, d);
}

RationalNumber::RationalNumber(std::string rational) {
    parseString(rational);
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
    // the denominator can become too large if "0" is multiplied over and over again
    if (num == 0 || num == -0) {
        den = 1;
        return *this;
    }

    bool denominatorIsLarger = abs(num) <= abs(den);

    bool isNegative = num * den < 0;
    num *= (isNegative ? -1 : 1); // needs to be positive for while condition

    int divisor = denominatorIsLarger ? num : den;
    int dividend = denominatorIsLarger ? den: num;

    int prevDivisor = 0;
    while (prevDivisor < divisor) {
        prevDivisor++;
        if (divisor % prevDivisor != 0 || divisor % prevDivisor != -0)
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

void RationalNumber::parseString(std::string rational) {
    size_t pos = rational.find("/");
    if (pos == std::string::npos) {
        parseString(rational, "1");
        return;
    }

    std::string n = rational.substr(0, pos);
    std::string d;
    if (pos == rational.size() - 1)
        d = "1";
    else {
        d = rational.substr(pos + 1, rational.size() - pos);
    }
    parseString(n, d);
}

void RationalNumber::parseString(std::string n, std::string d) {
    den = (d == "" ? 1 : atoi(d.c_str()));
    if (den == 0)
        throw std::invalid_argument("can't divide by 0");

    num = (n == "" ? 1 : atoi(n.c_str()));
    simplify();
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
    return RationalNumber(num * op.num, den * op.den);
}

RationalNumber RationalNumber::operator*(const int &op) {
    return RationalNumber(num * op, den);
}

RationalNumber RationalNumber::operator*=(const RationalNumber &op) {
    num *= op.num;
    den *= op.den;
    return *this;
}

RationalNumber RationalNumber::operator/(const RationalNumber &op) {
    if (op.num == 0 || op.num == -0)
        throw std::invalid_argument("attempted to divide by 0 while dividing two rational numbers");
    return RationalNumber(*this * RationalNumber(op.den, op.num)).simplify();
}

bool RationalNumber::operator<(const RationalNumber &op) {
    return getDecimalValue() < op.getDecimalValue();
}

bool RationalNumber::operator>(const RationalNumber &op) {
    return getDecimalValue() > op.getDecimalValue();
}

bool RationalNumber::operator==(const RationalNumber &op) {
    return (num == op.num && den == op.den) || (getDecimalValue() == op.getDecimalValue());
}

RationalNumber RationalNumber::operator=(const RationalNumber &op) {
    num = op.num;
    den = op.den;
    return *this;
}

std::ostream& operator<<(std::ostream &os, const RationalNumber &rationalNumber) {
    std::stringstream ss;
    if (RationalNumber::PRINT_SIGN && rationalNumber.num >= 0) {
        ss << '+';
    }

    if (RationalNumber::PRINT_AS_FRACTION) {
        if (rationalNumber.num == 0 || rationalNumber.den == 1)
            ss << rationalNumber.num;
        else
            ss << rationalNumber.num << "/" << rationalNumber.den;
    }
    else
        ss << rationalNumber.getDecimalValue();
    os << ss.str();
    return os;
}

std::string RationalNumber::toString() const {
    if (RationalNumber::PRINT_AS_FRACTION) {
        std::stringstream ss;
        if (num == 0 || den == 1)
            ss << num;
        else
            ss << num << "/" << den;
        return ss.str();
    }
    else
        throw std::invalid_argument("rational to string as decimal is not implemented");
    return "";
}

float RationalNumber::getDecimalValue() const {
    return num / float(den);
}
