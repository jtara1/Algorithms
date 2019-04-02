#ifndef TEST_BUILD_MINMAXALPHABETA_H
#define TEST_BUILD_MINMAXALPHABETA_H

#include <ctime>
#include "Board.h"
#include "BoardAction.h"
#include "IndexScoreTuple.h"

class MinMaxAlphaBeta {
public:
    MinMaxAlphaBeta();

    BoardAction FindAction(Board &state);

    double GetTime();
    BoardAction GetAction(int index);

    Board AlphaBetaSearchIterative(Board &state);

private:
    BoardAction AlphaBetaSearch(Board& state);
    IndexScoreTuple MinValue(Board& state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion = false);
    IndexScoreTuple MaxValue(Board& state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion = false);

    int depth = 0;

    /* the depth of the "tree" the algo will search before storing the best solution */
    int max_depth = 3;
    int max_depth_default = max_depth;

    /* increment max_depth by this amount for iterative deepening */
    int max_depth_step = 3;

    std::clock_t clock_start;
    double max_time = 5;

    BoardAction* best_action_ptr = nullptr;
    bool started_search = false;

    std::vector<BoardAction>* root_actions_ptr = nullptr;

    int root_actions_index;
    std::vector<BoardAction> root_actions;
    BoardAction best_action;
};


#endif //TEST_BUILD_MINMAXALPHABETA_H
