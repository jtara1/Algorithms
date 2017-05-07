#include <cmath>
#include <vector>
#include "calculate_roots.h"

using namespace std;

CalculateRoots::CalculateRoots(functionOfX func, double *guesses) {
	iterations = 0;

	f = func;

	x1 = guesses[0];
	guesses++;
	// if guess #2 provided
	if (*guesses)
		x2 = *guesses;
	else
		x2 = 0;
}

//vector<double> CalculateRoots::calculateRoots() {
//	CalculateRoots::printIterationHeader();
//	CalculateRoots::calculateApproximation();
//	return roots;
//}

double CalculateRoots::calculateRelativeError() {
	relativeError = abs(approximation - previousApproximation) / abs(approximation);
	return relativeError;
}
