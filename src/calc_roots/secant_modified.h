#ifndef SECANT_MODIFIED_H
#define SECANT_MODIFIED_H

//#include "secant.h"
#include "calculate_roots.h"

class SecantModified : public CalculateRoots {
     private:
        /// delta used in calculating roots using modified secant method
        double delta;

        /// true if guess is valid
        bool guessIsValid();

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
		SecantModified(functionOfX func, double delta, double *guesses, int guessesSize, int maxIterations, double targetRelativeError, double *trueRoot = NULL);

		std::vector<double> calculateRoots() override;
};
#endif // SECANT_MODIFIED_H
