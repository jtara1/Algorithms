#include <cmath>
#include <vector>
#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <sstream>
#include <cstring>

#include "calculate_roots.h"

using namespace std;

CalculateRoots::CalculateRoots(functionOfX func, double *guesses, int guessesSize, int maxIterations = 100, double targetRelativeError = 0.01, double *trueRoot) {
	iterations = 0;

	f = func;

    this->guesses = guesses;
    this->guessesSize = guessesSize;

	this->maxIterations = maxIterations;
	this->targetRelativeError = targetRelativeError;
	this->trueRoot = trueRoot;
}

bool CalculateRoots::getNextGuesses() {
    if (guessIndex + guessesPerRoot - 1 >= guessesSize)
        return false;

    // create output file to hold approximations & errors for a set of iterations
    stringstream ss;
    ss << guessIndex / guessesPerRoot;
    string ofName = methodName + ss.str() + ".csv";

    outFile.open(ofName);
    if (outFile.fail()) {
        printf("Failed to create file for output file: %s.\n", ofName);
        cerr << "Error: " << strerror(errno);
        exit(1);
    }

    // update variables for next set of iterations
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
    relativeError = 2e9;
    absoluteError = 2e9;
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
        outFile.close();
        outFile.clear();
	}
	return roots;
}

double CalculateRoots::calculateErrors() {
    if (trueRoot != nullptr && *trueRoot != 0)
        absoluteError = abs(*trueRoot - approximation) / abs(*trueRoot);
    if (iterations != 0)
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
	// root found - we're at zero or extremely close to it
	if (fapprox < nearZero && fapprox > -nearZero)
        return true;
    return false;
}

void CalculateRoots::recordItems(double *numbs, int size, bool endLineAfter) {
    for (double *pointer = numbs; pointer != numbs + size; pointer++) {
        // print
        cout << setprecision(3) << setw(10) << *pointer;
        cout.flush();

        // write to file
        outFile << *pointer << ',';
    }
    if (endLineAfter) {
        outFile << endl;
        cout << endl;
    }
}

void CalculateRoots::recordItems(string *items, int size, bool endLineAfter) {
    for (string *pointer = items; pointer != items + size; pointer++) {
        // print
        cout << setw(10) << *pointer;
        cout.flush();

        // write to file
        outFile << *pointer << ',';
    }
    if (endLineAfter) {
        outFile << endl;
        cout << endl;
    }
}

void CalculateRoots::writeLineToFile(double *items, int size) {
    for (; size > 0; size--) {
        outFile << *items << ',';
        items++;
    }
    outFile << endl;
}

void CalculateRoots::writeLineToFile(string *items, int size) {
    for (; size > 0; size--) {
        outFile << *items << ',';
        items++;
    }
    outFile << endl;
}

double CalculateRoots::getApproximation() {
    return approximation;
}

void CalculateRoots::printRoots() {
    cout << "Roots at x = {\n" << setprecision(15);
    for (int i = 0; i < roots.size(); i++) {
        cout << roots.at(i) << endl;
    }
    cout << "}\n" << endl;
}
