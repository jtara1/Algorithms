#ifndef BRACKETING_METHOD_H
#define BRACKETING_METHOD_H

#include <vector>
#include "calculate_roots.h"

class BracketingMethod : public CalculateRoots {
    protected:
        bool bracketsSurroundRoot();

    public:
        BracketingMethod(functionOfX func, double *guesses, int guessesSize, int maxIterations, double targetRelativeError, double *trueRoot = nullptr);

        /// override this so it can check whether or not the current set of guesses bracket a root
        std::vector<double> calculateRoots() override;
};
#endif
