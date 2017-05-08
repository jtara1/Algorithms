#include <iostream>
#include <iomanip>
#include "bisection.h"
//#include "calculate_roots.h"

using namespace std;

Bisection::Bisection(CalculateRoots::functionOfX f, double *guesses)
: CalculateRoots::CalculateRoots(f, guesses, 100, 0.01) {
    if (f(x1) * f(x2) > 0)
        throw invalid_argument("f(a) and f(b) can not have the same sign");
}

void Bisection::calculateApproximation() {
    // reach max iterations
    if (iterations > maxIterations)
        return;

    // target relative error reached
    if (relativeError < targetRelativeError && iterations > 0) {
        // possibly append roots vector with approximation
        roots.push_back(approximation);
        return;
    }

	previousApproximation = approximation;
	approximation = (x1 + x2) / 2;

	// root found, done iterating
	if (setupNextIteration()) {
        roots.push_back(approximation);
		return;
	}

	printIteration();
	iterations++;
	// recursive call
	calculateApproximation();
}

bool Bisection::setupNextIteration() {
	float signOfProduct = f(x1) * f(approximation);
	double funcOfApprox = f(approximation);

	// root found - we're at zero or extremely close to it
	if (funcOfApprox < NEAR_ZERO && funcOfApprox > -NEAR_ZERO)
        return true;
	else if (signOfProduct < 0)
		x2 = approximation;
	else
		x1 = approximation;

	return false;
}

void Bisection::printIteration() {
    double numbs[] = {(double)iterations, x1, x2, approximation, f(x1), f(x2), f(approximation)};
    // print values in this row for this iteration of the table
    printItems(numbs, sizeof(numbs)/sizeof(*numbs));
    if (iterations != 0) {
        double err = calculateRelativeError();
        printItems(&err, 1);
    }
    cout << endl;
}

void Bisection::printIterationHeader() {
    string headers[] = {"n", "a", "b", "c", "f(a)", "f(b)", "f(c)", "Rel. Err."};
    printItems(headers, sizeof(headers)/sizeof(*headers));

}
