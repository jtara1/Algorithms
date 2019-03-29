#include "BoardAction.h"
#include "State.h"

BoardAction::BoardAction(std::array<char, BOARD_AREA> &board, int start, int end, char board_repr) {
    this->board = board;
    this->start = start;
    this->end = end;
    this->board_repr = board_repr;
}

// methods
State BoardAction::Results() {
    std::array<char, BOARD_AREA> new_board = board; // copy


}
