#ifndef TEST_BUILD_BOARDACTION_H
#define TEST_BUILD_BOARDACTION_H

#include <vector>

//class Board;
#include "Board.h"

class BoardAction {
public:
    // attrs
    int start;
    int end;
//    std::array<char, BOARD_AREA> board_pointer;
    Board* board_ptr;
    char tile_repr;

    // constructor
    BoardAction(Board& board, int start, int end, char board_repr);

    // methods
    Board Results();
    static std::vector<BoardAction> Actions(Board& board, bool for_other_player = false);

};

#endif //PROJECT_ACTION_H
