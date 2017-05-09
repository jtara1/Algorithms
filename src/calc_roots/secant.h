#ifndef SECANT_H
#define SECANT_H

#include "calculate_roots.h"

class Secant : public CalculateRoots {
     protected:
        /// swaps two variables
        void swapValues(double &, double &);

        /// swap x1 and x2 if conditions for secant method are met
        void swapIfNeeded();

        /**
		 * Returns true if exact root found;
		 * also does operations to setup for next iteration in finding
		 * a root
		 */
		bool setupNextIteration() override;

     private:
		/// calc the next approximation
		void calculateApproximation() override;

		/// print header for iteration values table
		void printIterationHeader() override;

		/// print all associated variables used in this iteration
		void printIteration() override;

	public:
		Secant(functionOfX, double *, int, double);
};
#endif // SECANT_H
