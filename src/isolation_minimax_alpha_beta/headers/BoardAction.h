#ifndef TEST_BUILD_BOARDACTION_H
#define TEST_BUILD_BOARDACTION_H

#include <vector>

//class Board;
#include "Board.h"

class BoardAction {
public:
    // constructors
    BoardAction() = default;
    BoardAction(Board& board, int start, int end, char board_repr);

    // methods
    Board Results(bool mutate = false);

    // static
    static std::vector<BoardAction> Actions(Board& board, bool for_other_player = false);
    static Board Results(Board &board, BoardAction &action, bool mutate = false);

    // get and set
    void SetScore(float score);
    float GetScore() const;
    int GetEnd() const;

    // ops
    bool operator>=(const BoardAction& action);
    bool operator<(const BoardAction& action) const;
    friend std::ostream& operator<<(std::ostream& os, BoardAction& action);

private:
    // attrs
    int start;
    int end;
    Board* board_ptr;
    float score = 0;
};

#endif //PROJECT_ACTION_H
