#include <iostream>
#include "secant_modified.h"

using namespace std;

SecantModified::SecantModified(functionOfX func, double *bracketingPoints, double delta, int maxIter = 100, double targetRelErr = 0.01)
: CalculateRoots::CalculateRoots(func, bracketingPoints, maxIter, targetRelErr) {
    if (x1 == 0.0)
        throw std::invalid_argument("Modified Secant method initial guess can not be 0.\n");
    x2 = x1 * delta;
    this->delta = delta;
}

void SecantModified::calculateApproximation() {
    if (exitConditionsMet())
        return;

    if (iterations == 0) {
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
    printItems(numbs, sizeof(numbs)/sizeof(*numbs));
    if (iterations != 0) {
        double err = calculateRelativeError();
        printItems(&err, 1);
    }
    cout << endl;
}

void SecantModified::printIterationHeader() {
    string headers[] = {"n", "Xn", "dXn", "Xn+1", "f(Xn)", "f(Xn+dXn)", "f(Xn+1)", "Rel. Err."};
    printItems(headers, sizeof(headers)/sizeof(*headers));
    cout << endl;
}
