#ifndef DIVIDED_DIFFERENCE_H
#define DIVIDED_DIFFERENCE_H

class DividedDifference : public PolynomialIterpolation {
private:
    void iterpolate();
public:
    DividedDifference(string fileName = "input.txt");
};
#endif // DIVIDED_DIFFERENCE_H
