#include "headers/BoardAction.h"
//#include "headers/State.h"

//BoardAction::BoardAction(std::array<char, BOARD_AREA> board, int start, int end, char board_repr) {
BoardAction::BoardAction(Board& board, int start, int end, char board_repr) {
//    this->board = board;
    board_ptr = &board;
    this->start = start;
    this->end = end;
    this->tile_repr = board_repr;
}

// methods
Board BoardAction::Results() {
//    std::array<char, BOARD_AREA> new_board = board; // copy
    return Board();
}

/*
 * move as a queen in as many as 8 directions
 * a move to each possible tile in its path is considered an action
 * directions: North, NorthEast, East, SouthEast, etc.
 * increments: -8, -7, +1, +9, etc
 */
std::vector<BoardAction> BoardAction::Actions(Board& board) {
    static int directions [8] = {-8, -7, 1, 9, 8, 7, -1, -9};

    std::vector<BoardAction> actions = std::vector<BoardAction>();
    int next_pos, pos;

    for (int i = 0; i < 8; ++i) {
        pos = board.GetPlayerPos();
        next_pos = pos;

        while (true) { // O(BOARD_SIZE = 8)
            next_pos += directions[i];
            if (!board.CanBeOccupied(next_pos)) break;

            char tile_repr = board.GetPlayerRepr();
            BoardAction action = BoardAction(board, pos, next_pos, tile_repr);
            actions.emplace_back(action);
        }
    }

    return actions;
}