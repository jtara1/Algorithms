#include "headers/MinMaxAlphaBeta.h"

#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>
#include <tuple>

#include "headers/Board.h"
#include "headers/BoardAction.h"
#include "headers/IndexScoreTuple.h"

// implementations
/*
 * function ALPHA-BETA-SEARCH(state_pointer) returns an action
        v ←MAX-VALUE(state_pointer,−∞,+∞)
        return the action in ACTIONS(state_pointer) with value v
 */
Board MinMaxAlphaBeta::AlphaBetaSearch(Board& state) {
    if (!started_search) this->clock_start = std::clock();
    this->started_search = true;

    IndexScoreTuple value = MaxValue(
            state,
            -std::numeric_limits<float>::infinity(),
            std::numeric_limits<float>::infinity(),
            true
    );

    max_depth += max_depth_step;
    if (GetTime() <= max_time) return AlphaBetaSearch(state);
    // return the action in ACTIONS(state_pointer) whose float is value
//    return BoardAction().Results();
    return Board();
}

Board MinMaxAlphaBeta::AlphaBetaSearchIterative(Board& state) {

}

/*
 * function MAX-VALUE(state_pointer, α, β) returns a utility value
        if TERMINAL-TEST(state_pointer) then return UTILITY(state_pointer)
        v ← −∞
        for each a in ACTIONS(state_pointer) do
            v ← MAX(v, MIN-VALUE(RESULT(s,a), α, β))
            if v ≥ β then return v
            α←MAX(α, v)
        return v
 */
IndexScoreTuple MinMaxAlphaBeta::MaxValue(Board& state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion) {
    if (depth >= max_depth || state.IsTerminal()) return {root_actions_index, state.GetScore()};

    IndexScoreTuple value = IndexScoreTuple::NegInf();
    ++(this->depth);

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    if (this->root_actions == nullptr) root_actions = &actions;

    int root_actions_index = -1;
    for (auto it = actions.begin(); it != actions.end(); it++) {
        root_actions_index += is_root_recursion;
        if (is_root_recursion) this->root_actions_index = root_actions_index;

        BoardAction action = *it;

        Board next_board = action.Results();
        value = std::max(value, MinValue(next_board, alpha, beta)); // TODO: what's s, Results, Max ?

        if (value >= beta)
            return { root_actions_index, state.GetScore() };
        beta = IndexScoreTuple::Max(beta, value);
    }

    return IndexScoreTuple(root_actions_index, state.GetScore());
}

/*
 * function MIN-VALUE(state_pointer, α, β) returns a utility value
        if TERMINAL-TEST(state_pointer) then return UTILITY(state_pointer)
        v ← +∞
        for each a in ACTIONS(state_pointer) do
            v ← MIN(v, MAX-VALUE(RESULT(s,a) , α, β))
            if v ≤ α then return v
            β ← MIN(β, v)
        return v
 */
IndexScoreTuple MinMaxAlphaBeta::MinValue(Board& state, IndexScoreTuple& alpha, IndexScoreTuple& beta, bool is_root_recursion) {
    if (depth >= max_depth || state.IsTerminal()) std::make_tuple(root_actions_index, state.GetScore());

    IndexScoreTuple value = IndexScoreTuple::Inf();
    ++(this->depth);

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    if (this->root_actions == nullptr) root_actions = &actions;

    int root_actions_index = -1;
    for (auto it = actions.begin(); it != actions.end(); it++) {
        root_actions_index += is_root_recursion;
        if (is_root_recursion) this->root_actions_index = root_actions_index;

        BoardAction action = *it;

        Board next_board = action.Results();
        value = std::min(value, MaxValue(next_board, alpha, beta)); // TODO: what's s, Results, Min ?

        if (value <= alpha)
            return { root_actions_index, state.GetScore() };
        beta = IndexScoreTuple::Min(beta, value);
    }

    return { root_actions_index, state.GetScore() };
}

MinMaxAlphaBeta::MinMaxAlphaBeta() {

}

double MinMaxAlphaBeta::GetTime() {
    return (std::clock() - this->clock_start) / (double)CLOCKS_PER_SEC;
}
