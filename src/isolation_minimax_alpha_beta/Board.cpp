#include "Board.h"

// constructors
Board::Board(bool ai_starts) {
    is_ai_turn = ai_starts;

    // empty board
    for (int i = 0; i < BOARD_SIZE; ++i) {
        this->board[i] = empty_repr;
    }

    ai_pos = ai_starts ? 0 : BOARD_SIZE * BOARD_SIZE - 1;
    enemy_pos = ai_starts ? BOARD_SIZE * BOARD_SIZE - 1 : 0;

    board[ai_pos] = ai_repr;
    board[enemy_pos] = enemy_repr;
}

// helper methods
int Board::GetPosition() {
    return is_ai_turn ? ai_pos : enemy_pos;
}

bool Board::CanBeOccupied(int pos) {
    return pos >= 0 && pos < BOARD_AREA && board[pos] == empty_repr;
}

// core methods
bool Board::IsTerminal() {
    int pos = GetPosition();

    // check tile above
    bool upwards_movable = pos - BOARD_SIZE >= 0 && board[pos - BOARD_SIZE] == empty_repr;

    // check tile to right
    bool rightwards_movable = pos % BOARD_SIZE != 7 && board[pos + 1] == empty_repr;

    // check tile below
    bool downwards_movable = pos + BOARD_SIZE < BOARD_AREA && board[pos + BOARD_SIZE] == empty_repr;

    // check tile to left
    bool leftwards_movable = pos % BOARD_SIZE != 0 && board[pos - 1] == empty_repr;

    return !(upwards_movable || rightwards_movable || downwards_movable || leftwards_movable); // if it can't move, it's terminal
}

float Board::GetValue() {
    return State::GetValue();
}

/*
 * move as a queen in as many as 8 directions
 * a move to each possible tile in its path is considered an action
 * directions: North, NorthEast, East, SouthEast, etc.
 * increments: -8, -7, +1, +9, etc
 */
std::vector<Action> Board::Actions() {
    static int directions [8] = {-8, -7, 1, 9, 8, 7, -1, -9};

    std::vector<Action> actions = std::vector<Action>();
    int next_pos, pos;

    for (int i = 0; i < 8; ++i) {
        pos = is_ai_turn ? ai_pos : enemy_pos;
        next_pos = pos;

        while (true) { // O(BOARD_SIZE = 8)
            next_pos += directions[i];
            if (!CanBeOccupied(next_pos)) break;

            actions.emplace_back(Action(pos, next_pos));
        }
    }

    return actions;
}
