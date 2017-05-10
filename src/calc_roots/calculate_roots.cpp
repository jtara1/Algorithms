#include <cmath>
#include <vector>
#include <iostream>
#include <iomanip>
#include "calculate_roots.h"

using namespace std;

CalculateRoots::CalculateRoots(functionOfX func, double *guesses, int guessesSize, int maxIterations = 100, double targetRelativeError = 0.01) {
	iterations = 0;

	f = func;

    this->guesses = guesses;
    this->guessesSize = guessesSize;

	this->maxIterations = maxIterations;
	this->targetRelativeError = targetRelativeError;
}

bool CalculateRoots::getNextGuesses() {
    if (guessIndex + guessesPerRoot - 1 >= guessesSize)
        return false;

    if (guessesPerRoot == 1) {
        x1 = guesses[guessIndex];
        fx1 = f(x1);
    } else if (guessesPerRoot == 2) {
        x1 = guesses[guessIndex];
        fx1 = f(x1);
        x2 = guesses[guessIndex + 1];
        fx2 = f(x2);
    } else {
        throw invalid_argument("number of guesses per root should be 1 or 2\n");
    }
    iterations = 0;
    relativeError = 100;
    guessIndex += guessesPerRoot;

    return true;
}

vector<double> CalculateRoots::calculateRoots() {
    cout << methodName << ":\n";
    // approx next root while next guess(es) available to use
	while (getNextGuesses()) {
        printIterationHeader();
        // recursively calls itself until a break condition is met
        calculateApproximation();
        cout << endl;
	}
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

void CalculateRoots::printRoots() {
    cout << "Roots at x = {\n" << setprecision(15);
    for (int i = 0; i < roots.size(); i++) {
        cout << roots.at(i) << endl;
    }
    cout << "}" << endl;
}
