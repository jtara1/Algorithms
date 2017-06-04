#include <iostream>
#include <cstdlib>
#include <sstream>

#include "rational_number.h"

const RationalNumber RationalNumber::ZERO = RationalNumber(0, 1);
bool RationalNumber::PRINT_AS_FRACTION = true;

RationalNumber::RationalNumber() {
    num = 1;
    den = 1;
}

RationalNumber::RationalNumber(std::string n, std::string d) {
    parseString(n, d);
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
//    rationalize(num);
//    rationalize(den);
}

RationalNumber RationalNumber::simplify() {
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

/*
 ** find rational approximation to given real number
 ** David Eppstein / UC Irvine / 8 Aug 1993
 **
 ** With corrections from Arno Formella, May 2008
 **
 ** usage: a.out r d
 **   r is real number to approx
 **   d is the maximum denominator allowed
 **
 ** based on the theory of continued fractions
 ** if x = a1 + 1/(a2 + 1/(a3 + 1/(a4 + ...)))
 ** then best approximation is found by truncating this series
 ** (with some adjustments in the last term).
 **
 ** Note the fraction can be recovered as the first column of the matrix
 **  ( a1 1 ) ( a2 1 ) ( a3 1 ) ...
 **  ( 1  0 ) ( 1  0 ) ( 1  0 )
 ** Instead of keeping the sequence of continued fraction terms,
 ** we just keep the last partial product of these matrices.
 */
void RationalNumber::rationalize(float numb, bool isNumerator, int maxDen) {

    long m[2][2];
    double x, startx;
    long maxden;
    long ai;

    startx = x = numb;
    maxden = maxDen;

    /* initialize matrix */
    m[0][0] = m[1][1] = 1;
    m[0][1] = m[1][0] = 0;

    /* loop finding terms until denom gets too big */
    while (m[1][0] *  ( ai = (long)x ) + m[1][1] <= maxden) {
        long t;
        t = m[0][0] * ai + m[0][1];
        m[0][1] = m[0][0];
        m[0][0] = t;
        t = m[1][0] * ai + m[1][1];
        m[1][1] = m[1][0];
        m[1][0] = t;
            if(x==(double)ai) break;     // AF: division by zero
        x = 1/(x - (double) ai);
            if(x>(double)0x7FFFFFFF) break;  // AF: representation failure
    }

    /* now remaining x is between 0 and 1/ai */
    /* approx as either 0 or 1/m where m is max that will fit in maxden */
    /* first try zero */
    printf("%ld/%ld, error = %e\n", m[0][0], m[1][0],
	   startx - ((double) m[0][0] / (double) m[1][0]));

    /* now try other possibility */
    ai = (maxden - m[1][1]) / m[1][0];
    m[0][0] = m[0][0] * ai + m[0][1];
    m[1][0] = m[1][0] * ai + m[1][1];
    printf("%ld/%ld, error = %e\n", m[0][0], m[1][0],
	   startx - ((double) m[0][0] / (double) m[1][0]));

}

void RationalNumber::parseString(std::string n, std::string d) {
    num = (n == "" ? 1 : atoi(n.c_str()));
    den = (d == "" ? 1 : atoi(d.c_str()));
    if (den == 0)
        throw std::invalid_argument("can't divide by 0");
//    RationalNumber(n == "" ? 1 : std::atoi(n.c_str()),
//                   d == "" ? 1 : std::atoi(d.c_str())
//    );
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

RationalNumber RationalNumber::operator/(const RationalNumber &op) {
    return RationalNumber(*this * RationalNumber(op.den, op.num)).simplify();
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
    if (RationalNumber::PRINT_AS_FRACTION) {
        std::stringstream ss;
        ss << rationalNumber.num << "/" << rationalNumber.den;
        os << ss.str();
//        os << rationalNumber.num << "/" << rationalNumber.den;
    }
    else
        os << rationalNumber.num / (double)rationalNumber.den;
    return os;
}

std::string RationalNumber::toString() {
    if (RationalNumber::PRINT_AS_FRACTION) {
        std::stringstream ss;
        ss << num << "/" << den;
        return ss.str();
    }
    else
        throw std::invalid_argument("rational to string as decimal is not implemented");
    return "";
}
