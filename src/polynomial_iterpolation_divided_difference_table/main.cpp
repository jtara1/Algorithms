#include <iostream>

#include "rational_number.h"

using namespace std;

void testRationalNumber() {
    RationalNumber a(2, 4);
    RationalNumber b(1, 4);
    cout << a << " + " << b << " = " << a + b << endl;

    RationalNumber c(12, 38);
    cout << "12/38" << " simplified => " << c << endl;

    RationalNumber d(40, 12);
    cout << "40/12 simplified => " << d << endl;

    RationalNumber e(4, 10); // 2 over 5
    RationalNumber f(3, 11);
    RationalNumber g = e * f;
    cout << e << " * " << f << " = " << g << endl;
}

int main() {
//    testRationalNumber();

}
