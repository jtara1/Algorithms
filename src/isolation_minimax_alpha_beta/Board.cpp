#include <sstream>
#include <iomanip>
#include <iostream>
#include <limits>

#include "headers/Board.h"
#include "headers/LinearEquation.h"
#include "headers/BoardAction.h"

// constructors
Board::Board(bool ai_starts) {
    is_ai_turn = ai_starts;

    // empty board_pointer
    for (int i = 0; i < BOARD_AREA; ++i) {
        this->board[i] = empty_repr;
    }

    ai_pos = ai_starts ? 0 : BOARD_AREA - 1;
    enemy_pos = ai_starts ? BOARD_AREA - 1 : 0;

    board.at((size_t)ai_pos) = ai_repr;
    board.at((size_t)enemy_pos) = enemy_repr;
}

Board::Board(std::array<char, BOARD_AREA> init_board, int player1_pos, int player2_pos, bool ai_starts) : Board(ai_starts) {
//    ai_pos = ai_starts ? player1_pos : player2_pos;
//    enemy_pos = ai_starts ? player2_pos : player1_pos;

}

Board::Board(std::array<char, BOARD_AREA> init_board, int turn_count, bool ai_starts) : Board(ai_starts) {
    is_ai_turn = ai_starts;

    for (int i = 0; i < init_board.size(); ++i) {
        char tile = init_board[i];

        if (tile == ai_repr) { // set current players pos
            ai_pos = i;
//            if (ai_starts) ai_pos = i;
//            else enemy_pos = i;
        } else if (tile == enemy_repr) {
            enemy_pos = i;
//            if (ai_starts) enemy_pos = i; // set other players pos
//            else ai_pos = i;
        }

        if (tile != ai_repr && tile != enemy_repr && tile != empty_repr && tile != visited_repr)
            throw std::invalid_argument("invalid character for tile in init board");
    }

    board = init_board;
    this->turn_count = turn_count;
}

bool Board::IsAITurn() const {
    return is_ai_turn;
}

// core methods
/* if either players piece can not move moved in any direction */
bool Board::IsTerminal() {
    std::array<int, 2> positions = { GetPlayerPos(), GetOtherPlayerPos() };

    bool current_player_lost = true;

    for (int pos : positions) {
        // check tile above
        bool upwards_movable = pos - BOARD_SIZE >= 0 && board.at((size_t)pos - BOARD_SIZE) == empty_repr;

        // check NorthEast
        bool ne_movable = pos - BOARD_SIZE + 1 >= 1 && pos % BOARD_SIZE != 7 && board.at((size_t)pos - BOARD_SIZE + 1) == empty_repr;

        // check tile to right
        bool rightwards_movable = pos + 1 < BOARD_AREA && pos % BOARD_SIZE != 7 && board.at((size_t)pos + 1) == empty_repr;

        // check SouthEast
        bool se_movable = pos + BOARD_SIZE + 1 < BOARD_AREA && pos % BOARD_SIZE != 7 && board.at((size_t)pos + BOARD_SIZE + 1) == empty_repr;

        // check tile below
        bool downwards_movable = pos + BOARD_SIZE < BOARD_AREA && board.at((size_t)pos + BOARD_SIZE) == empty_repr;

        // check SouthWest
        bool sw_movable = pos + BOARD_SIZE - 1 < BOARD_AREA && pos % BOARD_SIZE != 0 && board.at((size_t)pos + BOARD_SIZE - 1) == empty_repr;

        // check tile to left
        bool leftwards_movable = pos - 1 >= 0 && pos % BOARD_SIZE != 0 && board.at((size_t)pos - 1) == empty_repr;

        // check NorthWest
        bool nw_movable = pos - BOARD_SIZE - 1 >= 0 && pos % BOARD_SIZE != 0 && board.at((size_t)pos - BOARD_SIZE - 1) == empty_repr;

        bool terminal = !(upwards_movable || rightwards_movable || downwards_movable || leftwards_movable || ne_movable || se_movable || sw_movable || nw_movable); // if it can't move, it's terminal
        if (terminal) {
            if (current_player_lost) winner_repr = GetOtherPlayerRepr();
            else winner_repr = GetPlayerRepr();
            std::cout << "reached terminal state\n";
            return true;
        }

        current_player_lost = false;
    }

    return false;
}

float Board::GetScore() {
    // if ai_turn then get current player's score
    // if not ai_turn then get other player's score
    const int player1_actions = static_cast<const int>(BoardAction::Actions(*this, !is_ai_turn).size());
    const int player2_actions = static_cast<const int>(BoardAction::Actions(*this, is_ai_turn).size());

    if (player1_actions == 0) return -std::numeric_limits<float>::infinity();
    if (player2_actions == 0) return std::numeric_limits<float>::infinity();

    return player1_actions - player2_actions;
}

// print methods
template<typename T> std::string Stringf(T t, const int& width) {
    std::ostringstream stream;
    stream << std::left << std::setw(width) << t;
    return stream.str();
}

std::ostringstream Board::VisualRepr() const {
    std::ostringstream stream;
    stream << std::left;

    // col names
    for (int i = 0; i < BOARD_SIZE + 1; ++i) {
        if (i == 0) stream << " ";
        else stream << i;
        stream << " ";
    }

    stream << '\t' << Stringf<std::string>("Player 1 (X)", 24) << Stringf<std::string>("Player 2 (O)", 24) << '\n';

    // each row in board with the row name
    int row = 0;
    for (int i = 0; i < BOARD_AREA; ++i) {
        if (i % BOARD_SIZE == 0) {
            row = i / BOARD_SIZE; // 0-indexed
            stream << char(65 + row) << " ";
        }

        stream << board.at((size_t)i) << " ";

        if ((i % ((row + 1) * BOARD_SIZE - 1)) == 0 && i != 0) { // if entire row was printed
            if (action_history.size() < row * 2 + 1) {
                stream << '\n';
            } else { // action history has at least 1 action to print for this row
                bool has_next = action_history.size() > row * 2 + 1; // has a 2nd action to print for this row
                std::string next_move;
                if (has_next) next_move = action_history.at((size_t)row * 2 + 1);

                stream << '\t' << row + 1 << ". " << Stringf<std::string>(action_history.at((size_t)row * 2), 24);
                stream << Stringf<std::string>(next_move, 24) << '\n';
            }
        }
    }

    // additional rows if there's more action history
    if (action_history.size() > BOARD_SIZE * 2) {
        auto first = action_history.begin() + BOARD_SIZE * 2;
        auto last = action_history.end();
        std::vector<std::string> history(first, last);

        int row = BOARD_SIZE + 1; // 1-indexed since it's used to print here
        int count = 0;

        for (const std::string &move : history) {
            if (count % 2 == 1) {
                stream << std::right << std::setw(24) << move << '\n';
                ++row;
            }
            else
                stream << std::right << std::setw(21) << row << ". " << move;
            ++count;
        }
    }

    return stream;
}

std::ostream& operator<<(std::ostream& os, const Board& board) {
    os << board.VisualRepr().str();
    return os;
}

// helper methods
bool Board::CanBeOccupied(int pos) {
    return pos >= 0 && pos < BOARD_AREA && board.at((size_t)pos) == empty_repr;
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

bool Board::IsLegalMove(int board_index, bool for_other_player) {
    std::tuple<int, int> coords = BoardIndexToCoords(board_index);
    return IsLegalMove(std::get<0>(coords), std::get<1>(coords), for_other_player);
}

bool Board::IsLegalMove(int row, int col, bool for_other_player) {
    if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) return false;

    // get the linear equation from current pos, to new pos
    std::tuple<int, int> coords = BoardIndexToCoords(for_other_player ? GetOtherPlayerPos() : GetPlayerPos()); // current pos on board
    int x = std::get<1>(coords); // x for this player
    int y = std::get<0>(coords); // y for this player
    int x2 = col; // destination x
    int y2 = row; // destination y

    LinearEquation eqn = LinearEquation(x, y, x2, y2);
    if (!eqn.IsQueenAttackPath(x2, y2)) return false;

    int prev_x = x;

    for (std::tuple<int, int> point : eqn.GetPointsBetween(x2, y2)) { // includes destination pos, but excludes current pos
        int next_x = std::get<1>(point);
        int next_y = std::get<0>(point);

        // move caused player to wrap around from right to left
        if (prev_x % (BOARD_SIZE - 1) == 0 && prev_x != 0 && next_x % BOARD_SIZE == 0) return false;

        // move caused player to wrap around from left to right
        bool next_x_is_on_right_col = next_x % (BOARD_SIZE - 1) == 0 && next_x != 0;
        bool prev_x_is_on_left_col = prev_x % BOARD_SIZE == 0;
        if (prev_x_is_on_left_col && next_x_is_on_right_col) return false;

        int tile_index = CoordsToBoardIndex(next_x, next_y);
        if (board.at((size_t)tile_index) != empty_repr) return false;

        prev_x = next_x;
    }

    return true;
}

int Board::CoordsToBoardIndex(int row, int col) {
    return row * BOARD_SIZE + col;
}

std::tuple<int, int> Board::BoardIndexToCoords(int index) {
    int row = index / BOARD_SIZE;
    int col = index - (BOARD_SIZE * row);
    return { row, col };
}

void Board::MovePlayer(int row, int col) {
    return MovePlayer(CoordsToBoardIndex(row, col));
}

void Board::MovePlayer(int pos) {
    board.at((size_t)pos) = GetPlayerRepr();
    int player_pos = GetPlayerPos();
    board.at((size_t)player_pos) = visited_repr;

    UpdateActionHistory(pos);

    SetPlayerPos(pos);

    ++turn_count;
    is_ai_turn = !is_ai_turn;
}

void Board::UpdateActionHistory(int pos) {
    std::tuple<int, int> coords = BoardIndexToCoords(pos);
    int row = std::get<0>(coords);
    int col = std::get<1>(coords);

    char row_repr = char('A' + row);
    int col_repr = col + 1;

//    std::string repr = std::string(&row_repr) + std::to_string(col_repr);
//    action_history.push_back(repr);

    std::ostringstream stream;
    stream << row_repr << col_repr;

    action_history.push_back(stream.str());
}

void Board::SetPlayerPos(int pos) {
    is_ai_turn ? ai_pos = pos : enemy_pos = pos;
}

int Board::GetOtherPlayerPos() {
    return is_ai_turn ? enemy_pos : ai_pos;
}

char Board::GetOtherPlayerRepr() {
    return is_ai_turn ? enemy_repr : ai_repr;
}

std::array<char, BOARD_AREA> Board::GetBoard() const {
    return board;
}

char Board::GetWinnerRepr() {
    return winner_repr;
}

int Board::GetTurnCount() {
    return turn_count;
}

