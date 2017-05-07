#include <cmath>
#include <vector>
#include "calculate_roots.h"
 
using namespace std;
 
CalculateRoots::CalculateRoots(double (*f)(double), double *guesses) {
	iterations = 0;
	
	functionOfX = f;
	
	x1 = guesses[0];
	guesses++
	// if guess #2 provided
	if (*guesses)
		x2 = guesses[0];
	else
		x2 = 0;
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
