#include "Board.h"

// constructors
Board::Board(bool ai_starts) {
    is_ai_turn = ai_starts;

    // empty board
    for (int i = 0; i < BOARD_SIZE; ++i) {
        this->board[i] = empty_repr;
    }

    char first_val = ai_starts ? ai_repr : enemy_repr;
    char last_val = ai_starts ? enemy_repr : ai_repr;

    board[0] = first_val;
    board[BOARD_SIZE * BOARD_SIZE - 1] = last_val;
}

bool Board::IsTerminal() {
    return State::IsTerminal();
}

float Board::GetValue() {
    return State::GetValue();
}

std::vector<Action> Board::Actions() {
    return State::Actions();
}
