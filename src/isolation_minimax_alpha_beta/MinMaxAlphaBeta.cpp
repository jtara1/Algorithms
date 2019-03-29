#include "MinMaxAlphaBeta.h"

#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>

#include "Action.cpp"
#include "State.cpp"
#include "Board.cpp"

// implementations
/*
 * function ALPHA-BETA-SEARCH(state) returns an action
        v ←MAX-VALUE(state,−∞,+∞)
        return the action in ACTIONS(state) with value v
 */
State MinMaxAlphaBeta::AlphaBetaSearch(State state) {
    float value = MaxValue(
            state,
            -std::numeric_limits<float>::infinity(),
            std::numeric_limits<float>::infinity()
    );
    // return the action in ACTIONS(state) whose float is value
    return Action().Results();
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
float MinMaxAlphaBeta::MaxValue(State state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = -std::numeric_limits<float>::infinity();

    std::vector<Action> actions = state.Actions();
    for (auto it = actions.begin(); it != actions.end(); it++) {
        Action action = *it;
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
float MinMaxAlphaBeta::MinValue(State state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = std::numeric_limits<float>::infinity();

    std::vector<Action> actions = state.Actions();
    for (auto it = actions.begin(); it != actions.end(); it++) {
        Action action = *it;
        value = std::min(value, MaxValue(action.Results(), alpha, beta)); // TODO: what's s, Results, Min ?

        if (value <= alpha) return value;
        beta = std::min(beta, value);
    }

    return value;
}