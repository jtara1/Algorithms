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
 * Root at x = 126.63243603998882806353860
 */
double functionB(double x) {
	return x + 10 - x * cosh(50 / x);
}

double fPrimeB(double x) {
    return (50 * sinh(50 / x)) / x - cosh(50 / x) + 1;
}

/**
 * Enable GCC Compiler flag:
 * -std=c++0x
 */
int main() {
    double delta = 0.01; // delta value used for secant modified method

    // functionA, its derivative, and its guesses
    CalculateRoots::functionOfX fA = &functionA;
    CalculateRoots::functionOfX fPA = &fPrimeA;

    // array of guesses used in pairs (0, 1), (1, 2), (2, 4)
    double guessesA[] = {0., 1., 1., 2., 2., 4.,};
    int guessesASize = sizeof(guessesA) / sizeof(*guessesA);

    // single guesses for methods that need 1 guess per root approximation
    double guessA[] = {1., 2., 3.,};
    int guessASize = sizeof(guessA) / sizeof(*guessA);

    // functionB, its derivative, and its guesses
    CalculateRoots::functionOfX fB = &functionB;
    CalculateRoots::functionOfX fPB = &fPrimeB;
    double guessesB[] = {50., 200.,};
    int guessesBSize = sizeof(guessesB) / sizeof(*guessesB);
    double guessB[] = {200.};
    int guessBSize = sizeof(guessB) / sizeof(*guessB);
    double trueRootB = 126.63243603998882806353860;

    int maxIterations = 100;
    double targetRelativeError = 0.0001; // 0.01%

    // funcA, bisection
	Bisection bisection = Bisection(fA, guessesA, guessesASize, maxIterations, targetRelativeError);
	bisection.calculateRoots();
	bisection.printRoots();

	// funcA, newton
	NewtonRaphson newton = NewtonRaphson(fA, fPA, guessA, guessASize, maxIterations, targetRelativeError);
	newton.calculateRoots();
	newton.printRoots();

    // funcA, secant
	Secant secant = Secant(fA, guessesA, guessesASize, maxIterations, targetRelativeError);
	secant.calculateRoots();
	secant.printRoots();

    // funcA, secant modified
	SecantModified secant2 = SecantModified(fA, delta, guessA, guessASize, maxIterations, targetRelativeError);
	secant2.calculateRoots();
	secant2.printRoots();

	// funcA, false-positive
	FalsePosition fPos = FalsePosition(fA, guessesA, guessesASize, maxIterations, targetRelativeError);
	fPos.calculateRoots();
	fPos.printRoots();

	// methods for funcB
	Bisection bi = Bisection(fB, guessesB, guessesBSize, maxIterations, targetRelativeError, &trueRootB);
	bi.calculateRoots();
	bi.printRoots();

	NewtonRaphson newt = NewtonRaphson(fB, fPB, guessB, guessBSize, maxIterations, targetRelativeError, &trueRootB);
	newt.calculateRoots();
	newt.printRoots();

	Secant sec = Secant(fB, guessesB, guessesBSize, maxIterations, targetRelativeError, &trueRootB);
	sec.calculateRoots();
	sec.printRoots();

	SecantModified secMod = SecantModified(fB, delta, guessB, guessBSize, maxIterations, targetRelativeError, &trueRootB);
	secMod.calculateRoots();
	secMod.printRoots();

	FalsePosition falsePos = FalsePosition(fB, guessesB, guessesBSize, maxIterations, targetRelativeError, &trueRootB);
	falsePos.calculateRoots();
	falsePos.printRoots();

    string stringVar;
    getline(cin, stringVar);

	return 0;
}
