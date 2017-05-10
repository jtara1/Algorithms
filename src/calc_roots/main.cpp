#include <iostream>
#include <cmath>
#include <iomanip>

#include "calculate_roots.h"
#include "bisection.h"
#include "newton_raphson.h"
#include "secant.h"
#include "secant_modified.h"
#include "false_position.h"

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

double fPrimeA(double x) {
    return 6 * pow(x, 2.) - 23.4 * x + 17.7;
}

/**
 * Root at x = 126.632
 */
double functionB(double x) {
	return x + 10 - x * cosh(50 / x);
}

double fPrimeB(double x) {
    return (50 * sinh(50 / x)) / x - cosh(50 / x) + 1;
}

/**
 * Root at x = 1;
 */
double functionC(double x) {
    return pow(x, 10.) - 1;
}

int main() {
    double delta = 0.01; // delta value used for secant modified method

    // functionA, its derivative, and its guesses
    CalculateRoots::functionOfX fA = &functionA;
    CalculateRoots::functionOfX fPA = &fPrimeA;

    // array of guesses used in pairs (0, 1), (1, 2), (2, 4)
    double guesses1A[] = {0., 1., 1., 2., 2., 4.,};
    int guesses1ASize = sizeof(guesses1A) / sizeof(*guesses1A);

    // single guesses for methods that need 1 guess per root approximation
    double guess1A[] = {1., 2., 3.,};
    int guess1ASize = sizeof(guess1A) / sizeof(*guess1A);

    // functionB, its derivative, and its guesses
    CalculateRoots::functionOfX fB = &functionB;
    CalculateRoots::functionOfX fPB = &fPrimeB;
    double guessesB[] = {120., 130.};
    double guessB[] = {125.};

    int maxIterations = 100;
    double targetRelativeError = 0.01;

    // funcA, root1, bisection
	Bisection bisection = Bisection(fA, guesses1A, guesses1ASize, maxIterations, targetRelativeError);
	bisection.calculateRoots();
	bisection.printRoots();

	// funcA, root1, newton
	NewtonRaphson newton = NewtonRaphson(fA, fPA, guess1A, guess1ASize, maxIterations, targetRelativeError);
	newton.calculateRoots();
	newton.printRoots();

    // funcA, root1, secant
	Secant secant = Secant(fA, guesses1A, guesses1ASize, maxIterations, targetRelativeError);
	secant.calculateRoots();
	secant.printRoots();

    // funcA, root1, secant modified
	SecantModified secant2 = SecantModified(fA, delta, guess1A, guess1ASize, maxIterations, targetRelativeError);
	secant2.calculateRoots();
	secant2.printRoots();

	// funcA, root1, false-positive
	FalsePosition fPos = FalsePosition(fA, guesses1A, guesses1ASize, maxIterations, targetRelativeError);
	fPos.calculateRoots();
	fPos.printRoots();

	return 0;
}
