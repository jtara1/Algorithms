#include <utility>

#include <tuple>
#include <iostream>
#include <set>
#include "headers/BoardAction.h"

BoardAction::BoardAction(Board board, int start, int end, char board_repr) {
    this->board = std::move(board);
    board_ptr = &(this->board);
    this->start = start;
    this->end = end;
    if (end < 0 || end >= BOARD_AREA)
        throw std::invalid_argument("BoardAction::BoardAction board player pos, end index out of range");
}

// methods
Board BoardAction::Results(bool mutate) {
    if (!mutate) {
        Board board = this->board;
        board.MovePlayer(end);
        return board;
    }

    board_ptr->MovePlayer(end);
    return *board_ptr;
}

//void BoardAction::SetScore(float score) {
//    this->score = score;
//}

/*
 * move as a queen in as many as 8 directions
 * a move to each possible tile in its path is considered an action
 * directions: North, NorthEast, East, SouthEast, etc.
 * increments: -8, -7, +1, +9, etc
 */
std::vector<BoardAction> BoardAction::Actions(Board board, bool for_other_player, bool get_first_action_available) {
    static const int directions_count = 8;
    static int directions [directions_count] = { -8, -7, 1, 9, 8, 7, -1, -9 };

    std::set<BoardAction> actions = std::set<BoardAction>();
    int next_pos;
    const int pos = for_other_player ? board.GetOtherPlayerPos() : board.GetPlayerPos();
    char tile_repr = for_other_player ? board.GetOtherPlayerRepr() : board.GetPlayerRepr();

    for (int i = 0; i < directions_count; ++i) { // iter over ea direction
        next_pos = pos;

        for (int j = 0; j < BOARD_SIZE - 1; ++j) { // max number of moves in one direction
            next_pos += directions[i];
            if (!board.IsLegalMove(next_pos, for_other_player)) break;

            BoardAction action = BoardAction(board, pos, next_pos, tile_repr);
            actions.insert(action);
            if (get_first_action_available) return std::vector<BoardAction> (actions.begin(), actions.end());
        }
    }

//    return std::vector<BoardAction> (actions.begin(), actions.end());
    std::vector<BoardAction> actions_vector(actions.begin(), actions.end());
    return actions_vector;
}

//bool BoardAction::operator>=(const BoardAction &action) const {
//    return score >= action.GetScore();
//}

std::ostream &operator<<(std::ostream &os, BoardAction &action) {
    std::tuple<int, int> coords = Board::BoardIndexToCoords(action.end);
    os << "row, col indices: " << std::get<1>(coords) << ", " << std::get<0>(coords) << '\n';
    return os;
}

bool BoardAction::operator<(const BoardAction &action) const {
    return end < action.end;
}

int BoardAction::GetEnd() const {
    return end;
}

Board BoardAction::Results(Board &board, BoardAction &action, bool mutate) {
    if (!mutate) {
        Board board_copy = board;
        board_copy.MovePlayer(action.GetEnd());
        return board_copy;
    }

    board.MovePlayer(action.GetEnd());
    return board;
}

