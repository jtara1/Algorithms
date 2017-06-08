#include <iostream>
#include <iomanip>

#include "divided_difference.h"

using std::cout;
using std::endl;
using std::setw;
using std::left;

DividedDifference::DividedDifference(std::string fileName) {
    RationalNumber::PRINT_AS_FRACTION = true;
    RationalNumber::PRINT_SIGN = true;
    // load x, y vals from a file
    loadDataFromFile(fileName);
    // add x, y vals to table values (prepare for interpolation & printing)
    tableValues.push_back(xValues);
    tableValues.push_back(yValues);
    tableFormatWidth = 17;

    // begin
    interpolate();

    // print results
    printTable();
    cout << "Newton's Form:\n";
    printNewtonsForm();
    cout << endl;

    cout << "Lagrange's Form:\n";
    printLagrangesFormAndBuildSimplePolynomial();
    cout << endl;

    RationalNumber::PRINT_AS_FRACTION = false;
    cout << "Simplified Form:\n";
    cout << "f(x) = " << simplifiedPolynomial << endl;
    cout << "------" << endl;
}

void DividedDifference::interpolate() {
    std::vector<RationalNumber> prevValues = yValues;
    coefficients.push_back(prevValues.at(0));

    int stepSize = 1, j = 0;
    for (int i = 0; i < xValues.size() - 1; i++) {
        for (j = 0; j < xValues.size() - stepSize; j++) {
            RationalNumber rational =
                (prevValues.at(j+1) - prevValues.at(j)) / (xValues.at(stepSize + j) - xValues.at(j));
            prevValues.push_back(rational);
        }
        prevValues.erase(begin(prevValues), begin(prevValues) + j + 1);
        coefficients.push_back(prevValues.at(0));
        tableValues.push_back(prevValues);
        stepSize++;
    }
}

void DividedDifference::printNewtonsForm() {
    std::string endParentheses = ")";

    cout << "f(x) = " << coefficients.at(0) << " + ";
    for (int i = 1; i < coefficients.size(); i++) {
        cout << "(x" << (xValues.at(i-1) * -1)
            << ")(" << coefficients.at(i)
            << (i == coefficients.size() - 1 ? endParentheses : " + ");

        endParentheses += ")";
    }
    cout << endl;
}

void DividedDifference::printLagrangesFormAndBuildSimplePolynomial() {
    std::string multiplier = "";
    Polynomial poly("-" + xValues.at(0).toString() + "x^0 + x^1");
    Polynomial temp;

    simplifiedPolynomial = Polynomial("x^0");
    simplifiedPolynomial = simplifiedPolynomial * coefficients.at(0);

    cout << "f(x) = " << coefficients.at(0);
    for (int i = 1; i < coefficients.size(); i++) {
        RationalNumber coefficient = coefficients.at(i);
        multiplier += "(x" + (xValues.at(i-1) * -1).toString() + ")";

        cout << coefficient << multiplier << " ";

        if (i != 1) {
            std::string polyStr = "-" + xValues.at(i-1).toString() + "x^0 + x^1";
            poly = poly * Polynomial(polyStr);
        }

        temp = poly * coefficient;
        simplifiedPolynomial = simplifiedPolynomial + temp;
    }
    cout << endl;
}

void DividedDifference::printHeader() {
    std::string header = "f[]";
    cout << left << setw(tableFormatWidth) << "x";
    for (int i = 1; i <= xValues.size(); i++) {
        cout << left << setw(tableFormatWidth) << header;
        header = header.insert(2, ",");
    }
    cout << endl;
}

void DividedDifference::printTable() {
    cout << setw(tableFormatWidth);

    printHeader();

    int start = 0;
    int start2 = 0;
    for (int printRow = 0; printRow < xValues.size() * 2; printRow++) {
        // print first two values (x, y) pair
        if (printRow % 2 == 0) {
            cout << left << setw(tableFormatWidth) << tableValues.at(0).at(printRow / 2)
                << setw(tableFormatWidth) << tableValues.at(1).at(printRow / 2)
                << setw(tableFormatWidth) << "";
            if (printRow < 2) {
                cout << endl;
                continue;
            }
            int j = start2;
            int i = 3;
            for (; j >= 0 && inRange(i, j); i += 2, j--) {
                if (i > 3)
                    cout << setw(tableFormatWidth) << "";
                cout << left << setw(tableFormatWidth) << tableValues.at(i).at(j);
            }
            start2++;
        } else {
            cout << setw(tableFormatWidth) << ""
                << setw(tableFormatWidth) << "";
            int j = start;
            int i = 2;
            for (; j >= 0 && inRange(i, j); i += 2, j--) {
                if (i > 2)
                    cout << setw(tableFormatWidth) << "";
                cout << left << setw(tableFormatWidth) << tableValues.at(i).at(j);
            }
            start++;
        }
        cout << endl;
    }
}

bool DividedDifference::inRange(int i, int j) const {
    return tableValues.size() - 1 >= i && tableValues.at(i).size() - 1 >= j;
}
