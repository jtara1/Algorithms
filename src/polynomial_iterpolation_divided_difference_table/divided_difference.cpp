#include "divided_difference.h"

DividedDifference::DividedDifference(string fileName) {
    loadDataFromFile(fileName);
}

void DividedDifference::iterpolate() {
    std::vector<double> coefficients;
    std::vector<double> prevValues;

    int stepSize = 1;
    for (int i = 0; i < xValues.size(); i++) {
        for (int j = 0; i < xValues.size() - i; j++) {
            prevValues.push_back();
        }
        stepSize++;
    }
}
