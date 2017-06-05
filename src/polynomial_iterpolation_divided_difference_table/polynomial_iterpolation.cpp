#include <fstream>
#include <string>
#include <cstdlib>
#include <regex>
#include <iostream>

#include "polynomial_iterpolation.h"

PolynomialIterpolation::PolynomialIterpolation() {

}

void PolynomialIterpolation::loadDataFromFile(std::string inputFile) {
    using std::cout;
    using std::endl;

    std::fstream infile;
    infile.open(inputFile);
    if (infile.fail())
        throw std::ios_base::failure("failed to open " + inputFile);

    std::regex pattern("([+-]?[0-9]+)\/?([+-]?[0-9]*?)", std::regex_constants::extended);
    std::smatch strMatch;
    std::string line;
    std::string numb;
    std::size_t pos = 0;
    bool done = false;

    std::vector<RationalNumber> values;
    // read the two lines containing (1st: all x vals;  2nd: all y vals)
    for (int i = 0; i < 2; i++) {
        std::getline(infile, line);

        while (true) {
            pos = line.find_first_of(" \n");
            if (pos == std::string::npos) {
                numb = line;
                done = true;
            } else {
                numb = line.substr(0, pos);
                line = line.substr(pos + 1, line.size() - pos);
            }

            bool matchFound = std::regex_search(numb, strMatch, pattern);
            if (!matchFound)
                break;

            pos += strMatch.length(0);
            RationalNumber rational(
                    strMatch.str(1),
                    strMatch.str(2)
            );
            values.push_back(rational);
            if (done)
                 break;
        }
        if (i == 0)
            xValues = values;
        else
            yValues = values;
        values.clear();
        pos = 0;
        done = false;
    }

    infile.close();

    if (xValues.size() != yValues.size())
        throw std::invalid_argument("there are a different amount of x values and y values");

    if (xValues.size() <= 1)
        throw std::invalid_argument("there needs to be at least two pairs of (x, y) values");

}
