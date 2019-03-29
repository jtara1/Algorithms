#ifndef PROJECT_ACTION_H
#define PROJECT_ACTION_H

#include <vector>

#include "Board.h"
#include "Action.h"

class State;

class BoardAction : public Action {
public:
    // attrs
    int start;
    int end;
    std::array<char, 64> board;
    char board_repr;

    // constructor
    BoardAction(std::array<char, BOARD_AREA> &board, int start, int end, char board_repr);

    // methods
    State Results();
};

#endif //PROJECT_ACTION_H
