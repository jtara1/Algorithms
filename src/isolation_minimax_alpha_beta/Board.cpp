#include <sstream>
#include "headers/Board.h"

// constructors
Board::Board(bool ai_starts) {
    is_ai_turn = ai_starts;

    // empty board
    for (int i = 0; i < BOARD_AREA; ++i) {
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
/* current player's piece can not move moved in any direction */
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
    return 0.0;
}

std::ostringstream Board::VisualRepr() const {
    std::ostringstream stream;

    // col names
    for (int i = 0; i < BOARD_SIZE + 1; ++i) {
        stream << " ";
        if (i == 0) stream << " ";
        else stream << i;
    }
    stream << '\n';

    // each row in board with the row name
    int row = 0;
    for (int i = 0; i < BOARD_AREA; ++i) {
        if (i % BOARD_SIZE == 0) {
            row = i / BOARD_SIZE;
            stream << char(65 + row) << " ";
        }

        stream << board[i] << " ";

        if ((i % ((row + 1) * BOARD_SIZE - 1)) == 0 && i != 0) stream << '\n';
    }

    return stream;
}

std::ostream& operator<<(std::ostream& os, const Board& board) {
    os << board.VisualRepr().str();
    return os;
}

int Board::GetPlayerPos() {
    return is_ai_turn ? ai_pos : enemy_pos;
}

char Board::GetPlayerRepr() {
    return is_ai_turn ? ai_repr : enemy_repr;
}

std::string Board::Repr() const {
    return VisualRepr().str();
}
