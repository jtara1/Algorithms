#ifndef TEST_BUILD_BOARD_H
#define TEST_BUILD_BOARD_H

#define BOARD_SIZE 8

#include "State.h"

class Board : public State {
public:
    // constructors
    Board(bool ai_starts = true);

    // methods
    bool IsTerminal() override;

    float GetValue() override;

    std::vector<Action> Actions() override;

private:
    // attrs
    char ai_repr = 'X';
    char enemy_repr = 'O';
    char empty_repr = '-';
    char visited_repr = '#';

    char board [BOARD_SIZE * BOARD_SIZE];

    bool is_ai_turn;

    // methods
};


#endif //TEST_BUILD_BOARD_H
