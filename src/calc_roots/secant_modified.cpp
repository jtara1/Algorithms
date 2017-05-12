#include <iostream>
#include "secant_modified.h"

using namespace std;

SecantModified::SecantModified(functionOfX func, double delta, double *bracketingPoints, int guessesSize, int maxIter = 100, double targetRelErr = 0.01)
: CalculateRoots::CalculateRoots(func, bracketingPoints, guessesSize, maxIter, targetRelErr) {
    this->delta = delta;
    methodName = "Modified Secant";
    guessesPerRoot = 1;
}

bool SecantModified::guessIsValid() {
    return x1 != 0;
}

vector<double> SecantModified::calculateRoots() {
    cout << methodName << ":\n";
    // approx next root while next guess(es) available to use
	while (getNextGuesses()) {
        if (!guessIsValid())
            throw invalid_argument("x1 can not equal zero\n");

        printIterationHeader();
        // recursively calls itself until a break condition is met
        calculateApproximation();
        cout << endl;
        outFile.close();
        outFile.clear();
	}
	return roots;
}

void SecantModified::calculateApproximation() {
    if (exitConditionsMet())
        return;

    if (iterations == 0) {
        x2 = delta * x1;
        fx1 = f(x1);
        fx2 = f(x1 + x2);
    }

    previousApproximation = approximation;
    approximation = x1 - fx1 * ((delta * x1) / (fx2 - fx1));
    fapprox = f(approximation);

    printIteration();
    iterations++;

    if (setupNextIteration()) {
        roots.push_back(approximation);
        return; // root found
    }
    // recursive call
    calculateApproximation();
}

bool SecantModified::setupNextIteration() {
    if (rootFound())
        return true;

    x1 = approximation;
    x2 = x1 * delta;

    fx1 = fapprox;
    fx2 = f(x1 + x2);

    return false;
}

void SecantModified::printIteration() {
    double numbs[] = {(double)iterations, x1, x2, approximation, fx1, fx2, fapprox};
    // print values in this row for this iteration of the table
    recordItems(numbs, sizeof(numbs)/sizeof(*numbs), false);
    if (iterations != 0) {
        double err = calculateRelativeError();
        recordItems(&err, 1);
    } else
        outFile << endl;
    cout << endl;
}

void SecantModified::printIterationHeader() {
    string headers[] = {"n", "Xn", "dXn", "Xn+1", "f(Xn)", "f(Xn+dXn)", "f(Xn+1)", "Rel. Err."};
    recordItems(headers, sizeof(headers)/sizeof(*headers));
    cout << endl;
}
