#include <fstream>
#include <string>
#include <cstdlib>

#include "polynomial_iterpolation.h"

PolynomialIterpolation::PolynomialIterpolation() {

}

void PolynomialIterpolation::loadDataFromFile(std::string inputFile) {
    std::fstream infile;
    infile.open(inputFile);
    if (infile.fail())
        throw std::exception("failed to open " + inputFile);

    std::string line;
    std::getline(infile, line);
    std::size_t index;
    // read first line containing x values
    while ((index = line.find(' ')) != std::string::npos) {
       std::string numb = line.substr(0, index);
       xValues.push_back(atof(numb.c_str()));
    }

    // read second line containing y values
    std::getline(infile, line);
    while ((index = line.find(' ')) != std::string::npos) {
       std::string numb = line.substr(0, index);
       yValues.push_back(atof(numb.c_str()));
    }

    if (xValues.size() != yValues.size())
        throw std::invalid_argument("there are a different amount of x values and y values");

}
