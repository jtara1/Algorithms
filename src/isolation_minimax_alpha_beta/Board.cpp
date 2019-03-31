#include <sstream>
#include "headers/Board.h"
#include "headers/LinearEquation.h"

// constructors
Board::Board(bool ai_starts) {
    is_ai_turn = ai_starts;

    // empty board_pointer
    for (int i = 0; i < BOARD_AREA; ++i) {
        this->board[i] = empty_repr;
    }

    ai_pos = ai_starts ? 0 : BOARD_SIZE * BOARD_SIZE - 1;
    enemy_pos = ai_starts ? BOARD_SIZE * BOARD_SIZE - 1 : 0;

    board[ai_pos] = ai_repr;
    board[enemy_pos] = enemy_repr;
}

// core methods
/* current player's piece can not move moved in any direction */
bool Board::IsTerminal() {
    int pos = GetPlayerPos();

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

// print methods
std::ostringstream Board::VisualRepr() const {
    std::ostringstream stream;

    // col names
    for (int i = 0; i < BOARD_SIZE + 1; ++i) {
        if (i == 0) stream << " ";
        else stream << i;
        stream << " ";
    }
    stream << '\n';

    // each row in board_pointer with the row name
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

// helper methods
bool Board::CanBeOccupied(int pos) {
    return pos >= 0 && pos < BOARD_AREA && board[pos] == empty_repr;
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

bool Board::IsLegalMove(int row, int col) {
    int pos = CoordsToBoardIndex(row, col);
    if (!CanBeOccupied(pos)) return false;

    // get the linear equation from current pos, to new pos
    std::tuple<int, int> coords = BoardIndexToCoords(GetPlayerPos());
    float x = (float)std::get<1>(coords);
    float y = (float)std::get<0>(coords);
    float x2 = (float)col;
    float y2 = (float)row;

    LinearEquation eqn = LinearEquation(x, y, x2, y2);
    if (!eqn.IsQueenAttackPath(x, y)) return false;

    for (std::tuple<float, float> point : eqn.GetPointsBetween(x2, y2)) {
        int tile_index = CoordsToBoardIndex((int)std::get<1>(point), (int)std::get<0>(point));
        if (board[tile_index] != empty_repr) return false;
    }

    return true;
}

int Board::CoordsToBoardIndex(int row, int col) {
    return row * BOARD_SIZE + col;
}

std::tuple<int, int> Board::BoardIndexToCoords(int index) {
    int row = index % BOARD_SIZE;
    int col = index - (BOARD_SIZE * row);
    return std::tuple<int, int>(row, col);
}

void Board::MovePlayer(int row, int col) {
    return MovePlayer(CoordsToBoardIndex(row, col));
}

void Board::MovePlayer(int pos) {
    board[pos] = GetPlayerRepr();
    board[GetPlayerPos()] = visited_repr;

    is_ai_turn = !is_ai_turn;
}
