#include "headers/MinMaxAlphaBeta.h"

#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>

#include "headers/Board.h"
#include "headers/BoardAction.h"

// implementations
/*
 * function ALPHA-BETA-SEARCH(state) returns an action
        v ←MAX-VALUE(state,−∞,+∞)
        return the action in ACTIONS(state) with value v
 */
Board MinMaxAlphaBeta::AlphaBetaSearch(Board state) {
    float value = MaxValue(
            state,
            -std::numeric_limits<float>::infinity(),
            std::numeric_limits<float>::infinity()
    );
    // return the action in ACTIONS(state) whose float is value
//    return BoardAction().Results();
    return Board();
}

/*
 * function MAX-VALUE(state, α, β) returns a utility value
        if TERMINAL-TEST(state) then return UTILITY(state)
        v ← −∞
        for each a in ACTIONS(state) do
            v ← MAX(v, MIN-VALUE(RESULT(s,a), α, β))
            if v ≥ β then return v
            α←MAX(α, v)
        return v
 */
float MinMaxAlphaBeta::MaxValue(Board state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = -std::numeric_limits<float>::infinity();

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    for (auto it = actions.begin(); it != actions.end(); it++) {
        BoardAction action = *it;
        value = std::max(value, MinValue(action.Results(), alpha, beta)); // TODO: what's s, Results, Max ?

        if (value >= beta) return value;
        alpha = std::max(alpha, value);
    }

    return value;
}

/*
 * function MIN-VALUE(state, α, β) returns a utility value
        if TERMINAL-TEST(state) then return UTILITY(state)
        v ← +∞
        for each a in ACTIONS(state) do
            v ← MIN(v, MAX-VALUE(RESULT(s,a) , α, β))
            if v ≤ α then return v
            β ← MIN(β, v)
        return v
 */
float MinMaxAlphaBeta::MinValue(Board state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = std::numeric_limits<float>::infinity();

    std::vector<BoardAction> actions = BoardAction::Actions(state);
    for (auto it = actions.begin(); it != actions.end(); it++) {
        BoardAction action = *it;
        value = std::min(value, MaxValue(action.Results(), alpha, beta)); // TODO: what's s, Results, Min ?

        if (value <= alpha) return value;
        beta = std::min(beta, value);
    }

    return value;
}