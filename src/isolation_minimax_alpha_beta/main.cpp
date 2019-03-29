#include <iostream>
#include <limits>
#include <vector>
#include <algorithm>
#include <iterator>

#include "BoardAction.cpp"
#include "State.cpp"
#include "Board.cpp"

// declarations
template<class A>
BoardAction AlphaBetaSearch(State);
template<class A>
float MaxValue(State, float, float);
template<class A>
float MinValue(State, float, float);
State Results(State, BoardAction);
int main();

// implementations
/*
 * function ALPHA-BETA-SEARCH(state) returns an action
        v ←MAX-VALUE(state,−∞,+∞)
        return the action in ACTIONS(state) with value v
 */
template<class A>
BoardAction AlphaBetaSearch(State state) {
    float value = MaxValue<A>(
            state,
            -std::numeric_limits<float>::infinity(),
            std::numeric_limits<float>::infinity()
    );
    // return the action in ACTIONS(state) whose float is value
    return NULL;
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
template<class A>
float MaxValue(State state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = -std::numeric_limits<float>::infinity();

    std::vector<A> actions = state.Actions<A>();
    for (auto it = actions.begin(); it != actions.end(); it++) {
        A action = *it;
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
template<class A>
float MinValue(State state, float alpha, float beta) {
    if (state.IsTerminal()) return state.GetValue();
    float value = std::numeric_limits<float>::infinity();

    std::vector<A> actions = state.Actions<A>();
    for (auto it = actions.begin(); it != actions.end(); it++) {
        A action = *it;
        value = std::min(value, MaxValue(action.Results(), alpha, beta)); // TODO: what's s, Results, Min ?

        if (value <= alpha) return value;
        beta = std::min(beta, value);
    }

    return value;
}

 /**
 * @return
 */
int main() {
    Board initBoard = Board();
    BoardAction action = AlphaBetaSearch<BoardAction>(initBoard);
//    std::cout << action << std::endl;

    char temp;
    std::cin >> temp;
    return 0;
}
