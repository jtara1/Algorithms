#include "calculate_roots.h"

#ifndef BISECTION_H
#define BISECTION_H

class Bisection : public CalculateRoots {
	private:
		// calc the next approximation
		void calculateApproximation() override;
		
		/**
		 * Returns true if exact root found;
		 * also does operations to setup for next iteration in finding
		 * a root
		 */
		bool setupNextIteration() override;
		
		// print header for iteration values table
		void printIterationHeader() override;
		
		// print all associated variables used in this iteration
		void printIteration() override;
		
	public:
		Bisection(CalculateRoots::functionOfX, double *);
};

#endif
