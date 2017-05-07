#include <iostream>
#include <cmath>
#include <ctgmath>
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

//~ double functionB(double x) {
	//~ return x + 10 - x * cosh(50 / x);
//~ }

int main() {
	double guesses[] = {0., 0.5};
	CalculateRoots bisection = Bisection(functionA, guesses);
	bisection.calculateRoots();
	
	return 0;
}
