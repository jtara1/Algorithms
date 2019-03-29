#ifndef TEST_BUILD_BOARD_H
#define TEST_BUILD_BOARD_H

#define BOARD_SIZE 8
#define BOARD_AREA 64

#include <array>

#include "State.h"

class BoardAction;

class Board : public State {
public:
    // constructors
    Board(bool ai_starts = true);

    // methods
    bool IsTerminal() override;

    float GetValue() override;

    template<class T = BoardAction>
    std::vector<BoardAction> Actions() override;

private:
    // attrs
    char ai_repr = 'X';
    char enemy_repr = 'O';
    char empty_repr = '-';
    char visited_repr = '#';

    int ai_pos;
    int enemy_pos;

    std::array<char, BOARD_AREA> board;

    bool is_ai_turn;

    // methods
    int GetPosition();
    bool CanBeOccupied(int pos);
};


#endif //TEST_BUILD_BOARD_H
