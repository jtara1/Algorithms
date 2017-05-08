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
		NewtonRaphson(functionOfX, functionOfX, double *);
};
#endif // NEWTON_RAPHSON_H
