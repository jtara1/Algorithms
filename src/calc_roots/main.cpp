#include <iostream>
#include <cmath>
#include <iomanip>
#include "bisection.h"
#include "calculate_roots.h"

using namespace std;

/**
 * Roots at x = {
 * 0.36509824,
 * 1.9217409,
 * 3.5631608}
 */
double functionA(double x) {
	return 2 * pow(x, 3.) - 11.7 * pow(x, 2.) + 17.7 * x - 5;
}

/**
 * Root at x = 126.632
 */
double functionB(double x) {
	return x + 10 - x * cosh(50 / x);
}

int main() {
    // funcA, bisection
	double guesses[] = {0., 0.5};
	CalculateRoots::functionOfX f = &functionA;
	Bisection bisection = Bisection(f, guesses);
	vector<double> roots = bisection.calculateRoots();
	cout << setprecision(15) << roots.at(0);

	return 0;
}
