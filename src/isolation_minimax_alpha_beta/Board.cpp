#include "Board.h"

// constructors
Board::Board(bool ai_starts) {
    is_ai_turn = ai_starts;

    // empty board
    for (int i = 0; i < BOARD_SIZE; ++i) {
        this->board[i] = empty_repr;
    }

    ai_pos = ai_starts ? (char)0 : (char)(BOARD_SIZE * BOARD_SIZE - 1);
    enemy_pos = ai_starts ? (char)(BOARD_SIZE * BOARD_SIZE - 1) : (char)0;

    board[ai_pos] = ai_repr;
    board[enemy_pos] = enemy_repr;
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
