#include <iostream>
#include <iomanip>
#include "bisection.h"
#include "calculate_roots.h"

using namespace std;

Bisection::Bisection(CalculateRoots::functionOfX f, double *guesses) 
: CalculateRoots::CalculateRoots(f, guesses) {
	
}

void Bisection::calculateApproximation() {
	previousApproximation = approximation;
	approximation = (x1 + x2) / 2;
	
	// root found
	if (setupNextIteration())
		return;
		
	printIteration();
	iterations++;
	// recursive call
	calculateApproximation();
}

bool Bisection::setupNextIteration() {
	float signOfProduct = f(x1) * f(approximation);
	if (signOfProduct < 0)
		x2 = approximation;
	else if (signOfProduct > 0)
		x1 = approximation;
	// root found
	else
		return true;
	return false;
}

void Bisection::printIteration() {
	cout << setw(10) << setprecision(3)
		<< iterations << x1 << x2 << approximation << f(x1) 
		<< f(x2) << f(approximation);
	if (iterations == 0)
		cout << endl;
	else
		cout << CalculateRoots::calculateRelativeError() << endl;
}

void Bisection::printIterationHeader() {
	cout << setw(10) << setprecision(3)
		<< "n" << "a" << "b" << "c" << "f(a)" << "f(b)" << "f(c)" << "Rel. Err." << endl;
}
