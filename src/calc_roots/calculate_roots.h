#ifndef CALCULATE_ROOTS_H
#define CALCULATE_ROOTS_H

#include <vector>
#include <string>

/**
 * Abstract class for calculating the roots of a function of x
 */
class CalculateRoots {
	public:
	    /**
	    * The approximation of zero (0.000000000000001)
	    */
	    static constexpr double NEAR_ZERO = 1e-15;

		/**
		 * A type of function that returns a double and takes
		 * a double as an argument
		 */
		typedef double (*functionOfX)(double);

		/**
		 * takes a function and an abitrary number of
		 * guesses and relative err. to exit at
		 */
		CalculateRoots(functionOfX func, double *guesses, int guessesSize, int maxIterations, double targetRelativeError);

		/**
		 * Begin calculating the roots
		 * Returns the vector containing all the roots
		 */
		std::vector<double> calculateRoots();

		/// returns the most recent approximation to the root
		double getApproximation();

		/// print each root that has been calculated
		void printRoots();

	protected:
		/// function that's an attribute
		functionOfX f;

		/// values and function values used during each iteration
		double x1, x2, fx1, fx2, fapprox = 1;

		/// approximation for this iteration
		double approximation, previousApproximation;

		/// errors calculated
		double relativeError = 100, absoluteError = 100;

		/// guesses per root
		int guessesPerRoot = 2;

		/// exit conditions
		double targetRelativeError;
		int maxIterations;

		/// the name of this method
		std::string methodName = "Calc Roots";

		/// all of the roots found for this function
		std::vector<double> roots;

		/// number of iterations gone through
		int iterations;

        /// a number of guesses for calculating a number of roots
		double *guesses;

		/// index of current guess; number of guesses
		int guessIndex = 0, guessesSize;

		/// get the next guess(es) from the array guesses; returns false if there are no more guesses
		bool getNextGuesses();

		/// calc the next approximation
		virtual void calculateApproximation() = 0;

		/**
		 * Returns true if the root has been found at this approx using
		 * fapprox
		 */
		bool rootFound(double const nearZero = NEAR_ZERO);

		/**
		 * Returns true if exact root found;
		 * also does operations to setup for next iteration in finding
		 * a root
		 */
		virtual bool setupNextIteration() = 0;

		/// calc relative error (E sub a)
		double calculateRelativeError();

		/// checks if maxIterations exceeded or target rel. error reached
		bool exitConditionsMet();

		/// print header for iteration values table
		virtual void printIterationHeader() = 0;

		/// print all associated variables used in this iteration
		virtual void printIteration() = 0;

		/// print an individual item for the table formatted
		void printItems(double *, int);

		/// print each string in the array of string formatted
		void printItems(std::string *, int);
};

#endif
