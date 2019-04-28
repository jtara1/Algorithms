#ifndef TEST_BUILD_MINMAXALPHABETA_H
#define TEST_BUILD_MINMAXALPHABETA_H

#include <ctime>
#include <random>

#include "Board.h"
#include "BoardAction.h"
#include "IndexScoreTuple.h"

class MinMaxAlphaBeta {
public:
    MinMaxAlphaBeta(bool is_ai_player1 = true, float time_limit_sec = 2);

//    BoardAction FindAction(Board& state);
    BoardAction FindAction(Board* state_ptr);

    double GetTime();

private:
    BoardAction AlphaBetaSearch(Board board);
    IndexScoreTuple MinValue(Board state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion = false);
    IndexScoreTuple MaxValue(Board state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion = false);

    BoardAction GetRootAction(int index, bool previous_call_failed = false);

    void Reset();

    int GetRandomIndex(int upper_limit);
    int depth = 0;

    /* the depth of the "tree" the algo will search before storing the best solution */
    int max_depth = 1;
    int max_depth_default = max_depth;

    /* increment max_depth by this amount for iterative deepening */
    int max_depth_step = 1;

    std::clock_t clock_start;
    double max_time = 2;

    bool started_search = false;
    bool is_ai_player1;

    int root_actions_index = -1;

    std::vector<BoardAction>* root_actions_ptr = nullptr;
    std::vector<BoardAction> root_actions;

    BoardAction* best_action_ptr = nullptr;
    BoardAction best_action;

    Board state;
    Board* state_ptr = nullptr;
    BoardAction first_available_action;

    float time_limit_padding = 0.01;

    std::mt19937 mt; // used to get random number
};


#endif //TEST_BUILD_MINMAXALPHABETA_H
