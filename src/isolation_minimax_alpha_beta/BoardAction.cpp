#include "headers/BoardAction.h"

BoardAction::BoardAction(Board& board, int start, int end, char board_repr) {
    board_ptr = &board;
    this->start = start;
    this->end = end;
    this->tile_repr = board_repr;
}

// methods
Board BoardAction::Results() {
    return Board();
}

/*
 * move as a queen in as many as 8 directions
 * a move to each possible tile in its path is considered an action
 * directions: North, NorthEast, East, SouthEast, etc.
 * increments: -8, -7, +1, +9, etc
 */
std::vector<BoardAction> BoardAction::Actions(Board& board, bool for_other_player) {
    static int directions [8] = {-8, -7, 1, 9, 8, 7, -1, -9};

    std::vector<BoardAction> actions = std::vector<BoardAction>();
    int next_pos;
    int pos = for_other_player ? board.GetOtherPlayerPos() : board.GetPlayerPos();
    char tile_repr = for_other_player ? board.GetOtherPlayerRepr() : board.GetPlayerRepr();

    for (int i = 0; i < 8; ++i) {
        next_pos = pos;

        while (true) { // O(BOARD_SIZE = 8)
            next_pos += directions[i];
            if (!board.CanBeOccupied(next_pos)) break;

            BoardAction action = BoardAction(board, pos, next_pos, tile_repr);
            actions.emplace_back(action);
        }
    }

    return actions;
}