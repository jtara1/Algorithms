#ifndef FALSE_POSITION_H
#define FALSE_POSITION_H

#include "bracketing_method.h"

class FalsePosition : public BracketingMethod {
     private:
		/// calc the next approximation
		void calculateApproximation() override;

		/**
		 * Returns true if approximation is root,
		 * also does preparation for next iteration
		 */
        bool setupNextIteration() override;

		/// print header for iteration values table
		void printIterationHeader() override;

		/// print all associated variables used in this iteration
		void printIteration() override;

	public:
		FalsePosition(functionOfX func, double *guesses, int guessesSize, int maxIterations, double targetRelativeError);
};
#endif
