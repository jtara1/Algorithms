#include <vector>

#ifndef CALCULATE_ROOTS_H
#define CALCULATE_ROOTS_H

/**
 * Abstract class for calculating the roots of a function of x
 */
class CalculateRoots {
	protected:
		// function that's an attribute
		double (*functionOfX)(double);
		
		// approximation for this iteration & two x values to calc approx
		double approximation, x1, x2, previousApproximation;
		
		// errors calculated
		double relativeError, absoluteError;
		
		// all of the roots found for this function
		std::vector<double> roots;
		
		// number of iterations gone through
		int iterations;
		
		// calc the next approximation
		virtual void calculateApproximation();
		
		/**
		 * Returns true if exact root found;
		 * also does operations to setup for next iteration in finding
		 * a root
		 */
		virtual bool setupNextIteration();
		
		// calc relative error (E sub a)
		double calculateRelativeError();
		
		// print header for iteration values table
		virtual void printIterationHeader();
		
		// print all associated variables used in this iteration
		virtual void printIteration();
		
	public:
		// takes a function and an abitrary number of guesses
		CalculateRoots(double (*f)(double), double *);
		
		/**
		 * Begin calculating the roots
		 * Returns the vector containing all the roots
		 */
		std::vector<double> calculateRoots();
};

#endif 
