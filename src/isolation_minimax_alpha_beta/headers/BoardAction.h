#ifndef TEST_BUILD_BOARDACTION_H
#define TEST_BUILD_BOARDACTION_H

#include <vector>

//class Board;
#include "Board.h"

class BoardAction {
public:
    // attrs
    int start;
    int end;
    Board* board_ptr;

    // constructors
    BoardAction() = default;
    BoardAction(Board& board, int start, int end, char board_repr);

    // methods
    Board Results(bool mutate = false);
    static std::vector<BoardAction> Actions(Board& board, bool for_other_player = false);

    void SetScore(float score);
    float GetScore() const;

    // ops
    bool operator>=(const BoardAction& action);
    friend std::ostream& operator<<(std::ostream& os, BoardAction& action);

private:
    float score;
};

#endif //PROJECT_ACTION_H
