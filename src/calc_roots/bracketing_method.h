#ifndef BRACKETING_METHOD_H
#define BRACKETING_METHOD_H

#include "calculate_roots.h"

class BracketingMethod : public CalculateRoots {
    protected:
        bool bracketsSurroundRoot();

    public:
        BracketingMethod(functionOfX, double *, int, double);
};
#endif
