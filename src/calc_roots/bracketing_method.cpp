#include <iostream>
#include "bracketing_method.h"

BracketingMethod::BracketingMethod(functionOfX f, double *guesses, int guessesSize, int maxIterations, double targetRelErr = 0.01)
: CalculateRoots::CalculateRoots(f, guesses, guessesSize, maxIterations, targetRelErr) {

}

bool BracketingMethod::bracketsSurroundRoot() {
    fx1 = f(x1);
    fx2 = f(x2);
    return fx1 * fx2 < 0;
}

std::vector<double> BracketingMethod::calculateRoots() {
    std::cout << methodName << ":\n";
	while (getNextGuesses()) {
        if (!this->bracketsSurroundRoot()) {
            printf("f(%f) = %f, f(%f) = %f\n", x1, fx1, x2, fx2);
            throw std::invalid_argument("a set of guesses do not bracket a root\n");
        }
        printIterationHeader();
        calculateApproximation();
        std::cout << std::endl;

        outFile.close();
        outFile.clear();
	}
	return roots;
}
