#include <iostream>
#include "newton_raphson.h"

using namespace std;

NewtonRaphson::NewtonRaphson(functionOfX func, functionOfX fPrime, double *guess)
: CalculateRoots(func, guess, 100, 0.01) {
    this->fPrime = fPrime;
}

void NewtonRaphson::calculateApproximation() {
    // max iter or target rel. err. reached
    if (exitConditionsMet())
        return;

    fx1 =f(x1);
    fPrimex1 = fPrime(x1);
    previousApproximation = approximation;

    // calc next approximation
    approximation = x1 - fx1 / fPrimex1;

    fapprox = f(approximation);
    printIteration();
    iterations++;

    if (setupNextIteration())
        return; // root found
    // recursive call
    calculateApproximation();
}

bool NewtonRaphson::setupNextIteration() {
    if (rootFound())
        return true;
    x1 = approximation;
    return false;
}

void NewtonRaphson::printIteration() {
    double numbs[] = {(double)iterations, x1, approximation, fx1, fPrimex1, fapprox};
    // print values in this row for this iteration of the table
    printItems(numbs, sizeof(numbs)/sizeof(*numbs));
    if (iterations != 0) {
        double err = calculateRelativeError();
        printItems(&err, 1);
    }
    cout << endl;
}

void NewtonRaphson::printIterationHeader() {
    string headers[] = {"n", "Xn", "Xn+1", "f(Xn)", "f'(Xn)", "f(Xn+1)", "Rel. Err."};
    printItems(headers, sizeof(headers)/sizeof(*headers));
    cout << endl;
}
