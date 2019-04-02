#ifndef TEST_BUILD_MINMAXALPHABETA_H
#define TEST_BUILD_MINMAXALPHABETA_H

#include <ctime>
#include "Board.h"
#include "BoardAction.h"

class MinMaxAlphaBeta {
public:
    MinMaxAlphaBeta();

    Board AlphaBetaSearch(Board& state);

    double GetTime();

    Board AlphaBetaSearchIterative(Board &state);

    int depth = 0;
    /* the depth of the "tree" the algo will search before storing the best solution */
    int max_depth = 3;
    /* increment max_depth by this amount for iterative deepening */
    int max_depth_step = 3;

    std::clock_t clock_start;
    double max_time = 5;

    BoardAction* best_action;
    bool started_search = false;
    std::vector<BoardAction>* root_actions;

private:
    std::tuple<int, float> MinValue(Board& state, float alpha, float beta, bool is_root_recursion = false);
    std::tuple<int, float> MaxValue(Board& state, float alpha, float beta);

    int root_actions_index;
};


#endif //TEST_BUILD_MINMAXALPHABETA_H
