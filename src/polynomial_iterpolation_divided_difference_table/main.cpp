#include <iostream>
#include <ratio>

#include "polynomial.h"
#include "rational_number.h"
#include "divided_difference.h"

using namespace std;


/// test cases
void testPolynomial() {
    RationalNumber::PRINT_AS_FRACTION = false;

    cout << "ADDITION TESTS:\n";
    Polynomial a = Polynomial("x^0 + 2x^1 + 2x^2 + 11x^3");
    Polynomial b = Polynomial("x^0 + 2x^2 + 3x^3");
    Polynomial apple = Polynomial("-1x^0");
    cout << "-1x^0 = ";
    apple.print();
    cout << endl;

    a.print();
    b.print();
    Polynomial c = a + b;
    c.print();

    cout << "-----" << endl;

    Polynomial x = Polynomial("x^0 + 2x^3 + 2x^8");
    Polynomial y = Polynomial("x^0 + 2x^2 + 3x^3  + 11x^5");
    x.print();
    y.print();
    Polynomial z = x + y;
    z.print();

    cout << "-----" << endl;

    Polynomial d = Polynomial("0x^0");
    Polynomial e = Polynomial("x^0 + x^2");
    d.print();
    e.print();
    Polynomial f = d + e;
    f.print();

    cout << "\nSUBTRACTION TESTS:\n";
    x.print();
    y.print();
    Polynomial z1 = x - y;
    z1.print();
    cout << endl;

    a.print();
    b.print();
    Polynomial a1 = a - b;
    a1.print();

    cout << "-----" << endl;

    cout << "\nASSIGNMENT TESTS:\n";
    a.print();
    b.print();
    cout << "-----" << endl;
    a = b;
    b.print();
    a.print();

    cout << "-----" << endl;

    cout << "\nMULTIPLICATION TESTS:\n";
    d.print();
    e.print();
    Polynomial f1 = d * e;
    f1.print();

    cout << "-----" << endl;

    x.print();
    y.print();
    Polynomial z2 = x * y;
    z2.print();

    cout << "-----" << endl;

    cout << "\nCOPY CONSTRUCTOR TESTS:\n";
    x.print();
//    printPolynomial(x);
}

/// test cases
void testRationalNumber() {
    RationalNumber::PRINT_AS_FRACTION = true;

    RationalNumber a(2, 4);
    RationalNumber b(1, 4);

    RationalNumber d1(10, -6);
    cout << "10/-6 simplified => " << d1 << endl;

    cout << a << " + " << b << " = " << a + b << endl;

    RationalNumber c(12, 38);
    cout << "12/38" << " simplified => " << c << endl;

    RationalNumber d(40, 12);
    cout << "40/12 simplified => " << d << endl;

    RationalNumber e(4, 10); // 2 over 5
    RationalNumber f(3, 11);
    RationalNumber g = e * f;
    cout << e << " * " << f << " = " << g << endl;

    RationalNumber a1(4, -404);
    cout << "4/-404" << " simplified => " << a1 << endl;

    RationalNumber b1(1, 6);
    cout << "(1/6 - 1/2)/(-1) = " << (b1 - a) / (RationalNumber(-1, 1)) << endl;

    RationalNumber c1(0, 1002);
    RationalNumber c2(0, -10);
    cout << "0/1002 = " << c1 << endl;
    cout << "0/-10 = " << c2 << endl;
//    cout << "0/1002 / 0/-10 = " << c1 / c2 << endl;
    cout << "0/1002 + 0/-10 = " << c1 + c2 << endl;
    cout << "0/1002 - 0/-10 = " << c1 - c2 << endl;
}

/**
 * The input should be a file containing one row of x-values
 * and a second row of y-values
 * Each value needs to be an integer or ratio (e.g.: -3, 4/22, etc.)
 */
int main() {
    // the data is loaded from the file, polynomial is interpolated, & output printed
//    DividedDifference divDiff;
//    DividedDifference divDiff2("input (1).txt");
//    DividedDifference divDiff3("input (2).txt");
//    DividedDifference divDiff4("input (3).txt");

//    testRationalNumber();
    testPolynomial();
}
