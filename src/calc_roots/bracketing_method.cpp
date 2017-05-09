#include <stdexcept>
#include "bracketing_method.h"

BracketingMethod::BracketingMethod(functionOfX func, double *bracketingPoints, int maxIter, double targetRelErr)
: CalculateRoots::CalculateRoots(func, bracketingPoints, maxIter, targetRelErr) {
    if (!bracketsSurroundRoot())
        throw std::invalid_argument("Points do not bracket a root.\n");
}

bool BracketingMethod::bracketsSurroundRoot() {
    fx1 = f(x1);
    fx2 = f(x2);
    return fx1 < 0 && fx2 > 0;
}
