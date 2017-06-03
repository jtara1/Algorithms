#include <iostream>
#include "divided_difference.h"

DividedDifference::DividedDifference(std::string fileName) {
    this->loadDataFromFile(fileName);
    this->interpolate();
}

void DividedDifference::interpolate() {
    using std::cout;
    using std::endl;

    for (int i = 0; i < xValues.size(); i++) {
        cout << xValues.at(i) << endl;
    }
    std::vector<RationalNumber> coefficients;
    std::vector<RationalNumber> prevValues = yValues;

    int stepSize = 1, j = 0;
    for (int i = 0; i < xValues.size() - 1; i++) {
        for (j = 0; j < xValues.size() - stepSize; j++) {
            RationalNumber rational =
                (prevValues.at(j+1) - prevValues.at(j)) / (xValues.at(stepSize + j) - xValues.at(j));
            std::cout << rational << (j == prevValues.size() - stepSize ? "\n" : ", ");
            prevValues.push_back(rational);
        }
        coefficients.push_back(prevValues.at(0));
        prevValues.erase(begin(prevValues), begin(prevValues) + j + 1);
        stepSize++;
    }
}
