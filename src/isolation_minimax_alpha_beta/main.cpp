#include <iostream>
#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>

#include "Action.cpp"
#include "State.cpp"

// declarations
Action AlphaBetaSearch(State);
float MaxValue(State, float, float);
float MinValue(State, float, float);
State Results(State, Action);
int main();

// implementations
/*
 * function ALPHA-BETA-SEARCH(state) returns an action
        v ←MAX-VALUE(state,−∞,+∞)
        return the action in ACTIONS(state) with value v
 */
Action AlphaBetaSearch(State state) {
    float value = MaxValue(
            state,
            -std::numeric_limits<float>::infinity(),
            std::numeric_limits<float>::infinity()
    );
    // return the action in ACTIONS(state) whose float is value
    return Action();
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
float MaxValue(State state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = -std::numeric_limits<float>::infinity();

    std::vector<Action> actions = state.Actions();
    for (auto it = actions.begin(); it != actions.end(); it++) {
        Action action = *it;
        value = std::max(value, MinValue(Results(state, action), alpha, beta)); // TODO: what's s, Results, Max ?

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
float MinValue(State state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = std::numeric_limits<float>::infinity();

    std::vector<Action> actions = state.Actions();
    for (auto it = actions.begin(); it != actions.end(); it++) {
        Action action = *it;
        value = std::min(value, MaxValue(Results(state, action), alpha, beta)); // TODO: what's s, Results, Min ?

        if (value <= alpha) return value;
        beta = std::min(beta, value);
    }

    return value;
}

State Results(State state, Action action) {
    return state;
}


 /**
 * @return
 */
int main() {
//    Board initBoard = Board();
//    Action action = AlphaBetaSearch(initBoard);
//    std::cout << action << std::endl;

    char temp;
    std::cin >> temp;
    return 0;
}
