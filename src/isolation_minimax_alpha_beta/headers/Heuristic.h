#ifndef TEST_BUILD_HEURISTIC_H
#define TEST_BUILD_HEURISTIC_H


#include "Board.h"

class Heuristic {
public:
    Heuristic(Board* board);

    float GetScore();

private:
    Board* board_pointer;
};


#endif //TEST_BUILD_HEURISTIC_H
