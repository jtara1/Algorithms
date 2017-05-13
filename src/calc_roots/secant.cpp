#include <iostream>
#include <cmath>
#include "secant.h"

using namespace std;

Secant::Secant(functionOfX func, double *bracketingPoints, int guessesSize, int maxIter, double targetRelErr, double *trueRoot)
: CalculateRoots::CalculateRoots(func, bracketingPoints, guessesSize, maxIter, targetRelErr, trueRoot) {
    methodName = "Secant";
}

void Secant::swapValues(double &value1, double &value2) {
    double temp = value1;
    value1 = value2;
    value2 = temp;
}

void Secant::swapIfNeeded() {
    if (abs(fx1) > abs(fx2)) {
        swapValues(x1, x2);
        swapValues(fx1, fx2);
    }
}

void Secant::calculateApproximation() {
    if (exitConditionsMet())
        return;

    if (iterations == 0) {
        fx1 = f(x1);
        fx2 = f(x2);
    }
    swapIfNeeded();

    previousApproximation = approximation;
    approximation = x2 - fx2 * ((x2 - x1) / (fx2 - fx1));

    printIteration();
    iterations++;

    fapprox = f(approximation);
    if (setupNextIteration()) {
        roots.push_back(approximation);
        return; // root found
    }
    // recursive call
    calculateApproximation();
}

bool Secant::setupNextIteration() {
    if (rootFound())
        return true;

    x1 = x2;
    x2 = approximation;

    fx1 = fx2;
    fx2 = fapprox;

    return false;
}

void Secant::printIteration() {
    calculateErrors();
    double numbs[] = {(double)iterations, x1, x2, approximation, fapprox, absoluteError, relativeError};
    // print values in this row for this iteration of the table
    recordItems(numbs, sizeof(numbs)/sizeof(*numbs));
}

void Secant::printIterationHeader() {
    string headers[] = {"n", "Xn-1", "Xn", "Xn+1", "f(Xn+1)", "Abs. Err.", "Rel. Err."};
    recordItems(headers, sizeof(headers)/sizeof(*headers));
    cout << endl;
}
