#include <stdexcept>
#include <iostream>
#include "false_position.h"

using namespace std;

FalsePosition::FalsePosition(functionOfX func, double *guesses, int guessesSize, int maxIterations = 100, double targetRelativeError = 0.01)
: BracketingMethod::BracketingMethod(func, guesses, guessesSize, maxIterations, targetRelativeError) {
    methodName = "False-position";
}

void FalsePosition::calculateApproximation() {
    if (exitConditionsMet())
        return;

    if (iterations == 0) {
        fx1 = f(x1);
        fx2 = f(x2);
    }

    previousApproximation = approximation;
    approximation = x2 - (fx2 * (x2 - x1)) / (fx2 - fx1);
    fapprox = f(approximation);

    printIteration();
    iterations++;

    if (setupNextIteration()) {
        roots.push_back(approximation);
        return;
    }
    calculateApproximation(); // recursive call
}

bool FalsePosition::setupNextIteration() {
    if (rootFound())
        return true;
    else if (fapprox < 0) {
        x1 = approximation;
        fx1 = fapprox;
    }
    else {
        x2 = approximation;
        fx2 = fapprox;
    }
    return false;
}

void FalsePosition::printIteration() {
    double numbs[] = {(double)iterations, x1, x2, approximation, fx1, fx2, fapprox};
    // print values in this row for this iteration of the table
    printItems(numbs, sizeof(numbs)/sizeof(*numbs));
    if (iterations != 0) {
        double err = calculateRelativeError();
        printItems(&err, 1);
    }
    cout << endl;
}

void FalsePosition::printIterationHeader() {
    string headers[] = {"n", "a", "b", "c", "f(a)", "f(b)", "f(c)", "Rel. Err."};
    printItems(headers, sizeof(headers)/sizeof(*headers));
    cout << endl;
}
