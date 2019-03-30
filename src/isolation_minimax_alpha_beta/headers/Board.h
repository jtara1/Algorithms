#ifndef TEST_BUILD_BOARD_H
#define TEST_BUILD_BOARD_H

#define BOARD_SIZE 8
#define BOARD_AREA 64

#include <array>

//#include "State.h"
//class BoardAction {};

class Board {
public:
    // constructors
    Board(bool ai_starts = true);

    // methods
    bool IsTerminal();
    float GetValue();

    bool CanBeOccupied(int pos);
    /* is current player is allowed to move from his current pos to this pos? */
    bool IsLegalMove(int row, int col);
    int GetPlayerPos();
    char GetPlayerRepr();

    std::string Repr() const;
    std::ostringstream VisualRepr() const;
    friend std::ostream& operator<<(std::ostream& os, const Board& board);

    int ai_pos;
    int enemy_pos;

private:
    // attrs
    char ai_repr = 'X';
    char enemy_repr = 'O';
    char empty_repr = '-';
    char visited_repr = '#';

    std::array<char, BOARD_AREA> board;

    bool is_ai_turn;

    // methods
    int GetPosition();
};


#endif //TEST_BUILD_BOARD_H
