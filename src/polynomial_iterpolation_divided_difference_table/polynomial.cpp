#include <iostream>
#include <cctype>
#include <cstdlib>
#include <string>
#include <cstring>

#include "polynomial.h"

using namespace std;

Polynomial::Polynomial() {
    parseString("0x^0");
}

Polynomial::Polynomial(int terms) {
    coefficients = new RationalNumber[terms];
    exponents = new int[terms];
    this->terms = terms;
}

Polynomial::Polynomial(string poly) {
    parseString(poly);
}

Polynomial::Polynomial(const Polynomial &poly) {
    terms = poly.terms;
    delete [] coefficients;
    delete [] exponents;

    coefficients = new RationalNumber[terms];
    exponents = new int[terms];

    memcpy(coefficients, poly.coefficients, terms * sizeof(RationalNumber));
    memcpy(exponents, poly.exponents, terms * sizeof(int));
}

Polynomial::~Polynomial() {
    delete [] coefficients;
    delete [] exponents;
}

void Polynomial::parseString(string poly) {
    removeSpaces(poly);
    // count how many terms there are
    size_t index = 0;
    terms = 0;
    while (index != string::npos) {
        index = poly.find("x^", index);
        if (index != string::npos) {
            index += 2;
            terms++;
        }
    }

    coefficients = new RationalNumber[terms];
    exponents = new int[terms];

    // insert plus sign in front of first coefficient if no sign
    if (poly[0] != '-' || poly[0] != '+')
        poly = poly.insert(0, "+");

    size_t splitIndex = poly.find_first_of("+-");
//    parseTerm(poly.substr(0, splitIndex), 0);

    for (int i = 0; i < terms; i++) {
        size_t nextSplitIndex = poly.find_first_of("+-", splitIndex + 2);
        if (nextSplitIndex == string::npos)
            nextSplitIndex = poly.size();
        parseTerm(poly.substr(splitIndex, nextSplitIndex - splitIndex), i);
        splitIndex = nextSplitIndex;
    }
}

void Polynomial::parseTerm(string term, int termIndex) {
    size_t splitIndex = term.find("x^");

    if (splitIndex == string::npos)
        throw invalid_argument("\"x^\" could not be found in a term in the polynomial\n");

//    RationalNumber coefficient = atof(term.substr(0, splitIndex));
    coefficients[termIndex] = (term[1] == 'x' ? RationalNumber(1, 1) : RationalNumber(atoi(term.c_str()), 1));

    size_t expIndex = splitIndex + 2;
    exponents[termIndex] = atoi(term.substr(expIndex, term.size() - expIndex).c_str());
}

void Polynomial::removeSpaces(string &str) {
    size_t index = str.find(" ");

    // no more " " strings in str arg
    if (index == string::npos)
        return;

    str = str.replace(index, 1, "");
    removeSpaces(str);
}

void Polynomial::print() const {
    for (int i = 0; i < terms; i++) {
        cout << (coefficients[i] < RationalNumber::ZERO ? "" : "+") << coefficients[i] << "x^" << exponents[i];
    }
    cout << endl;
}

Polynomial Polynomial::operator+(const Polynomial &op) {
    Polynomial newP = Polynomial(terms + op.terms);

    int polyIndex = 0, a = 0, b = 0;
    while (a < terms || b < op.terms) {
        while (b < op.terms) {
            if (a < terms) {
                if (exponents[a] == op.exponents[b]) {
                    newP.coefficients[polyIndex] = coefficients[a] + op.coefficients[b];
                    newP.exponents[polyIndex] = exponents[a];
                    polyIndex++;
                    a++;
                    b++;
                    break;
                } else if (exponents[a] > op.exponents[b]) {
                    newP.coefficients[polyIndex] = op.coefficients[b];
                    newP.exponents[polyIndex] = op.exponents[b];
                    polyIndex++; b++;
                } else {
                    newP.coefficients[polyIndex] = coefficients[a];
                    newP.exponents[polyIndex] = exponents[a];
                    polyIndex++; a++;
                    break;
                }
            } else {
                newP.coefficients[polyIndex] = op.coefficients[b];
                newP.exponents[polyIndex] = op.exponents[b];
                polyIndex++; b++;
            }
        }
        if (b >= op.terms && a < terms) {
            // more terms from this polynomial too add, but no more from op poly
            newP.coefficients[polyIndex] = coefficients[a];
            newP.exponents[polyIndex] = exponents[a];
            polyIndex++; a++;
        }
    }

    // down size the length of the two pointers used in newP
    RationalNumber *coef = new RationalNumber[polyIndex];
    int *expo = new int[polyIndex];

    memcpy(coef, newP.coefficients, (polyIndex) * sizeof(RationalNumber));
    memcpy(expo, newP.exponents, (polyIndex) * sizeof(int));

    delete [] newP.coefficients;
    delete [] newP.exponents;

    newP.coefficients = coef;
    newP.exponents = expo;
    newP.terms = polyIndex;

    return newP;
}

Polynomial Polynomial::operator-(const Polynomial &op) {
    Polynomial temp;
    temp = op;
    for (int i = 0; i < op.terms; i++) {
        temp.coefficients[i] = temp.coefficients[i] * -1;
    }
    return *this + temp;
}

Polynomial Polynomial::operator*(const Polynomial &op) {
    Polynomial poly1 = terms <= op.terms ? *this : op;
    Polynomial poly2 = terms > op.terms ? *this : op;

    // this'll a number of polynomials that hasn't combined like terms yet
    Polynomial *product = new Polynomial[poly1.terms];
    for (int i = 0; i < poly1.terms; i++) {
        product[i].coefficients = new RationalNumber[poly2.terms];
        product[i].exponents = new int[poly2.terms];
        product[i].terms = poly2.terms;
    }

    for (int i = 0; i < poly1.terms; i++) {
        for (int j = 0; j < poly2.terms; j++) {
            product[i].coefficients[j] = poly1.coefficients[i] * poly2.coefficients[j];
            product[i].exponents[j] = poly1.exponents[i] + poly2.exponents[j];
        }
    }

    Polynomial sum = Polynomial("0x^0"); // zero polynomial
    // simply the product to a single polynomial
    for (int i = 0; i < poly1.terms; i++) {
        sum = sum + product[i];
    }
    delete [] product;

    return sum;
}

Polynomial& Polynomial::operator=(const Polynomial &op) {
    if (terms != op.terms) {
        delete [] coefficients;
        delete [] exponents;

        terms = op.terms;
        coefficients = new RationalNumber[terms];
        exponents = new int[terms];
    }

    memcpy(coefficients, op.coefficients, op.terms * sizeof(RationalNumber));
    memcpy(exponents, op.exponents, op.terms * sizeof(int));

    return *this;
}
