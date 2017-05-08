#include <cmath>
#include <vector>
#include <iostream>
#include <iomanip>
#include "calculate_roots.h"

using namespace std;

CalculateRoots::CalculateRoots(functionOfX func, double *guesses, int maxIterations = 100, double targetRelativeError = 0.01) {
	iterations = 0;

	f = func;

	x1 = guesses[0];
	x2 = guesses[1];

	this->maxIterations = maxIterations;
	this->targetRelativeError = targetRelativeError;
}

vector<double> CalculateRoots::calculateRoots() {
	printIterationHeader();
	calculateApproximation();
	return roots;
}

double CalculateRoots::calculateRelativeError() {
	relativeError = abs(approximation - previousApproximation) / abs(approximation);
	return relativeError;
}

bool CalculateRoots::exitConditionsMet() {
    // reached max iterations
    if (iterations > maxIterations)
        return true;

    // target relative error reached
    else if (relativeError < targetRelativeError && iterations > 0) {
        // possibly append roots vector with approximation
        roots.push_back(approximation);
        return true;
    }
    return false;
}

bool CalculateRoots::rootFound(double const nearZero) {
//    double funcOfApprox = f(approximation);

	// root found - we're at zero or extremely close to it
	if (fapprox < nearZero && fapprox > -nearZero)
        return true;
    return false;
}

void CalculateRoots::printItems(double *numbs, int size) {
    for (double *pointer = numbs; pointer != numbs + size; pointer++) {
        cout << setprecision(3) << setw(10) << *pointer;
        cout.flush();
    }
}

void CalculateRoots::printItems(string *items, int size) {
    for (string *pointer = items; pointer != items + size; pointer++) {
        cout << setw(10) << *pointer;
        cout.flush();
    }
}

double CalculateRoots::getApproximation() {
    return approximation;
}
