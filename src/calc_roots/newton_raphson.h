#ifndef NEWTON_RAPHSON_H
#define NEWTON_RAPHSON_H

#include "calculate_roots.h"

class NewtonRaphson : public CalculateRoots {
    private:
        functionOfX fPrime;

        double fPrimex1;

		/// calc the next approximation
		void calculateApproximation() override;

		/**
		 * Returns true if exact root found;
		 * also does operations to setup for next iteration in finding
		 * a root
		 */
		bool setupNextIteration() override;

		/// print header for iteration values table
		void printIterationHeader() override;

		/// print all associated variables used in this iteration
		void printIteration() override;

	public:
		NewtonRaphson(functionOfX func, functionOfX fPrime, double *guesses, int guessesSize, int maxIterations, double targetRelativeError, double *trueRoot = NULL);
};
#endif // NEWTON_RAPHSON_H
